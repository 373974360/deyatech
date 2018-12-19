package org.land.admin.service;

import org.land.admin.entity.Menu;
import org.land.admin.vo.MenuVo;
import org.land.common.base.BaseService;
import java.util.Collection;
import java.util.List;

/**
 * <p>
 *  系统菜单信息 服务类
 * </p>
 *
 * @Author lee.
 * @since 2018-12-19
 */
public interface MenuService extends BaseService<Menu> {

    /**
     * 根据Menu对象属性检索系统菜单信息的tree对象
     *
     * @return
     */
    Collection<MenuVo> getMenuTree();

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
}
