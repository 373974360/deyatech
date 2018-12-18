package org.litisn.admin.controller;

import org.litisn.admin.entity.User;
import org.litisn.admin.vo.UserVo;
import org.litisn.admin.service.UserService;
import org.litisn.common.entity.RestResult;
import lombok.extern.slf4j.Slf4j;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import java.io.Serializable;
import java.util.Collection;
import org.springframework.web.bind.annotation.RestController;
import org.litisn.common.base.BaseController;

/**
 * <p>
 * 系统用户信息 前端控制器
 * </p>
 * @author: litisn
 * @since 2018-12-18
 */
@RestController
@RequestMapping("/admin/user")
@Slf4j
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
        log.info("保存或者更新系统用户信息:s%", JSONUtil.toJsonStr(user));
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
        log.info("批量保存或者更新系统用户信息:s%", JSONUtil.toJsonStr(userList));
        boolean result = userService.saveOrUpdateBatch(userList);
        return RestResult.ok(result);
    }

    /**
     * 根据ID逻辑删除系统用户信息
     *
     * @param id
     * @return
     */
    @PostMapping("/removeById")
    public RestResult removeById(Serializable id) {
        log.info("根据id删除系统用户信息:s%", id);
        boolean result = userService.removeById(id);
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
        log.info("根据id批量删除系统用户信息:s%", JSONUtil.toJsonStr(ids));
        boolean result = userService.removeByIds(ids);
        return RestResult.ok(result);
    }

    /**
     * 根据ID获取系统用户信息
     *
     * @param id
     * @return
     */
    @GetMapping("/getById")
    public RestResult getById(Serializable id) {
        User user = userService.getById(id);
        UserVo userVo = userService.setVoProperties(user);
        log.info("根据id获取系统用户信息：s%", JSONUtil.toJsonStr(userVo));
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
        log.info("根据User对象属性检索所有系统用户信息:s%",JSONUtil.toJsonStr(userVos));
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
        log.info("根据User对象属性分页检索系统用户信息:s%",JSONUtil.toJsonStr(users));
        return RestResult.ok(users);
    }
}
