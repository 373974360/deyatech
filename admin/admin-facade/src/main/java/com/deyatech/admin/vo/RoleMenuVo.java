package com.deyatech.admin.vo;

import com.deyatech.admin.entity.RoleMenu;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 系统角色菜单关联信息扩展对象
 * </p>
 *
 * @author lee.
 * @since 2019-03-07
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@ApiModel(value = "系统角色菜单关联信息扩展对象", description = "系统角色菜单关联信息扩展对象", parent = RoleMenu.class)
public class RoleMenuVo extends RoleMenu {
}
