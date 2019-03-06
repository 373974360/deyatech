package com.deyatech.admin.mapper;

import com.deyatech.admin.entity.Menu;
import com.deyatech.common.base.BaseMapper;

/**
 * <p>
 * 系统菜单信息 Mapper 接口
 * </p>
 *
 * @Author lee.
 * @since 2019-03-06
 */
public interface MenuMapper extends BaseMapper<Menu> {

    /**
     * 根据用户ID查找该用户拥有的所有权限
     * @param userId
     * @return
     */
    String[] getAllPermissionsByUserId(String userId);

}
