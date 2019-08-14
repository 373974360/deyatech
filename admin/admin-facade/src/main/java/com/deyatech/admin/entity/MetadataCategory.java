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
 * 元数据分类
 * </p>
 *
 * @author lee.
 * @since 2019-08-07
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("admin_metadata_category")
@ApiModel(value = "元数据分类对象", description = "元数据分类", parent = BaseEntity.class)
public class MetadataCategory extends BaseEntity {

    @ApiModelProperty(value = "名称", dataType = "String")
    @TableField("name_")
    private String name;

    @ApiModelProperty(value = "上级节点id", dataType = "String")
    @TableField("parent_id")
    private String parentId;

    @ApiModelProperty(value = "树结构中的位置", dataType = "String")
    @TableField("tree_position")
    private String treePosition;

    @ApiModelProperty(value = "排序", dataType = "Integer", example = "1")
    @TableField("sort_no")
    private Integer sortNo;

}
