package org.land.admin.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableField;
import org.land.common.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import java.util.Date;
/**
 * <p>
 * 系统角色菜单关联信息
 * </p>
 *
 * @author lee.
 * @since 2019-02-27
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("admin_role_menu")
@ApiModel(value="系统角色菜单关联信息对象", description="系统角色菜单关联信息" ,parent = BaseEntity.class)
public class RoleMenu extends BaseEntity {

    @ApiModelProperty(value = "角色id",dataType = "String")
    @TableField("role_id")
    private String roleId;

    @ApiModelProperty(value = "菜单id",dataType = "String")
    @TableField("menu_id")
    private String menuId;

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
