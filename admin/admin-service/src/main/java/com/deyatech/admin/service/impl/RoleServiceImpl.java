package com.deyatech.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.deyatech.admin.entity.Role;
import com.deyatech.admin.vo.RoleVo;
import com.deyatech.admin.mapper.RoleMapper;
import com.deyatech.admin.service.RoleService;
import com.deyatech.common.base.BaseServiceImpl;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Collection;

/**
 * <p>
 * 系统角色信息 服务实现类
 * </p>
 *
 * @Author lee.
 * @since 2019-03-07
 */
@Service
public class RoleServiceImpl extends BaseServiceImpl<RoleMapper, Role> implements RoleService {


    @Autowired
    RoleMapper roleMapper;

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
                int count = roleMapper.getRoleUsersCountByRoleId(roleVo.getId());
                roleVo.setRoleUsersCount(count);
                roleVos.add(roleVo);
            }
        }
        return roleVos;
    }



    /**
     * 根据名称模糊查询
     * @param entity
     * @return
     */
    @Override
    public IPage<Role> pageByBean(Role entity) {
        QueryWrapper queryWrapper = new QueryWrapper();
        if (StringUtils.isNotEmpty(entity.getName())) {
            queryWrapper.like("name_", entity.getName());
        }
        return super.page(getPageByBean(entity), queryWrapper);
    }
}
