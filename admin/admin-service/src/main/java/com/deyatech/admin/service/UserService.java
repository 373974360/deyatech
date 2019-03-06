package com.deyatech.admin.service;

import com.deyatech.admin.entity.User;
import com.deyatech.admin.vo.UserVo;
import com.deyatech.common.base.BaseService;
import java.util.Collection;
import java.util.List;

/**
 * <p>
 *  系统用户信息 服务类
 * </p>
 *
 * @Author lee.
 * @since 2019-03-06
 */
public interface UserService extends BaseService<User> {

    /**
     * 单个将对象转换为vo系统用户信息
     *
     * @param user
     * @return
     */
    UserVo setVoProperties(User user);

    /**
     * 批量将对象转换为vo系统用户信息
     *
     * @param users
     * @return
     */
    List<UserVo> setVoProperties(Collection users);
}
