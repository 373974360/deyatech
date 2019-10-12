package com.deyatech.admin.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.deyatech.admin.entity.User;
import com.deyatech.admin.vo.UserVo;
import com.deyatech.common.base.BaseService;
import org.springframework.web.bind.annotation.RequestParam;

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

    /**
     * 根据用户工号取得用户信息
     * @param empNo
     * @return
     */
    UserVo getUserByEmpNo(String empNo);

    /**
     * 根据部门及窗口查询用户
     *
     * @param departmentIds
     * @param windowIds
     * @return
     */
    List<User> getUsersByWindowAndDepartment(String departmentIds, String windowIds);

    /**
     * 根据id列表查询用户信息
     *
     * @param ids
     * @return
     */
    List<User> findByIds(List<String> ids);

    /**
     * 获取用户所在部门及子部门的所有用户ID
     *
     * @param userId
     * @return
     */
    List<User> getAllUserIdInUserDepartment(String userId);
}
