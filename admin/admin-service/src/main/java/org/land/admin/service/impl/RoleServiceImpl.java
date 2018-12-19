package org.land.admin.service.impl;

import org.land.admin.entity.Role;
import org.land.admin.vo.RoleVo;
import org.land.admin.mapper.RoleMapper;
import org.land.admin.service.RoleService;
import org.land.common.base.BaseServiceImpl;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Collection;

/**
 * <p>
 * 系统角色信息 服务实现类
 * </p>
 *
 * @Author lee.
 * @since 2018-12-19
 */
@Service
public class RoleServiceImpl extends BaseServiceImpl<RoleMapper, Role> implements RoleService {

    /**
     * 单个将对象转换为vo系统角色信息
     *
     * @param role
     * @return
     */
    @Override
    public RoleVo setVoProperties(Role role){
        RoleVo roleVo = new RoleVo();
        BeanUtil.copyProperties(role, roleVo);
        return roleVo;
    }

    /**
     * 批量将对象转换为vo系统角色信息
     *
     * @param roles
     * @return
     */
    @Override
    public List<RoleVo> setVoProperties(Collection roles){
        List<RoleVo> roleVos = CollectionUtil.newArrayList();
        if (CollectionUtil.isNotEmpty(roles)) {
            for (Object role : roles) {
                RoleVo roleVo = new RoleVo();
                BeanUtil.copyProperties(role, roleVo);
                roleVos.add(roleVo);
            }
        }
        return roleVos;
    }
}
