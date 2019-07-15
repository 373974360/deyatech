package com.deyatech.admin.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.deyatech.admin.entity.RoleUser;
import com.deyatech.admin.vo.RoleUserVo;
import com.deyatech.common.base.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 系统数据字典索引信息 Mapper 接口
 * </p>
 *
 * @Author lee.
 * @since 2019-03-07
 */
public interface RoleUserMapper extends BaseMapper<RoleUser> {

    /**
     * 根据角色ID获取该角色下关联的所有用户信息
     * @param page
     * @param roleUserVo
     * @return
     */
    IPage<RoleUserVo> pageByRoleUserVo(@Param("page") IPage<RoleUser> page, @Param("roleUserVo") RoleUserVo roleUserVo);
}
