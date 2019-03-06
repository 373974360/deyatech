package com.deyatech.admin.service;

import com.deyatech.admin.entity.Role;
import com.deyatech.admin.vo.RoleVo;
import com.deyatech.common.base.BaseService;
import java.util.Collection;
import java.util.List;

/**
 * <p>
 *  系统角色信息 服务类
 * </p>
 *
 * @Author lee.
 * @since 2019-03-06
 */
public interface RoleService extends BaseService<Role> {

    /**
     * 单个将对象转换为vo系统角色信息
     *
     * @param role
     * @return
     */
    RoleVo setVoProperties(Role role);

    /**
     * 批量将对象转换为vo系统角色信息
     *
     * @param roles
     * @return
     */
    List<RoleVo> setVoProperties(Collection roles);
}
