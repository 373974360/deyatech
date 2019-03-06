package com.deyatech.admin.service.impl;

import com.deyatech.admin.entity.RoleMenu;
import com.deyatech.admin.vo.RoleMenuVo;
import com.deyatech.admin.mapper.RoleMenuMapper;
import com.deyatech.admin.service.RoleMenuService;
import com.deyatech.common.base.BaseServiceImpl;
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
 * @since 2019-03-06
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
