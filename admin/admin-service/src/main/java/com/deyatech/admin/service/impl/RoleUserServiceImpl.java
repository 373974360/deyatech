package com.deyatech.admin.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.deyatech.admin.entity.RoleUser;
import com.deyatech.admin.mapper.RoleUserMapper;
import com.deyatech.admin.service.RoleUserService;
import com.deyatech.admin.vo.RoleUserVo;
import com.deyatech.common.base.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * <p>
 * 系统数据字典索引信息 服务实现类
 * </p>
 *
 * @Author lee.
 * @since 2019-03-07
 */
@Service
public class RoleUserServiceImpl extends BaseServiceImpl<RoleUserMapper, RoleUser> implements RoleUserService {

    @Autowired
    RoleUserMapper roleUserMapper;

    /**
     * 单个将对象转换为vo系统数据字典索引信息
     *
     * @param roleUser
     * @return
     */
    @Override
    public RoleUserVo setVoProperties(RoleUser roleUser) {
        RoleUserVo roleUserVo = new RoleUserVo();
        BeanUtil.copyProperties(roleUser, roleUserVo);
        return roleUserVo;
    }

    /**
     * 批量将对象转换为vo系统数据字典索引信息
     *
     * @param roleUsers
     * @return
     */
    @Override
    public List<RoleUserVo> setVoProperties(Collection roleUsers) {
        List<RoleUserVo> roleUserVos = CollectionUtil.newArrayList();
        if (CollectionUtil.isNotEmpty(roleUsers)) {
            for (Object roleUser : roleUsers) {
                RoleUserVo roleUserVo = new RoleUserVo();
                BeanUtil.copyProperties(roleUser, roleUserVo);
                roleUserVos.add(roleUserVo);
            }
        }
        return roleUserVos;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void setRoleUsers(String roleId, List<String> userIds) {
        RoleUser roleUser = new RoleUser();
        roleUser.setRoleId(roleId);
        this.removeByBean(roleUser);
        if (CollectionUtil.isNotEmpty(userIds)) {
            List<RoleUser> list = new ArrayList<>();
            for (String userId : userIds) {
                RoleUser ur = new RoleUser();
                ur.setRoleId(roleId);
                ur.setUserId(userId);
                list.add(ur);
            }
            this.saveOrUpdateBatch(list);
        }
    }

    @Override
    public IPage<RoleUserVo> pageByRoleUserVo(RoleUserVo roleUserVo) {
        return roleUserMapper.pageByRoleUserVo(getPageByBean(roleUserVo),roleUserVo);
    }


}
