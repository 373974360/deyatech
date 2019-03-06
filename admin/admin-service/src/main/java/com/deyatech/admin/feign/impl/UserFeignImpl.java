package com.deyatech.admin.feign.impl;

import com.deyatech.admin.feign.UserFeign;
import com.deyatech.admin.service.UserService;
import com.deyatech.admin.vo.UserVo;
import com.deyatech.admin.entity.User;
import com.deyatech.common.entity.RestResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * UserFeign实现类
 * </p>
 *
 * @author: lee.
 * @since: 2018-12-25 16:36
 */
@RestController
public class UserFeignImpl implements UserFeign {

    @Autowired
    UserService userService;

    @Override
    public RestResult<UserVo> getByUser(@RequestBody User user) {
        User byBean = userService.getByBean(user);
        return RestResult.ok(userService.setVoProperties(byBean));
    }
}
