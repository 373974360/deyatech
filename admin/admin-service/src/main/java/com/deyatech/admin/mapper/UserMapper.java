package com.deyatech.admin.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.deyatech.admin.entity.User;
import com.deyatech.admin.vo.UserVo;
import com.deyatech.common.base.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 系统用户信息 Mapper 接口
 * </p>
 *
 * @Author lee.
 * @since 2019-03-07
 */
public interface UserMapper extends BaseMapper<User> {

    /**
     * 根据参数分页获取用户对象列表
     * @param page
     * @param user
     * @return
     */
    IPage<UserVo> findList(@Param("page") IPage<User> page, @Param("user") UserVo user);

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
    UserVo getUserByEmpNo(@Param("empNo") String empNo);

    /**
     * 根据部门及窗口查询用户
     *
     * @param map
     * @return
     */
    List<User> getUsersByWindowAndDepartment(Map map);

    /**
     * 获取用户所在部门及子部门的所有用户
     *
     * @param list
     * @return
     */
    List<User> getAllUserIdInUserDepartment(@Param("list") List<String> list);
}
