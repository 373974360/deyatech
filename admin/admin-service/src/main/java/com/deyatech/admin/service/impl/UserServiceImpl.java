package com.deyatech.admin.service.impl;

import com.deyatech.admin.entity.User;
import com.deyatech.admin.vo.UserVo;
import com.deyatech.admin.mapper.UserMapper;
import com.deyatech.admin.service.UserService;
import com.deyatech.common.base.BaseServiceImpl;
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
 * @since 2019-03-06
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
