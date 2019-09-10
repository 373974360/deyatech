package com.deyatech.admin.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableField;
import com.deyatech.common.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
/**
 * <p>
 * 元数据
 * </p>
 *
 * @author lee.
 * @since 2019-08-07
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("admin_metadata")
@ApiModel(value = "元数据对象", description = "元数据", parent = BaseEntity.class)
public class Metadata extends BaseEntity {

    @ApiModelProperty(value = "中文名称", dataType = "String")
    @TableField("name_")
    private String name;

    @ApiModelProperty(value = "字段名", dataType = "String")
    @TableField("brief_name")
    private String briefName;

    @ApiModelProperty(value = "英文名", dataType = "String")
    @TableField("en_name")
    private String enName;

    @ApiModelProperty(value = "类型", dataType = "Integer", example = "1")
    @TableField("type_")
    private Integer type;

    @ApiModelProperty(value = "数据类型", dataType = "String")
    @TableField("data_type")
    private String dataType;

    @ApiModelProperty(value = "控件类型", dataType = "String")
    @TableField("control_type")
    private String controlType;

    @ApiModelProperty(value = "数据长度", dataType = "String")
    @TableField("data_length")
    private String dataLength;

    @ApiModelProperty(value = "校验方式", dataType = "String")
    @TableField("check_model")
    private String checkModel;

    @ApiModelProperty(value = "数据来源", dataType = "String")
    @TableField("data_source")
    private String dataSource;

    @ApiModelProperty(value = "关联数据字典 id", dataType = "String")
    @TableField("dictionary_id")
    private String dictionaryId;

    @ApiModelProperty(value = "定义", dataType = "String")
    @TableField("definition_")
    private String definition;

    @ApiModelProperty(value = "值域", dataType = "String")
    @TableField("field_")
    private String field;

    @ApiModelProperty(value = "必填", dataType = "Boolean")
    @TableField("required_")
    private Boolean required;

    @ApiModelProperty(value = "强制字段，必选", dataType = "Boolean")
    @TableField("mandatory_")
    private Boolean mandatory;

    @ApiModelProperty(value = "是否允许多次出现", dataType = "Boolean")
    @TableField("multi_flag")
    private Boolean multiFlag;

    @ApiModelProperty(value = "出现次数", dataType = "Integer", example = "1")
    @TableField("annotation_count")
    private Integer annotationCount;

    @ApiModelProperty(value = "控件长度", dataType = "Integer")
    @TableField("control_length")
    private Integer controlLength;

    @ApiModelProperty(value = "分类id", dataType = "String")
    @TableField("category_id")
    private String categoryId;

    @ApiModelProperty(value = "复合元数据id", dataType = "String")
    @TableField("relation_id")
    private String relationId;

}
