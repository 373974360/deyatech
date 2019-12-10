package com.deyatech.admin.util;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.deyatech.admin.vo.MetadataCollectionMetadataVo;
import com.deyatech.admin.vo.MetadataCollectionVo;
import com.deyatech.admin.vo.MetadataVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.ColumnMapRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

/**
 * @author doukang
 * @description 元数据工具类
 * @date 2019/8/19 12:13
 */
@Slf4j
@Component
public class MetaUtils {

    @Autowired
    private JdbcUtils jdbcUtils;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private static JdbcUtils staticJdbcUtils;

    private static JdbcTemplate staticJdbcTemplate;

    private static String databaseName;

    @PostConstruct
    public void init() {
        staticJdbcUtils = jdbcUtils;
        staticJdbcTemplate = jdbcTemplate;
        databaseName = jdbcUtils.getDatabaseName();
    }

//    /**
//     * 初始化元数据集对应的表
//     *
//     * @param metadataCollectionVoList
//     */
//    public static void initMetadataTable(List<MetadataCollectionVo> metadataCollectionVoList) {
//        if (CollectionUtil.isEmpty(metadataCollectionVoList)) {
//            return;
//        }
//        Map<String, Table> tableMap = parseTable(metadataCollectionVoList);
//        if (CollectionUtil.isEmpty(tableMap)) {
//            return;
//        }
//
//        Map<String, Table> existTableMap = getExistTableFromDB(tableMap.keySet());
//        for (Table table : tableMap.values()) {
//            if (existTableMap.keySet().contains(table.getTableName())) {
//                alterTable(table, existTableMap.get(table.getTableName()));
//            } else {
//                createTable(table);
//            }
//        }
//    }

    /**
     * 元数据集新增或者修改
     *
     * @param metadataCollectionVo
     */
    public static void metadataTableChange(MetadataCollectionVo metadataCollectionVo, boolean isUpdate) {
        Table table = parseTable(metadataCollectionVo);
        if (isUpdate) {
            String sql = "SELECT COUNT(*) AS cnt FROM " + table.getFullTableName();
            Map<String, Object> data;
            try {
                data = staticJdbcTemplate.queryForObject(sql, new ColumnMapRowMapper(), null);
                if (data == null) {
                    throw new RuntimeException("检查" + table.getTableName() + "表是否存在");
                }
            } catch (Exception e) {
                throw new RuntimeException("检查" + table.getTableName() + "表是否存在");
            }
            long count = (long) data.get("cnt");
            if (count > 0) {
                Table oldTable = getTableFromDB(table.getTableName());
                if (oldTable == null) {
                    throw new RuntimeException("检查" + table.getTableName() + "表是否存在");
                }
                List<MetadataCollectionMetadataVo> oldMetadataList = metadataCollectionVo.getOldMetadataList();
                List<Column> oldColumnList = oldTable.getColumnList();
                for (Column oldColumn : oldColumnList) {
                    for (MetadataCollectionMetadataVo cmd : oldMetadataList) {
                        if (oldColumn.columnName.equals(cmd.getFieldName())) {
                            oldColumn.setId(cmd.getMetadataId());
                            break;
                        }
                    }
                }
                alterTable(table, oldTable);
            }
        } else {
            createTable(table);
        }
    }

    public static long countTotal(String tableName) {
        String sql = "SELECT COUNT(*) AS cnt FROM `" + tableName + "`";
        Map<String, Object> data;
        try {
            data = staticJdbcTemplate.queryForObject(sql, new ColumnMapRowMapper(), null);
            if (data == null) {
                throw new RuntimeException("检查" + tableName + "表是否存在");
            }
        } catch (Exception e) {
            throw new RuntimeException("检查" + tableName + "表是否存在");
        }
        return (long) data.get("cnt");
    }

    /**
     * 插入一条数据
     *
     * @param metadataCollectionVo
     * @param values
     */
    public static String insert(MetadataCollectionVo metadataCollectionVo, Map<String, Object> values) {
        String tableName = metadataCollectionVo.getMdcPrefix() + metadataCollectionVo.getEnName() + metadataCollectionVo.getMdcVersion();

        String id = IdWorker.getIdStr();
        values.put("id_", id);

        StringBuilder sb = new StringBuilder();
        sb.append("INSERT INTO `").append(tableName).append("` (");
        List<Object> params = new ArrayList<>();
        for (String columnName : values.keySet()) {
            sb.append("`").append(columnName).append("`,");
            params.add(values.get(columnName));
        }
        sb.deleteCharAt(sb.length() - 1)
                .append(") VALUES (")
                .append(StrUtil.repeatAndJoin("?", params.size(), ","))
                .append(");");
        try {
            staticJdbcTemplate.update(sb.toString(), params.toArray());
        } catch (Exception e) {
            throw new RuntimeException("保存数据出错", e);
        }
        return id;
    }

    public static void deleteById(MetadataCollectionVo metadataCollectionVo, String id) {
        Table table = parseTable(metadataCollectionVo);
        String sql = "DELETE FROM " + table.getFullTableName()
                + " WHERE `" + table.getPrimaryKeyColumn().getColumnName() + "` = ?;";
        try {
            staticJdbcTemplate.update(sql, id);
        } catch (Exception e) {
            throw new RuntimeException("执行SQL语句出错：", e);
        }
    }

    public static void updateById(MetadataCollectionVo metadataCollectionVo, String id, Map<String, Object> values) {
        Table table = parseTable(metadataCollectionVo);
        StringBuilder sb = new StringBuilder();
        sb.append("UPDATE ").append(table.getFullTableName()).append(" SET ");
        List<Object> params = new ArrayList<>();
        for (String columnName : values.keySet()) {
            sb.append("`").append(columnName).append("` = ?,");
            params.add(values.get(columnName));
        }
        sb.deleteCharAt(sb.length() - 1).append(" WHERE `")
                .append(table.getPrimaryKeyColumn().getColumnName()).append("` = ?;");
        params.add(id);
        try {
            staticJdbcTemplate.update(sb.toString(), params.toArray());
        } catch (Exception e) {
            throw new RuntimeException("执行SQL语句出错：", e);
        }
    }

    /**
     * 根据id查询
     *
     * @param metadataCollectionVo
     * @param id
     * @return
     */
    public static Map<String, Object> selectById(MetadataCollectionVo metadataCollectionVo, String id) {
        Table table = parseTable(metadataCollectionVo);
        String sql = "SELECT * FROM " + table.getFullTableName()
                + " WHERE `" + table.getPrimaryKeyColumn().getColumnName() + "` = ?;";
        Map<String, Object> data = staticJdbcTemplate.queryForObject(sql, new ColumnMapRowMapper(), id);
        if (data == null) {
            return null;
        }
        Map<String, Object> result = new HashMap<>();
        for (Column column : table.getColumnList()) {
            result.put(column.getColumnName(), data.get(column.getColumnName()));
        }
        return result;
    }

    /**
     * 删除表
     *
     * @param tableName
     */
    public static void dropTable(String tableName) {
        if (StrUtil.isEmpty(tableName)) {
            return;
        }
        String sql = "DROP TABLE IF EXISTS `" + tableName + "`;";
        log.info("执行SQL语句：" + sql);
        try {
            staticJdbcTemplate.update(sql);
        } catch (Exception e) {
            throw new RuntimeException("公用方法删除表时错误", e);
        }
    }

    /**
     * 创建表
     *
     * @param table
     * @return
     */
    private static void createTable(Table table) {
        StringBuilder sb = new StringBuilder();
        sb.append("CREATE TABLE IF NOT EXISTS ").append(table.getFullTableName()).append(" ( ");
        if (CollectionUtil.isNotEmpty(table.getColumnList())) {
            for (Column column : table.getColumnList()) {
                sb.append(columnDefinition(column, false));
                sb.append(",");
            }
        }
        if (CollectionUtil.isNotEmpty(table.getIndexList())) {
            for (Index index : table.getIndexList()) {
                sb.append(indexDefinition(index));
                sb.append(",");
            }
        }
        sb.deleteCharAt(sb.length() - 1);
        sb.append(" ) COMMENT '").append(table.getComment()).append("' ENGINE=InnoDB CHARSET=utf8; ");
        log.info("执行SQL语句：" + sb.toString());
        try {
            staticJdbcTemplate.update(sb.toString());
        } catch (Exception e) {
            throw new RuntimeException("公用方法生成表时错误", e);
        }
    }

    private static String checkTableType(Table table, Table oldTable) {
        StringBuilder message = new StringBuilder();
        if (CollectionUtil.isNotEmpty(table.getColumnList())) {
            for (Column column : table.getColumnList()) {
                if (CollectionUtil.isNotEmpty(oldTable.getColumnList())) {
                    for (Column oldColumn : oldTable.getColumnList()) {
                        // 相同字段
                        if (ObjectUtil.equal(column.getColumnName(), oldColumn.getColumnName())) {
                            // 类型变更
                            if (!column.dataType.name().equals(oldColumn.dataType.name())) {
                                message.append("【");
                                message.append(column.comment);
                                message.append(column.columnName);
                                message.append("】类型被变更、");
                            } else {
                                // 新长度小于旧长度
                                if (column.length < oldColumn.length) {
                                    message.append("【");
                                    message.append(column.comment);
                                    message.append(column.columnName);
                                    message.append("】长度被减小、");
                                }
                            }
                        }
                    }
                }
            }
        }
        if (message.length() > 0) {
            return "数据已存在，请修改元数据后重试。原因：" + message.substring(0, message.length() - 1);
        } else {
            return "";
        }
    }

    /**
     * 修改表
     *
     * @param table
     * @param oldTable
     * @return
     */
    private static void alterTable(Table table, Table oldTable) {
        if (CollectionUtil.isEmpty(table.getColumnList()) && CollectionUtil.isEmpty(oldTable.getColumnList())) {
            return;
        }
        StringBuilder sb = new StringBuilder();
        boolean alterFlag = false;

        sb.append("ALTER TABLE ").append(table.getFullTableName());

        if (CollectionUtil.isNotEmpty(table.getColumnList())) {
            for (Column column : table.getColumnList()) {
                boolean exist = false;
                if (CollectionUtil.isNotEmpty(oldTable.getColumnList())) {
                    for (Column oldColumn : oldTable.getColumnList()) {
                        // ID相等
                        if (ObjectUtil.equal(column.getId(), oldColumn.getId())) {
                            exist = true;
                            if (!column.equals(oldColumn)) {
                                alterFlag = true;
                                if (column.columnName.equals(oldColumn.columnName)) {
                                    sb.append(" MODIFY ").append(columnDefinition(column, true)).append(",");
                                } else {
                                    sb.append(" CHANGE COLUMN ").append("`" + oldColumn.columnName + "` ").append(columnDefinition(column, true)).append(",");
                                }
                            }
                            break;
                        }
                    }
                }
                if (!exist) {
                    alterFlag = true;
                    sb.append(" ADD COLUMN ").append(columnDefinition(column, false)).append(",");
                }
            }
        }

        // 处理删除字段
        if (CollectionUtil.isNotEmpty(oldTable.getColumnList())) {
            for (Column oldColumn : oldTable.getColumnList()) {
                boolean exist = false;
                if (CollectionUtil.isNotEmpty(table.getColumnList())) {
                    for (Column column : table.getColumnList()) {
                        if (ObjectUtil.equal(column.getId(), oldColumn.getId())) {
                            exist = true;
                            break;
                        }
                    }
                }
                if (!exist) {
                    alterFlag = true;
                    sb.append(" DROP COLUMN ").append("`").append(oldColumn.getColumnName()).append("`").append(",");
                }
            }
        }

        if (ObjectUtil.notEqual(table.getComment(), oldTable.getComment())) {
            alterFlag = true;
            sb.append(" COMMENT = '").append(table.getComment()).append("',");
        }

        if (!alterFlag) {
            return;
        }
        sb.deleteCharAt(sb.length() - 1).append("; ");
        log.info("执行SQL语句：" + sb.toString());
        try {
            staticJdbcTemplate.update(sb.toString());
        } catch (Exception e) {
            throw new RuntimeException("公用方法生成表时错误", e);
        }
    }

    /**
     * 拼接列的定义
     *
     * @param column  列
     * @param updated 如果是更新表结构，传入true，表示不拼接主键的定义，避免数据库返回多主键sql错误
     * @return
     */
    private static StringBuilder columnDefinition(Column column, boolean updated) {
        StringBuilder sb = new StringBuilder();
        sb.append("`").append(column.getColumnName()).append("` ");
        sb.append(column.getDataType().name());
        //处理类型后带有长度表示的括号，非指定长度的不在处理的范围内
        if (column.typeHasLength() && column.getLength() != 0) {
            sb.append("(");
            sb.append(column.getLength());
            if (column.decimalType() && column.getDecimals() != 0) {
                sb.append(",").append(column.getDecimals());
            }
            sb.append(") ");

            if (column.unsigned) {
                sb.append(" UNSIGNED ");
            }

            if (column.zerofill) {
                sb.append(" ZEROFILL ");
            }
        }

        if (column.notNull) {
            sb.append(" NOT NULL ");
        } else {
            sb.append(" NULL ");
        }
        //跳过不支持设置默认值的类型和主键
        if (column.defaultType() && column.defaultValue != null) {
            sb.append(" DEFAULT ").append(wrapValue(column.getDefaultValue())).append(" ");
        }

        if (column.increment) {
            sb.append(" AUTO_INCREMENT ");
        }

        if (column.unique) {
            sb.append(" UNIQUE ");
        }

        if (!updated && column.primaryKey) {
            sb.append(" PRIMARY KEY ");
        }
        if (column.comment != null) {
            sb.append(" COMMENT '");
            sb.append(column.comment);
            sb.append("' ");
        }
        return sb;
    }

    /**
     * 将参数值转换为mysql语句中的字符串
     *
     * @param defaultValue
     * @return
     */
    private static String wrapValue(Object defaultValue) {
        if (defaultValue == null) {
            return "NULL";
        }
        if (defaultValue instanceof CharSequence) {
            return "'" + defaultValue.toString() + "'";
        } else if (defaultValue instanceof Number) {
            return defaultValue.toString();
        } else if (defaultValue instanceof Boolean) {
            return Boolean.valueOf(defaultValue.toString()) ? "1" : "0";
        } else if (defaultValue == Keyword.CURRENT_TIMESTAMP) {
            return "CURRENT_TIMESTAMP";
        } else if (defaultValue == Keyword.CURRENT_TIMESTAMP_ON_UPDATE) {
            return "CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP";
        }
        return defaultValue.toString();
    }

    private static StringBuilder indexDefinition(Index index) {
        StringBuilder sb = new StringBuilder();
        sb.append("INDEX `").append(index.getIndexName()).append("` (");
        for (String column : index.getColumnName()) {
            sb.append("`").append(column).append("`,");
        }
        sb.deleteCharAt(sb.length() - 1).append(")");
        return sb;
    }

    private static Table parseTable(MetadataCollectionVo metadataCollectionVo) {
        String tableName = metadataCollectionVo.getMdcPrefix() + metadataCollectionVo.getEnName() + metadataCollectionVo.getMdcVersion();
        Table table = new Table(tableName);
        table.setComment(metadataCollectionVo.getName());

        table.setColumnList(new ArrayList<>());
        table.setIndexList(new ArrayList<>());

        table.getColumnList().add(primaryKeyColumn());

        if (CollectionUtil.isEmpty(metadataCollectionVo.getMetadataList())) {
            return table;
        }

        for (MetadataCollectionMetadataVo metadataVo : metadataCollectionVo.getMetadataList()) {
            table.getColumnList().add(parseColumn(metadataVo));
            if (metadataVo.getUseIndex()) {
                table.getIndexList().add(parseIndex(metadataVo));
            }
        }

        return table;
    }

    private static Map<String, Table> parseTable(List<MetadataCollectionVo> metadataCollectionVoList) {
        Map<String, Table> tableMap = new HashMap<>();
        Map<String, Map<String, Column>> columnsMap = new HashMap<>();
        Map<String, Map<String, Index>> indexMap = new HashMap<>();

        if (CollectionUtil.isEmpty(metadataCollectionVoList)) {
            return null;
        }

        for (MetadataCollectionVo collectionVo : metadataCollectionVoList) {
            String tableName = collectionVo.getMdcPrefix() + collectionVo.getEnName() + collectionVo.getMdcVersion();
            Table table = new Table(tableName);
            table.setComment(collectionVo.getName());
            tableMap.put(tableName, table);

            if (columnsMap.get(tableName) == null) {
                columnsMap.put(tableName, new HashMap<>());
                indexMap.put(tableName, new HashMap<>());

                Column primaryKey = primaryKeyColumn();
                columnsMap.get(tableName).put(primaryKey.getColumnName(), primaryKey);
            }

            if (CollectionUtil.isEmpty(collectionVo.getMetadataList())) {
                break;
            }

            for (MetadataCollectionMetadataVo metadataVo : collectionVo.getMetadataList()) {
                Column column = parseColumn(metadataVo);
                if (Objects.nonNull(column)) {
                    columnsMap.get(tableName).put(metadataVo.getFieldName(), column);
                }
                if (metadataVo.getUseIndex()) {
                    Index index = parseIndex(metadataVo);
                    if (Objects.nonNull(index)) {
                        indexMap.get(tableName).put(metadataVo.getFieldName(), index);
                    }
                }
            }
        }

        for (Map.Entry<String, Table> tableEntry : tableMap.entrySet()) {
            tableEntry.getValue().setColumnList(new ArrayList<>(columnsMap.get(tableEntry.getKey()).values()));
            tableEntry.getValue().setIndexList(new ArrayList<>(indexMap.get(tableEntry.getKey()).values()));
        }

        return tableMap;
    }

    private static Column parseColumn(MetadataCollectionMetadataVo metadataVo) {
        MetadataVo metadata = metadataVo.getMetadata();
        if (Objects.isNull(metadata)) {
            return null;
        }
        // 数据类型
        DataType dataType = parseDataType(metadata.getDataType());

        // 根据是否必填判断notNull：因不同版本间字段可能存在差异，数据库不做此类限制
        Column column = new Column(metadataVo.getFieldName(), dataType, false);
        column.setId(metadataVo.getMetadataId());
        // 数据长度
        String length = metadata.getDataLength();
        if (StrUtil.isNotBlank(length)) {
            column.setLength(Integer.parseInt(length));
        } else if (column.typeHasLength()) {
            column.setDefaultLength();
        }

        column.setComment(metadata.getName());

        // todo 默认值

        return column;
    }

    private static Index parseIndex(MetadataCollectionMetadataVo metadataVo) {
        if (!metadataVo.getUseIndex()) {
            return null;
        }
        Index index = new Index();
        index.setColumnName(new String[]{metadataVo.getFieldName()});
        index.setIndexName(metadataVo.getFieldName() + "_index");
        return index;
    }

    /**
     * 从数据库返回的类型字符串中解析出类型
     *
     * @param t
     * @return
     */
    private static DataType parseDataType(String t) {
        String typeName = null;
        if ("string".equals(t) || "text".equals(t) || "file".equals(t) || "image".equals(t)) {
            typeName = "VARCHAR";
        } else if ("date".equals(t)) {
            typeName = "DATETIME";
        } else {
            typeName = t.toUpperCase();
        }
        return DataType.valueOf(typeName);
    }

    private static Table getTableFromDB(String tableName) {
        Map<String, Table> tableMap = getExistTableFromDB(Collections.singletonList(tableName));
        if (CollectionUtil.isEmpty(tableMap)) {
            return null;
        }
        return tableMap.get(tableName);
    }

    private static Map<String, Table> getExistTableFromDB(Collection<String> tableNameList) {
        Map<String, Table> tableMap = new HashMap<>();
        try {
            DatabaseMetaData databaseMetaData = staticJdbcUtils.getMetaData();

            ResultSet tableSet = databaseMetaData.getTables(databaseName, null, null, new String[]{"TABLE"});
            while(tableSet.next()) {
                String tableName = tableSet.getString("TABLE_NAME");
                if (tableNameList.contains(tableName)) {
                    Table table = new Table(tableName);
                    table.setComment(tableSet.getString("REMARKS"));

                    List<Column> columnList = new ArrayList<>();
                    ResultSet columnSet = databaseMetaData.getColumns(databaseName, null, tableName, null);
                    while (columnSet.next()) {
                        Column column = new Column(columnSet.getString("COLUMN_NAME"),
                                DataType.valueOf(columnSet.getString("TYPE_NAME")),
                                columnSet.getInt("NULLABLE") == 0);
                        if (column.typeHasLength()) {
                            column.setLength(columnSet.getInt("COLUMN_SIZE"));
                        }
                        column.setDefaultValue(columnSet.getString("COLUMN_DEF"));
                        column.setComment(columnSet.getString("REMARKS"));
                        columnList.add(column);
                    }

                    String primaryKeyName = null;
                    ResultSet pkSet = databaseMetaData.getPrimaryKeys(databaseName, null, tableName);
                    while (pkSet.next()) {
                        primaryKeyName = pkSet.getString("COLUMN_NAME");
                    }
                    if (StrUtil.isNotBlank(primaryKeyName)) {
                        for (Column column : columnList) {
                            if (ObjectUtil.equal(primaryKeyName, column.getColumnName())) {
                                column.setPrimaryKey(true);
                                break;
                            }
                        }
                    }

                    table.setColumnList(columnList);
                    tableMap.put(tableName, table);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("查询表信息失败：", e);
        } finally {
            staticJdbcUtils.close();
        }

        return tableMap;
    }

    private static Column primaryKeyColumn() {
        Column column = new Column("id_", DataType.VARCHAR, 64, null, true);
        column.setPrimaryKey(true);
        column.setComment("主键");
        return column;
    }

    /**
     * 将mysql语句中的默认值转换为java对象
     *
     * @param defaultValue
     * @param extra
     * @param type
     * @return
     */
    private Object unwrapValue(String defaultValue, String extra, DataType type) {
        if (defaultValue == null) {
            return null;
        }
        //TODO 转换为对象
        if ((type == DataType.DATETIME
                || type == DataType.TIMESTAMP)
                && "CURRENT_TIMESTAMP".equalsIgnoreCase(defaultValue)) {
            if (extra.contains("on update CURRENT_TIMESTAMP")) {
                return Keyword.CURRENT_TIMESTAMP_ON_UPDATE;
            }
            return Keyword.CURRENT_TIMESTAMP;
        }
        if (type == DataType.BIT) {
            if ("deleteFilterGroup'0'".equalsIgnoreCase(defaultValue)) {
                return false;
            }
            if ("deleteFilterGroup'1'".equalsIgnoreCase(defaultValue)) {
                return true;
            }
            return null;
        }

        if (type == DataType.INT) {
            return Integer.valueOf(defaultValue);
        }
        if (type == DataType.BIGINT) {
            return Long.valueOf(defaultValue);
        }
        return defaultValue;
    }

    /**
     * MYSQL 关键字
     */
    private enum Keyword {
        /**
         * 当前时间
         */
        CURRENT_TIMESTAMP,
        /**
         * 当前时间，并当数据 update 时自动更新
         */
        CURRENT_TIMESTAMP_ON_UPDATE
    }

    /**
     * MYSQL 数据类型
     */
    private enum DataType {

        //        Numeric Types	Description
        TINYINT,//A very small integer
        SMALLINT,//A small integer
        MEDIUMINT,//A medium-sized integer
        INT,//A standard integer
        BIGINT,//A large integer
        DECIMAL,//A fixed-point number
        FLOAT,//A single-precision floating point number
        DOUBLE,//A double-precision floating point number
        BIT,//A bit field


        //        String Types	Description
        CHAR,//A fixed-length nonbinary (character) string
        VARCHAR,//A variable-length non-binary string
        BINARY,//A fixed-length binary string
        VARBINARY,//A variable-length binary string
        TINYBLOB,//A very small BLOB (binary large object)
        BLOB,//A small BLOB
        MEDIUMBLOB,//A medium-sized BLOB
        LONGBLOB,//A large BLOB
        TINYTEXT,//A very small non-binary string
        TEXT,//A small non-binary string
        MEDIUMTEXT,//A medium-sized non-binary string
        LONGTEXT,//A large non-binary string
        ENUM,//An enumeration; each column value may be assigned one enumeration member
        SET,//A set; each column value may be assigned zero or more SET members


        //        Date and Time Types	Description
        DATE,  //A date value in CCYY-MM-DD format
        TIME,  //A time value in hh:mm:ss format
        DATETIME,  //A date and time value inCCYY-MM-DD hh:mm:ssformat
        TIMESTAMP,  //A timestamp value in CCYY-MM-DD hh:mm:ss format
        YEAR,  //A year value in CCYY or YY format


        //        Spatial Data Types	Description
        GEOMETRY,       //A spatial value of any type
        POINT,       //A point (addFilter pair of X-Y coordinates)
        LINESTRING,       //A curve (one or more POINT values)
        POLYGON,       //A polygon
        GEOMETRYCOLLECTION,       //A collection of GEOMETRYvalues
        MULTILINESTRING,       //A collection of LINESTRINGvalues
        MULTIPOINT,       //A collection of POINTvalues
        MULTIPOLYGON,       //A collection of POLYGONvalues


        JSON;

    }

    /**
     * 表信息
     */
    private static class Table {

        private String catalog;
        private String tableName;
        private String comment;
        private List<Column> columnList;
        private List<Index> indexList;


        public Table(String tableName) {
            this.tableName = tableName;
        }

        public String getComment() {
            return comment;
        }

        public void setComment(String comment) {
            this.comment = comment;
        }

        public List<Column> getColumnList() {
            return columnList;
        }

        public void setColumnList(List<Column> columnList) {
            this.columnList = columnList;
        }

        public void addColumn(Column... columns) {
            if (columnList == null) {
                columnList = new ArrayList<>();
            }
            columnList.addAll(Arrays.asList(columns));
        }

        public String getCatalog() {
            return catalog;
        }

        public void setCatalog(String catalog) {
            this.catalog = catalog;
        }

        public String getTableName() {
            return tableName;
        }

        public void setTableName(String tableName) {
            this.tableName = tableName;
        }

        public String getFullTableName() {
            StringBuilder tableName = new StringBuilder();
            if (StringUtils.isNotBlank(getCatalog())) {
                tableName.append(getCatalog()).append(".");
            }
            tableName.append("`").append(getTableName()).append("`");
            return tableName.toString();
        }

        public List<Index> getIndexList() {
            return indexList;
        }

        public void setIndexList(List<Index> indexList) {
            this.indexList = indexList;
        }

        public Column getColumn(String columnName) {
            if (this.getColumnList() == null) {
                return null;
            }
            for (Column column : this.getColumnList()) {
                if (column.getColumnName().equalsIgnoreCase(columnName)) {
                    return column;
                }
            }
            return null;
        }

        public Column getPrimaryKeyColumn() {
            for (Column column : this.columnList) {
                if (column.primaryKey) {
                    return column;
                }
            }
            return null;
        }
    }

    /**
     * 列信息
     */
    private static class Column {

        private String id;
        private String columnName;
        private DataType dataType;
        private boolean primaryKey;
        private boolean increment;
        private int length;
        private int decimals;
        private Object defaultValue;
        private boolean notNull;
        private boolean unique;
        private String comment;
        private boolean unsigned;
        private boolean zerofill;

        public Column(String columnName, DataType dataType, boolean notNull) {
            this.columnName = columnName;
            this.dataType = dataType;
            this.notNull = notNull;
        }

        public Column(String columnName, DataType dataType, Object defaultValue, boolean notNull) {
            this.columnName = columnName;
            this.dataType = dataType;
            this.length = length;
            this.defaultValue = defaultValue;
            this.notNull = notNull;
        }

        public Column(String columnName, DataType dataType, int length, Object defaultValue, boolean notNull) {
            this.columnName = columnName;
            this.dataType = dataType;
            this.length = length;
            this.defaultValue = defaultValue;
            this.notNull = notNull;
        }

        public void setId(String id) {
            this.id = id;
        }
        public String getId() {
            return this.id;
        }
        public boolean isUnsigned() {
            return unsigned;
        }

        public Column setUnsigned(boolean unsigned) {
            this.unsigned = unsigned;
            return this;
        }

        public boolean isZerofill() {
            return zerofill;
        }

        public Column setZerofill(boolean zerofill) {
            this.zerofill = zerofill;
            return this;
        }

        public boolean isUnique() {
            return unique;
        }

        public Column setUnique(boolean unique) {
            this.unique = unique;
            return this;
        }

        public String getComment() {
            return comment;
        }

        public Column setComment(String comment) {
            this.comment = comment;
            return this;
        }

        public int getDecimals() {
            return decimals;
        }

        public Column setDecimals(int decimals) {
            this.decimals = decimals;
            return this;
        }

        public String getColumnName() {
            return columnName;
        }

        public Column setColumnName(String columnName) {
            this.columnName = columnName;
            return this;
        }

        public DataType getDataType() {
            return dataType;
        }

        public Column setDataType(DataType dataType) {
            this.dataType = dataType;
            return this;
        }

        public boolean isPrimaryKey() {
            return primaryKey;
        }

        public Column setPrimaryKey(boolean primaryKey) {
            this.primaryKey = primaryKey;
            return this;
        }

        public boolean isIncrement() {
            return increment;
        }

        public Column setIncrement(boolean increment) {
            this.increment = increment;
            return this;
        }

        public int getLength() {
            return length;
        }

        public Column setLength(int length) {
            this.length = length;
            return this;
        }

        public Object getDefaultValue() {
            return defaultValue;
        }

        public Column setDefaultValue(Object defaultValue) {
            this.defaultValue = defaultValue;
            return this;
        }

        public boolean isNotNull() {
            return notNull;
        }

        public Column setNotNull(boolean notNull) {
            this.notNull = notNull;
            return this;
        }

        public boolean typeHasLength() {
            return this.dataType == DataType.INT
                    || this.dataType == DataType.TINYINT
                    || this.dataType == DataType.SMALLINT
                    || this.dataType == DataType.BIGINT
                    || this.dataType == DataType.MEDIUMINT
                    || this.dataType == DataType.DECIMAL
                    || this.dataType == DataType.CHAR
                    || this.dataType == DataType.VARCHAR
                    || this.dataType == DataType.BINARY
                    || this.dataType == DataType.VARBINARY;
        }

        public void setDefaultLength() {
            switch (this.dataType) {
                case INT:
                    this.length = 11;
                    break;
                case VARCHAR:
                    this.length = 255;
                    break;
                default:
                    break;
            }
        }

        public boolean decimalType() {
            return this.dataType == DataType.FLOAT || this.dataType == DataType.DOUBLE
                    || this.dataType == DataType.DECIMAL;
        }

        public boolean defaultType() {
            return !this.primaryKey && this.dataType != DataType.TEXT && this.dataType != DataType.MEDIUMTEXT
                    && this.dataType != DataType.LONGTEXT && this.dataType != DataType.BLOB
                    && this.dataType != DataType.MEDIUMBLOB && this.dataType != DataType.LONGBLOB
                    && this.dataType != DataType.JSON && this.dataType != DataType.GEOMETRY;
        }

        /**
         * 获取根据列名返回的驼峰命名属性名
         *
         * @return
         */
        public String getCamelCaseName() {
            StringBuilder result = new StringBuilder();
            if (columnName != null && columnName.length() > 0) {
                boolean flag = false;
                for (int i = 0; i < columnName.length(); i++) {
                    char ch = columnName.charAt(i);
                    if ("_".charAt(0) == ch) {
                        flag = true;
                    } else {
                        if (flag) {
                            result.append(Character.toUpperCase(ch));
                            flag = false;
                        } else {
                            result.append(ch);
                        }
                    }
                }
            }
            return result.toString();
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }
            Column column = (Column) obj;
            return ObjectUtil.equal(columnName, column.columnName)
                    && ObjectUtil.equal(dataType, column.dataType)
                    && ObjectUtil.equal(primaryKey, column.primaryKey)
                    && ObjectUtil.equal(increment, column.increment)
                    && ObjectUtil.equal(length, column.length)
                    && ObjectUtil.equal(decimals, column.decimals)
                    && ObjectUtil.equal(defaultValue, column.defaultValue)
                    && ObjectUtil.equal(notNull, column.notNull)
                    && ObjectUtil.equal(unique, column.unique)
                    && ObjectUtil.equal(comment, column.comment)
                    && ObjectUtil.equal(unsigned, column.unsigned)
                    && ObjectUtil.equal(zerofill, column.zerofill);
        }
    }

    /**
     * 索引
     */
    static class Index {

        private String[] columnName;

        private String indexName;

        public String[] getColumnName() {
            return columnName;
        }

        public void setColumnName(String[] columnName) {
            this.columnName = columnName;
        }

        public String getIndexName() {
            return indexName;
        }

        public void setIndexName(String indexName) {
            this.indexName = indexName;
        }
    }
}
