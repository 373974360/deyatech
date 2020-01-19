package com.deyatech.admin.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.deyatech.admin.entity.RoleUser;
import com.deyatech.admin.mapper.RoleUserMapper;
import com.deyatech.admin.service.RoleUserService;
import com.deyatech.admin.vo.RoleUserVo;
import com.deyatech.common.base.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

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

    /**
     * 设置用户角色
     *
     * @param userIds
     * @param roleIds
     */
    @Override
    public void setUsersRoles(List<String> userIds, List<String> roleIds) {
        if (CollectionUtil.isNotEmpty(userIds)) {
            for (String userId : userIds) {
                // 删除原来的
                RoleUser roleUser = new RoleUser();
                roleUser.setUserId(userId);
                this.removeByBean(roleUser);
                // 添加新的
                if (CollectionUtils.isNotEmpty(roleIds)) {
                    List<RoleUser> list = new ArrayList<>();
                    for (String roleId : roleIds) {
                        RoleUser ru = new RoleUser();
                        ru.setUserId(userId);
                        ru.setRoleId(roleId);
                        list.add(ru);
                    }
                    this.saveOrUpdateBatch(list);
                }
            }
        }
    }

    /**
     * 取得用户角色
     *
     * @param userIds
     * @return
     */
    @Override
    public List<String> getUsersRoles(List<String> userIds) {
        if (CollectionUtil.isEmpty(userIds)) {
            return null;
        }
        QueryWrapper<RoleUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("user_id", userIds);
        List<RoleUser> all = super.list(queryWrapper);
        if (CollectionUtil.isEmpty(all)) {
            return null;
        }
        if (userIds.size() == 1) {
            return all.stream().map(RoleUser::getRoleId).distinct().collect(Collectors.toList());
        }
        // 取出所有用户的交集栏目
        Map<String, List<String>> userRoleIdsMap = new HashMap<>();
        int minLen = Integer.MAX_VALUE;
        List<String> resultRoleIds = null;
        for (String userId : userIds) {
            List<String> roleIds = all.stream().filter(cu -> cu.getUserId().equals(userId)).map(RoleUser::getRoleId).distinct().sorted(Comparator.comparing(roleId -> roleId)).collect(Collectors.toList());
            // 有一个用户没有，则交集为空
            if (CollectionUtil.isEmpty(roleIds)) {
                return null;
            }
            if (minLen > roleIds.size()) {
                minLen = roleIds.size();
                resultRoleIds = roleIds;
            }
            userRoleIdsMap.put(userId, roleIds);
        }
        for (String userId : userIds) {
            resultRoleIds.retainAll(userRoleIdsMap.get(userId));
        }
        return resultRoleIds;
    }


}
