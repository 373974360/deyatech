package com.deyatech.admin.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
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
 * @since 2019-03-07
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

    /**
     * 分页查询
     *
     * @param user
     * @return
     */
    IPage<UserVo> findPage(UserVo user);

    /**
     * 保存用户信息
     *
     * @param user
     * @return
     */
    boolean saveOrEdit(User user);

    /**
     * 检查登录帐号是否存在
     *
     * @param id
     * @param account
     * @return
     */
    boolean checkAccountExist(String id, String account);

    /**
     *检索所有的用户信息含部门
     * @return
     */
    Collection<UserVo> selectAllUserInfo();
}
