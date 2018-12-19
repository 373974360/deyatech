package org.land.admin.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableField;
import org.land.common.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
/**
 * <p>
 * 系统部门信息
 * </p>
 *
 * @author lee.
 * @since 2018-12-19
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("admin_dept")
public class Dept extends BaseEntity {

    /**
    * 部门名称
    */
    @TableField("name_")
    private String name;

    /**
    * 部门名称简称
    */
    @TableField("short_name")
    private String shortName;

    /**
    * 部门编码
    */
    @TableField("dept_code")
    private String deptCode;

    /**
    * 上级部门编号
    */
    @TableField("parent_id")
    private String parentId;

    /**
    * 树结构中的索引位置
    */
    @TableField("tree_position")
    private String treePosition;

    /**
    * 排序号
    */
    @TableField("sort_no")
    private Integer sortNo;

}
