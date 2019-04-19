package com.deyatech.admin.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.deyatech.admin.entity.RoleUser;
import com.deyatech.admin.vo.RoleUserVo;
import com.deyatech.common.base.BaseService;
import java.util.Collection;
import java.util.List;

/**
 * <p>
 *  系统数据字典索引信息 服务类
 * </p>
 *
 * @Author lee.
 * @since 2019-03-07
 */
public interface RoleUserService extends BaseService<RoleUser> {

    /**
     * 单个将对象转换为vo系统数据字典索引信息
     *
     * @param roleUser
     * @return
     */
    RoleUserVo setVoProperties(RoleUser roleUser);

    /**
     * 批量将对象转换为vo系统数据字典索引信息
     *
     * @param roleUsers
     * @return
     */
    List<RoleUserVo> setVoProperties(Collection roleUsers);

    /**
     * 设置角色用户
     *
     * @param roleId
     * @param userIds
     */
    void setRoleUsers(String roleId, List<String> userIds);

    /**
     * 根据角色ID获取该角色下关联的所有用户信息
     * @param roleUserVo
     * @return
     */
    IPage<RoleUserVo> pageByRoleUserVo(RoleUserVo roleUserVo);
}
