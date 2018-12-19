package org.land.admin.vo;

import org.land.admin.entity.RoleMenu;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 系统角色菜单关联信息扩展对象
 * </p>
 *
 * @author lee.
 * @since 2018-12-19
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class RoleMenuVo extends RoleMenu {
}
