package com.deyatech.admin.service.impl;

import com.deyatech.admin.entity.UserRole;
import com.deyatech.admin.vo.UserRoleVo;
import com.deyatech.admin.mapper.UserRoleMapper;
import com.deyatech.admin.service.UserRoleService;
import com.deyatech.common.base.BaseServiceImpl;
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
 * @since 2019-03-07
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
