package com.deyatech.admin.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableField;
import com.deyatech.common.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * <p>
 * 元数据集元数据关联
 * </p>
 *
 * @author lee.
 * @since 2019-08-07
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("admin_metadata_collection_metadata")
@ApiModel(value = "元数据集元数据关联对象", description = "元数据集元数据关联", parent = BaseEntity.class)
public class MetadataCollectionMetadata extends BaseEntity {

    @ApiModelProperty(value = "标签名称", dataType = "String")
    @TableField("label_")
    private String label;

    @ApiModelProperty(value = "字段名", dataType = "String")
    @TableField("brief_name")
    private String briefName;

    @ApiModelProperty(value = "数据类型", dataType = "String")
    @TableField("data_type")
    private String dataType;

    @ApiModelProperty(value = "数据长度", dataType = "String")
    @TableField("data_length")
    private String dataLength;

    @ApiModelProperty(value = "控件类型", dataType = "String")
    @TableField("control_type")
    private String controlType;

    @ApiModelProperty(value = "控件长度", dataType = "Integer")
    @TableField("control_length")
    private Integer controlLength;

    @ApiModelProperty(value = "校验方式", dataType = "String")
    @TableField("check_model")
    private String checkModel;

    @ApiModelProperty(value = "数据来源", dataType = "String")
    @TableField("data_source")
    private String dataSource;

    @ApiModelProperty(value = "数据字典id", dataType = "String")
    @TableField("dictionary_id")
    private String dictionaryId;

    @ApiModelProperty(value = "必填", dataType = "Boolean")
    @TableField("required_")
    private Boolean required;

    @ApiModelProperty(value = "强制字段，必选", dataType = "Boolean")
    @TableField("mandatory_")
    private Boolean mandatory;

    @ApiModelProperty(value = "是否表头", dataType = "Boolean")
    @TableField("table_head")
    private Boolean tableHead;

    @ApiModelProperty(value = "是否高级查询", dataType = "Boolean")
    @TableField("advanced_query")
    private Boolean advancedQuery;

    @ApiModelProperty(value = "是否全文检索", dataType = "Boolean")
    @TableField("use_full_index")
    private Boolean useFullIndex;

    @ApiModelProperty(value = "是否使用索引", dataType = "Boolean")
    @TableField("use_index")
    private Boolean useIndex;

    @ApiModelProperty(value = "排序", dataType = "Integer", example = "1")
    @TableField("sort_no")
    private Integer sortNo;

    @ApiModelProperty(value = "元数据集id", dataType = "String")
    @TableField("metadata_collection_id")
    private String metadataCollectionId;

    @ApiModelProperty(value = "元数据id", dataType = "String")
    @TableField("metadata_id")
    private String metadataId;

    @ApiModelProperty(value = "复合元数据id", dataType = "String")
    @TableField("relation_id")
    private String relationId;

    @ApiModelProperty(value = "元数据集版本", dataType = "String")
    @TableField("mdc_version")
    private String mdcVersion;

    /**
     * 记录状态，0为禁用，1为启用，-1为已删除
     */
    @TableField(exist = false)
    @ApiModelProperty(value = "记录状态", dataType = "Integer", notes = "0为禁用，1为启用，-1为已删除", example = "1")
    private Integer enable;

    /**
     * 备注
     */
    @TableField(exist = false)
    @ApiModelProperty(value = "备注", dataType = "String")
    private String remark;

    /**
     * 数据记录创建者
     */
    @TableField(exist = false)
    @ApiModelProperty(value = "数据记录创建者", dataType = "String", hidden = true)
    private String createBy;

    /**
     * 数据记录创建时间
     */
    @TableField(exist = false)
    @ApiModelProperty(value = "数据记录创建时间", dataType = "Date", hidden = true)
    private Date createTime;

    /**
     * 数据记录更新者
     */
    @TableField(exist = false)
    @ApiModelProperty(value = "数据记录更新者", dataType = "String", hidden = true)
    private String updateBy;

    /**
     * 数据记录更新时间
     */
    @TableField(exist = false)
    @ApiModelProperty(value = "数据记录更新时间", dataType = "Date", hidden = true)
    private Date updateTime;

    /**
     * 乐观锁字段
     */
    @TableField(exist = false)
    @ApiModelProperty(value = "乐观锁字段", dataType = "Integer", hidden = true)
    private Integer version;
}
