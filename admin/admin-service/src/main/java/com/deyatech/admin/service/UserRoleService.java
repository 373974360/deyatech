package com.deyatech.admin.service;

import com.deyatech.admin.entity.UserRole;
import com.deyatech.admin.vo.UserRoleVo;
import com.deyatech.common.base.BaseService;
import java.util.Collection;
import java.util.List;

/**
 * <p>
 *  系统数据字典索引信息 服务类
 * </p>
 *
 * @Author lee.
 * @since 2019-03-06
 */
public interface UserRoleService extends BaseService<UserRole> {

    /**
     * 单个将对象转换为vo系统数据字典索引信息
     *
     * @param userRole
     * @return
     */
    UserRoleVo setVoProperties(UserRole userRole);

    /**
     * 批量将对象转换为vo系统数据字典索引信息
     *
     * @param userRoles
     * @return
     */
    List<UserRoleVo> setVoProperties(Collection userRoles);
}
