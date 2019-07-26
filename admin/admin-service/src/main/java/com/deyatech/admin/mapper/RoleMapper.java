package com.deyatech.admin.mapper;

import com.deyatech.admin.entity.Role;
import com.deyatech.common.base.BaseMapper;

/**
 * <p>
 * 系统角色信息 Mapper 接口
 * </p>
 *
 * @Author lee.
 * @since 2019-03-07
 */
public interface RoleMapper extends BaseMapper<Role> {


    /**
     * 根据角色id查询角色对应的用户个数
     * @param roleId
     * @return
     */
    int getRoleUsersCountByRoleId(String roleId);

}
