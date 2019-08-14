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
 * 元数据集
 * </p>
 *
 * @author lee.
 * @since 2019-08-07
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("admin_metadata_collection")
@ApiModel(value = "元数据集对象", description = "元数据集", parent = BaseEntity.class)
public class MetadataCollection extends BaseEntity {

    @ApiModelProperty(value = "名称", dataType = "String")
    @TableField("name_")
    private String name;

    @ApiModelProperty(value = "英文名称", dataType = "String")
    @TableField("en_name")
    private String enName;

    @ApiModelProperty(value = "元数据前缀", dataType = "String")
    @TableField("md_prefix")
    private String mdPrefix;

    @ApiModelProperty(value = "元数据集前缀", dataType = "String")
    @TableField("mdc_prefix")
    private String mdcPrefix;

    @ApiModelProperty(value = "来源系统", dataType = "String")
    @TableField("source_")
    private String source;

    @ApiModelProperty(value = "多租户标识", dataType = "String")
    @TableField("tenant_flag")
    private String tenantFlag;

    @ApiModelProperty(value = "版本", dataType = "String")
    @TableField("mdc_version")
    private String mdcVersion;

    @ApiModelProperty(value = "是否主版本", dataType = "Boolean")
    @TableField("main_version")
    private Boolean mainVersion;

    @ApiModelProperty(value = "父集id", dataType = "String")
    @TableField("parent_id")
    private String parentId;
}
