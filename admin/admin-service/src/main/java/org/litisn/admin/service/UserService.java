package org.litisn.admin.service;

import org.litisn.admin.entity.User;
import org.litisn.admin.vo.UserVo;
import org.litisn.common.base.BaseService;
import java.util.Collection;
import java.util.List;
/**
 * <p>
 *  系统用户信息 服务类
 * </p>
 *
 * @Author litisn
 * @since 2018-12-18
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
