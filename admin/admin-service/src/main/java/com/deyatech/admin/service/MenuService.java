package com.deyatech.admin.service;

import com.deyatech.admin.entity.Menu;
import com.deyatech.admin.vo.MenuVo;
import com.deyatech.common.base.BaseService;
import java.util.Collection;
import java.util.List;

/**
 * <p>
 *  系统菜单信息 服务类
 * </p>
 *
 * @Author lee.
 * @since 2019-03-06
 */
public interface MenuService extends BaseService<Menu> {

    /**
     * 根据Menu对象属性检索系统菜单信息的tree对象
     *
     * @param menu
     * @return
     */
    Collection<MenuVo> getMenuTree(Menu menu);

    /**
     * 单个将对象转换为vo系统菜单信息
     *
     * @param menu
     * @return
     */
    MenuVo setVoProperties(Menu menu);

    /**
     * 批量将对象转换为vo系统菜单信息
     *
     * @param menus
     * @return
     */
    List<MenuVo> setVoProperties(Collection menus);

    /**
     * 根据用户ID查找该用户拥有的所有权限
     * @param userId
     * @return
     */
    String[] getAllPermissionsByUserId(String userId);
}
