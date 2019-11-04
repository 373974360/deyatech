package com.deyatech.admin.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
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
 * @since 2019-03-07
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

    /**
     * 翻页检索角色
     *
     * @param role
     * @return
     */
    IPage<RoleVo> pageByRole(Role role);
}
