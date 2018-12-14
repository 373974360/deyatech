package org.litisn.admin.service.impl;

import org.litisn.admin.entity.User;
import org.litisn.admin.mapper.UserMapper;
import org.litisn.admin.service.UserService;
import org.litisn.common.base.BaseServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 系统用户信息 服务实现类
 * </p>
 *
 * @author litisn
 * @since 2018-12-14
 */
@Service
public class UserServiceImpl extends BaseServiceImpl<UserMapper, User> implements UserService {

}
