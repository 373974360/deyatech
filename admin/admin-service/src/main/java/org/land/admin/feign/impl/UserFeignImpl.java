package org.land.admin.feign.impl;

import org.land.admin.entity.User;
import org.land.admin.feign.UserFeign;
import org.land.admin.service.UserService;
import org.land.admin.vo.UserVo;
import org.land.common.entity.RestResult;
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
