package com.deyatech.admin.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.deyatech.admin.entity.User;
import com.deyatech.admin.service.DepartmentService;
import com.deyatech.admin.vo.UserVo;
import com.deyatech.admin.mapper.UserMapper;
import com.deyatech.admin.service.UserService;
import com.deyatech.common.Constants;
import com.deyatech.common.base.BaseServiceImpl;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import com.deyatech.common.enums.EnableEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Collection;
import java.util.Map;

/**
 * <p>
 * 系统用户信息 服务实现类
 * </p>
 *
 * @Author lee.
 * @since 2019-03-07
 */
@Service
public class UserServiceImpl extends BaseServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    UserMapper userMapper;
    @Autowired
    DepartmentService departmentService;

    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(Constants.PASSWORD_ENCORDER_SALT);

    /**
     * 单个将对象转换为vo系统用户信息
     *
     * @param user
     * @return
     */
    @Override
    public UserVo setVoProperties(User user) {
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
    public List<UserVo> setVoProperties(Collection users) {
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

    @Override
    public IPage<UserVo> findPage(UserVo user) {
        return userMapper.findList(getPageByBean(user), user);
    }

    @Override
    public boolean saveOrEdit(User user) {
        if (StrUtil.isNotBlank(user.getPassword())) {
            user.setPassword(encoder.encode(user.getPassword()));
        }
        return saveOrUpdate(user);
    }

    @Override
    public boolean checkAccountExist(String id, String account) {
        QueryWrapper<User> query = new QueryWrapper<>();
        query.eq("account_", account);
        if (StrUtil.isNotBlank(id)) {
            query.ne("id_", id);
        }
        List<User> list = getBaseMapper().selectList(query);
        return CollectionUtil.isNotEmpty(list);
    }

    /**
     *检索所有的用户信息含部门
     * @return
     */
    @Override
    public Collection<UserVo> selectAllUserInfo() {
        return userMapper.selectAllUserInfo();
    }

    /**
     * 根据用户工号取得用户信息
     * @param empNo
     * @return
     */
    @Override
    public UserVo getUserByEmpNo(String empNo) {
        return userMapper.getUserByEmpNo(empNo);
    }

    /**
     * 根据部门及窗口查询用户
     *
     * @param departmentIds
     * @param windowIds
     * @return
     */
    @Override
    public List<User> getUsersByWindowAndDepartment(String departmentIds, String windowIds) {
        String[] departmentArr = StrUtil.isNotEmpty(departmentIds) ? departmentIds.split(",") : null;
        String[] windowArr = StrUtil.isNotEmpty(windowIds) ? windowIds.split(",") : null;
        Map map = CollectionUtil.newHashMap();
        map.put("departmentIds", departmentArr);
        map.put("windowIds", windowArr);
        return userMapper.getUsersByWindowAndDepartment(map);
    }

    @Override
    public List<User> findByIds(List<String> ids) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.in("id_", ids);
        wrapper.eq("enable_", EnableEnum.ENABLE.getCode());
        return list(wrapper);
    }


    /**
     * 获取用户所在部门及子部门的所有用户ID
     *
     * @param userId
     * @return
     */
    @Override
    public List<User> getAllUserIdInUserDepartment(String userId) {
        User user = getById(userId);
        List<String> departmentIds = departmentService.getAllChildrenDepartmentIds(user.getDepartmentId());
        departmentIds.add(user.getDepartmentId());
        return baseMapper.getAllUserIdInUserDepartment(departmentIds);
    }
}
