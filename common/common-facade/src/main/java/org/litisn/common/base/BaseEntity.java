package org.litisn.common.base;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * 基础类
 *
 * @author: litisn
 * @since: 2018-12-13 18:12
 */
@Data
@Accessors(chain = true)
public class BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID，生成规则为分布式id
     */
    @TableId(value = "id_", type = IdType.ID_WORKER_STR)
    private String id;

    /**
     * 记录状态，0为禁用，1为启用，-1为已删除
     */
    @TableLogic
    @TableField("enable_")
    private Integer enable;

    /**
     * 乐观锁字段
     */
    @Version
    @TableField("version_")
    private Integer version;

    /**
     * 备注
     */
    @TableField("remark_")
    private String remark;

    /**
     * 数据记录创建者
     */
    @TableField(value = "create_by", fill = FieldFill.INSERT)
    private String createBy;

    /**
     * 数据记录创建时间
     */
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private Date createTime;

    /**
     * 数据记录更新者
     */
    @TableField(value = "update_by", fill = FieldFill.UPDATE)
    private String updateBy;

    /**
     * 数据记录更新时间
     */
    @TableField(value = "update_time", fill = FieldFill.UPDATE)
    private Date updateTime;

    /**
     * 当前页码
     */
    @TableField(exist = false)
    private Long page;

    /**
     * 每页条数
     */
    @TableField(exist = false)
    private Long size;

    /**
     * 排序sql
     */
    @TableField(exist = false)
    private String sortSql;
}
