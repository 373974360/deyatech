package com.deyatech.admin.feign.impl;

import com.deyatech.admin.entity.User;
import com.deyatech.admin.feign.AdminFeign;
import com.deyatech.admin.service.MenuService;
import com.deyatech.admin.service.UserService;
import com.deyatech.admin.vo.UserVo;
import com.deyatech.common.entity.RestResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * MenuFeign实现类
 * </p>
 *
 * @author: lee.
 * @since: 2018-12-25 16:36
 */
@RestController
public class AdminFeignImpl implements AdminFeign {

    @Autowired
    UserService userService;

    @Autowired
    MenuService menuService;

    @Override
    public RestResult<String[]> getAllPermissionsByUserId(@RequestParam("userId") String userId) {
        return RestResult.ok(menuService.getAllPermissionsByUserId(userId));
    }

    @Override
    public RestResult<String[]> getAllRequestsByUserId(@RequestParam("userId") String userId) {
        return RestResult.ok(menuService.getAllRequestsByUserId(userId));
    }

    @Override
    public RestResult<UserVo> getByUser(@RequestBody User user) {
        User byBean = userService.getByBean(user);
        return RestResult.ok(userService.setVoProperties(byBean));
    }
}
