package org.land.admin.service.impl;

import org.land.admin.entity.Menu;
import org.land.admin.vo.MenuVo;
import org.land.admin.mapper.MenuMapper;
import org.land.admin.service.MenuService;
import org.land.common.base.BaseServiceImpl;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import org.land.common.Constants;
import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.ObjectUtil;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Collection;

/**
 * <p>
 * 系统菜单信息 服务实现类
 * </p>
 *
 * @Author lee.
 * @since 2018-12-19
 */
@Service
public class MenuServiceImpl extends BaseServiceImpl<MenuMapper, Menu> implements MenuService {

    /**
     * 根据Menu对象属性检索系统菜单信息的tree对象
     *
     * @return
     */
    @Override
    public Collection<MenuVo> getMenuTree() {
        List<MenuVo> menuVos = setVoProperties(super.list());
        List<MenuVo> rootMenus = CollectionUtil.newArrayList();
        if (CollectionUtil.isNotEmpty(menuVos)) {
            for (MenuVo menuVo : menuVos) {
                menuVo.setLabel(menuVo.getName());
                if(StrUtil.isNotBlank(menuVo.getTreePosition())){
                    String[] split = menuVo.getTreePosition().split(Constants.DEFAULT_TREE_POSITION_SPLIT);
                    menuVo.setLevel(split.length);
                }else{
                    menuVo.setLevel(Constants.DEFAULT_ROOT_LEVEL);
                }
                if (ObjectUtil.equal(menuVo.getParentId(), Constants.DEFAULT_PARENT_ROOT)) {
                    rootMenus.add(menuVo);
                }
                for (MenuVo childVo : menuVos) {
                    if (ObjectUtil.equal(childVo.getParentId(), menuVo.getId())) {
                        if (ObjectUtil.isNull(menuVo.getChildren())) {
                            List<MenuVo> children = CollectionUtil.newArrayList();
                            children.add(childVo);
                            menuVo.setChildren(children);
                        } else {
                            menuVo.getChildren().add(childVo);
                        }
                    }
                }
            }
        }
        return rootMenus;
    }

    /**
     * 单个将对象转换为vo系统菜单信息
     *
     * @param menu
     * @return
     */
    @Override
    public MenuVo setVoProperties(Menu menu){
        MenuVo menuVo = new MenuVo();
        BeanUtil.copyProperties(menu, menuVo);
        return menuVo;
    }

    /**
     * 批量将对象转换为vo系统菜单信息
     *
     * @param menus
     * @return
     */
    @Override
    public List<MenuVo> setVoProperties(Collection menus){
        List<MenuVo> menuVos = CollectionUtil.newArrayList();
        if (CollectionUtil.isNotEmpty(menus)) {
            for (Object menu : menus) {
                MenuVo menuVo = new MenuVo();
                BeanUtil.copyProperties(menu, menuVo);
                menuVos.add(menuVo);
            }
        }
        return menuVos;
    }
}
