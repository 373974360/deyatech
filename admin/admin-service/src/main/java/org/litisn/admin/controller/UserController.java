package org.litisn.admin.controller;


import lombok.extern.slf4j.Slf4j;
import org.litisn.admin.entity.User;
import org.litisn.admin.service.UserService;
import org.litisn.common.base.BaseController;
import org.litisn.common.entity.RestResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 系统用户信息 前端控制器
 * </p>
 *
 * @author litisn
 * @since 2018-12-14
 */
@RestController
@RequestMapping("/admin/user")
@Slf4j
public class UserController extends BaseController {

    @Autowired
    UserService userService;

    @PostMapping("/save")
    public RestResult save(User user){
        log.info("保存用户信息");
        boolean save = userService.save(user);
        return RestResult.ok(save);
    }

    @PostMapping("/update")
    public RestResult update(User user){
        log.info("更新用户信息");
        boolean updateById = userService.updateById(user);
        return RestResult.ok(updateById);
    }

    @GetMapping("/getAll")
    public RestResult getAll(){
        log.info("获取所有用户信息");
        List<User> list = userService.list();
        return RestResult.ok(list);
    }

}
