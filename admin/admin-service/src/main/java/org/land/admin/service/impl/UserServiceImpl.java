package org.land.admin.service.impl;

import org.land.admin.entity.User;
import org.land.admin.vo.UserVo;
import org.land.admin.mapper.UserMapper;
import org.land.admin.service.UserService;
import org.land.common.base.BaseServiceImpl;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Collection;

/**
 * <p>
 * 系统用户信息 服务实现类
 * </p>
 *
 * @Author lee.
 * @since 2018-12-19
 */
@Service
public class UserServiceImpl extends BaseServiceImpl<UserMapper, User> implements UserService {

    /**
     * 单个将对象转换为vo系统用户信息
     *
     * @param user
     * @return
     */
    @Override
    public UserVo setVoProperties(User user){
        UserVo userVo = new UserVo();
        BeanUtil.copyProperties(user, userVo);
        return userVo;
    }

    /**
     * 批量将对象转换为vo系统用户信息
     *
     * @param users
     * @return
     */
    @Override
    public List<UserVo> setVoProperties(Collection users){
        List<UserVo> userVos = CollectionUtil.newArrayList();
        if (CollectionUtil.isNotEmpty(users)) {
            for (Object user : users) {
                UserVo userVo = new UserVo();
                BeanUtil.copyProperties(user, userVo);
                userVos.add(userVo);
            }
        }
        return userVos;
    }
}
