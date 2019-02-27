package org.land.admin.service.impl;

import org.land.admin.entity.UserRole;
import org.land.admin.vo.UserRoleVo;
import org.land.admin.mapper.UserRoleMapper;
import org.land.admin.service.UserRoleService;
import org.land.common.base.BaseServiceImpl;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Collection;

/**
 * <p>
 * 系统数据字典索引信息 服务实现类
 * </p>
 *
 * @Author lee.
 * @since 2019-02-27
 */
@Service
public class UserRoleServiceImpl extends BaseServiceImpl<UserRoleMapper, UserRole> implements UserRoleService {

    /**
     * 单个将对象转换为vo系统数据字典索引信息
     *
     * @param userRole
     * @return
     */
    @Override
    public UserRoleVo setVoProperties(UserRole userRole){
        UserRoleVo userRoleVo = new UserRoleVo();
        BeanUtil.copyProperties(userRole, userRoleVo);
        return userRoleVo;
    }

    /**
     * 批量将对象转换为vo系统数据字典索引信息
     *
     * @param userRoles
     * @return
     */
    @Override
    public List<UserRoleVo> setVoProperties(Collection userRoles){
        List<UserRoleVo> userRoleVos = CollectionUtil.newArrayList();
        if (CollectionUtil.isNotEmpty(userRoles)) {
            for (Object userRole : userRoles) {
                UserRoleVo userRoleVo = new UserRoleVo();
                BeanUtil.copyProperties(userRole, userRoleVo);
                userRoleVos.add(userRoleVo);
            }
        }
        return userRoleVos;
    }
}
