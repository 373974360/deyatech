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
 * 系统部门信息
 * </p>
 *
 * @author lee.
 * @since 2019-03-05
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("admin_dept")
@ApiModel(value="系统部门信息对象", description="系统部门信息" ,parent = BaseEntity.class)
public class Dept extends BaseEntity {

    @ApiModelProperty(value = "部门名称",dataType = "String")
    @TableField("name_")
    private String name;

    @ApiModelProperty(value = "部门名称简称",dataType = "String")
    @TableField("short_name")
    private String shortName;

    @ApiModelProperty(value = "部门编码",dataType = "String")
    @TableField("dept_code")
    private String deptCode;

    @ApiModelProperty(value = "上级部门编号",dataType = "String")
    @TableField("parent_id")
    private String parentId;

    @ApiModelProperty(value = "树结构中的索引位置",dataType = "String")
    @TableField("tree_position")
    private String treePosition;

    @ApiModelProperty(value = "排序号",dataType = "Integer", example = "1")
    @TableField("sort_no")
    private Integer sortNo;

}
