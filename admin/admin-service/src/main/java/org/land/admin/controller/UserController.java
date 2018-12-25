package org.land.admin.controller;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.extern.slf4j.Slf4j;
import org.land.admin.entity.User;
import org.land.admin.service.UserService;
import org.land.admin.vo.UserVo;
import org.land.common.base.BaseController;
import org.land.common.entity.RestResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.Serializable;
import java.util.Collection;

/**
 * <p>
 * 系统用户信息 前端控制器
 * </p>
 *
 * @author: lee.
 * @since 2018-12-21
 */
@Slf4j
@RestController
@RequestMapping("/admin/user")
public class UserController extends BaseController {
    @Autowired
    UserService userService;

    /**
     * 单个保存或者更新系统用户信息
     *
     * @param user
     * @return
     */
    @PostMapping("/saveOrUpdate")
    public RestResult saveOrUpdate(User user) {
        log.info(String.format("保存或者更新系统用户信息: %s ", JSONUtil.toJsonStr(user)));
        boolean result = userService.saveOrUpdate(user);
        return RestResult.ok(result);
    }

    /**
     * 批量保存或者更新系统用户信息
     *
     * @param userList
     * @return
     */
    @PostMapping("/saveOrUpdateBatch")
    public RestResult saveOrUpdateBatch(Collection<User> userList) {
        log.info(String.format("批量保存或者更新系统用户信息: %s ", JSONUtil.toJsonStr(userList)));
        boolean result = userService.saveOrUpdateBatch(userList);
        return RestResult.ok(result);
    }

    /**
     * 根据User对象属性逻辑删除系统用户信息
     *
     * @param user
     * @return
     */
    @PostMapping("/removeByUser")
    public RestResult removeByUser(User user) {
        log.info(String.format("根据User对象属性逻辑删除系统用户信息: %s ", user));
        boolean result = userService.removeByBean(user);
        return RestResult.ok(result);
    }


    /**
     * 根据ID批量逻辑删除系统用户信息
     *
     * @param ids
     * @return
     */
    @PostMapping("/removeByIds")
    public RestResult removeByIds(Collection<Serializable> ids) {
        log.info(String.format("根据id批量删除系统用户信息: %s ", JSONUtil.toJsonStr(ids)));
        boolean result = userService.removeByIds(ids);
        return RestResult.ok(result);
    }

    /**
     * 根据User对象属性获取系统用户信息
     *
     * @param user
     * @return
     */
    @GetMapping("/getByUser")
    public RestResult getByUser(User user) {
        user = userService.getByBean(user);
        UserVo userVo = userService.setVoProperties(user);
        log.info(String.format("根据id获取系统用户信息：%s", JSONUtil.toJsonStr(userVo)));
        return RestResult.ok(userVo);
    }

    /**
     * 根据User对象属性检索所有系统用户信息
     *
     * @param user
     * @return
     */
    @GetMapping("/listByBean")
    public RestResult listByBean(User user) {
        Collection<User> users = userService.listByBean(user);
        Collection<UserVo> userVos = userService.setVoProperties(users);
        log.info(String.format("根据User对象属性检索所有系统用户信息: %s ", JSONUtil.toJsonStr(userVos)));
        return RestResult.ok(userVos);
    }

    /**
     * 根据User对象属性分页检索系统用户信息
     *
     * @param user
     * @return
     */
    @GetMapping("/pageByBean")
    public RestResult pageByBean(User user) {
        IPage users = userService.pageByBean(user);
        users.setRecords(userService.setVoProperties(users.getRecords()));
        log.info(String.format("根据User对象属性分页检索系统用户信息: %s ", JSONUtil.toJsonStr(users)));
        return RestResult.ok(users);
    }

}
