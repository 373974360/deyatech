package com.deyatech.admin.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.deyatech.admin.entity.User;
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
import java.util.List;
import java.util.Collection;

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

    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(Constants.PASSWORD_ENCORDER_SALT);

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

    @Override
    public IPage<UserVo> findPage(User user) {
        List<UserVo> list = userMapper.findList(user);
        int offset = (int) ((user.getPage() - 1) * user.getSize());
        if (offset + user.getSize() > list.size()) {
            list = list.subList(offset, list.size());
        } else {
            list = list.subList(offset, offset + user.getSize().intValue());
        }
        IPage<UserVo> page = new Page<>(user.getPage(), user.getSize(), list.size());
        page.setRecords(list);
        return page;
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
        query.ne("enable_", EnableEnum.DELETED.getCode());
        query.eq("account_", account);
        if (StrUtil.isNotBlank(id)) {
            query.ne("id_", id);
        }
        List<User> list = getBaseMapper().selectList(query);
        return CollectionUtil.isNotEmpty(list);
    }
}
