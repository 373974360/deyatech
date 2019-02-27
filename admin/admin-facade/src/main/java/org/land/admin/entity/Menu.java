package org.land.admin.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableField;
import org.land.common.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
/**
 * <p>
 * 系统菜单信息
 * </p>
 *
 * @author lee.
 * @since 2019-02-27
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("admin_menu")
@ApiModel(value="系统菜单信息对象", description="系统菜单信息" ,parent = BaseEntity.class)
public class Menu extends BaseEntity {

    @ApiModelProperty(value = "菜单名称",dataType = "String")
    @TableField("name_")
    private String name;

    @ApiModelProperty(value = "菜单类型(0:CURD;1:系统菜单;2:业务菜单;)",dataType = "Integer", example = "1")
    @TableField("type_")
    private Integer type;

    @ApiModelProperty(value = "上级菜单编号",dataType = "String")
    @TableField("parent_id")
    private String parentId;

    @ApiModelProperty(value = "树结构中的索引位置",dataType = "String")
    @TableField("tree_position")
    private String treePosition;

    @ApiModelProperty(value = "节点图标CSS类名",dataType = "String")
    @TableField("iconcls_")
    private String iconcls;

    @ApiModelProperty(value = "请求地址",dataType = "String")
    @TableField("request_")
    private String request;

    @ApiModelProperty(value = "排序号",dataType = "Integer", example = "1")
    @TableField("sort_no")
    private Integer sortNo;

    @ApiModelProperty(value = "权限标识",dataType = "String")
    @TableField("permission_")
    private String permission;

}
