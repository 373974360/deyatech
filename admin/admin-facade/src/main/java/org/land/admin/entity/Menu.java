package org.land.admin.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableField;
import org.land.common.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
/**
 * <p>
 * 系统菜单信息
 * </p>
 *
 * @author lee.
 * @since 2018-12-19
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("admin_menu")
public class Menu extends BaseEntity {

    /**
    * 菜单名称
    */
    @TableField("name_")
    private String name;

    /**
    * 菜单类型(0:CURD;1:系统菜单;2:业务菜单;)
    */
    @TableField("type_")
    private Integer type;

    /**
    * 上级菜单编号
    */
    @TableField("parent_id")
    private String parentId;

    /**
    * 树结构中的索引位置
    */
    @TableField("tree_position")
    private String treePosition;

    /**
    * 节点图标CSS类名
    */
    @TableField("iconcls_")
    private String iconcls;

    /**
    * 请求地址
    */
    @TableField("request_")
    private String request;

    /**
    * 排序号
    */
    @TableField("sort_no")
    private Integer sortNo;

    /**
    * 权限标识
    */
    @TableField("permission_")
    private String permission;

}
