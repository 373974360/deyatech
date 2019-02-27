package org.land.admin.service;

import org.land.admin.entity.User;
import org.land.admin.vo.UserVo;
import org.land.common.base.BaseService;
import java.util.Collection;
import java.util.List;

/**
 * <p>
 *  系统用户信息 服务类
 * </p>
 *
 * @Author lee.
 * @since 2019-02-27
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
