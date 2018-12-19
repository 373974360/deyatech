package org.land.admin.service;

import org.land.admin.entity.RoleMenu;
import org.land.admin.vo.RoleMenuVo;
import org.land.common.base.BaseService;
import java.util.Collection;
import java.util.List;

/**
 * <p>
 *  系统角色菜单关联信息 服务类
 * </p>
 *
 * @Author lee.
 * @since 2018-12-19
 */
public interface RoleMenuService extends BaseService<RoleMenu> {

    /**
     * 单个将对象转换为vo系统角色菜单关联信息
     *
     * @param roleMenu
     * @return
     */
    RoleMenuVo setVoProperties(RoleMenu roleMenu);

    /**
     * 批量将对象转换为vo系统角色菜单关联信息
     *
     * @param roleMenus
     * @return
     */
    List<RoleMenuVo> setVoProperties(Collection roleMenus);
}
