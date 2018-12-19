package org.land.admin.service.impl;

import org.land.admin.entity.RoleMenu;
import org.land.admin.vo.RoleMenuVo;
import org.land.admin.mapper.RoleMenuMapper;
import org.land.admin.service.RoleMenuService;
import org.land.common.base.BaseServiceImpl;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Collection;

/**
 * <p>
 * 系统角色菜单关联信息 服务实现类
 * </p>
 *
 * @Author lee.
 * @since 2018-12-19
 */
@Service
public class RoleMenuServiceImpl extends BaseServiceImpl<RoleMenuMapper, RoleMenu> implements RoleMenuService {

    /**
     * 单个将对象转换为vo系统角色菜单关联信息
     *
     * @param roleMenu
     * @return
     */
    @Override
    public RoleMenuVo setVoProperties(RoleMenu roleMenu){
        RoleMenuVo roleMenuVo = new RoleMenuVo();
        BeanUtil.copyProperties(roleMenu, roleMenuVo);
        return roleMenuVo;
    }

    /**
     * 批量将对象转换为vo系统角色菜单关联信息
     *
     * @param roleMenus
     * @return
     */
    @Override
    public List<RoleMenuVo> setVoProperties(Collection roleMenus){
        List<RoleMenuVo> roleMenuVos = CollectionUtil.newArrayList();
        if (CollectionUtil.isNotEmpty(roleMenus)) {
            for (Object roleMenu : roleMenus) {
                RoleMenuVo roleMenuVo = new RoleMenuVo();
                BeanUtil.copyProperties(roleMenu, roleMenuVo);
                roleMenuVos.add(roleMenuVo);
            }
        }
        return roleMenuVos;
    }
}
