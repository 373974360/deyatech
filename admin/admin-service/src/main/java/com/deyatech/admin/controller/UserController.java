package com.deyatech.admin.controller;

import com.deyatech.admin.entity.User;
import com.deyatech.admin.vo.UserVo;
import com.deyatech.admin.service.UserService;
import com.deyatech.common.entity.RestResult;
import io.swagger.annotations.ApiImplicitParams;
import lombok.extern.slf4j.Slf4j;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;

import com.deyatech.common.base.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

/**
 * <p>
 * 系统用户信息 前端控制器
 * </p>
 * @author: lee.
 * @since 2019-03-07
 */
@Slf4j
@RestController
@RequestMapping("/admin/user")
@Api(tags = {"系统用户信息接口"})
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
    @ApiOperation(value="单个保存或者更新系统用户信息", notes="根据系统用户信息对象保存或者更新系统用户信息信息")
    @ApiImplicitParam(name = "user", value = "系统用户信息对象", required = true, dataType = "User", paramType = "query")
    public RestResult<Boolean> saveOrUpdate(User user) {
        log.info(String.format("保存或者更新系统用户信息: %s ", JSONUtil.toJsonStr(user)));
        boolean result = userService.saveOrEdit(user);
        return RestResult.ok(result);
    }

    /**
     * 批量保存或者更新系统用户信息
     *
     * @param userList
     * @return
     */
    @PostMapping("/saveOrUpdateBatch")
    @ApiOperation(value="批量保存或者更新系统用户信息", notes="根据系统用户信息对象集合批量保存或者更新系统用户信息信息")
    @ApiImplicitParam(name = "userList", value = "系统用户信息对象集合", required = true, allowMultiple = true, dataType = "User", paramType = "query")
    public RestResult<Boolean> saveOrUpdateBatch(Collection<User> userList) {
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
    @ApiOperation(value="根据User对象属性逻辑删除系统用户信息", notes="根据系统用户信息对象逻辑删除系统用户信息信息")
    @ApiImplicitParam(name = "user", value = "系统用户信息对象", required = true, dataType = "User", paramType = "query")
    public RestResult<Boolean> removeByUser(User user) {
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
    @ApiOperation(value="根据ID批量逻辑删除系统用户信息", notes="根据系统用户信息对象ID批量逻辑删除系统用户信息信息")
    @ApiImplicitParam(name = "ids", value = "系统用户信息对象ID集合", required = true, allowMultiple = true, dataType = "String", paramType = "query")
    public RestResult<Boolean> removeByIds(@RequestParam("ids[]") List<String> ids) {
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
    @ApiOperation(value="根据User对象属性获取系统用户信息", notes="根据系统用户信息对象属性获取系统用户信息信息")
    @ApiImplicitParam(name = "user", value = "系统用户信息对象", required = false, dataType = "User", paramType = "query")
    public RestResult<UserVo> getByUser(User user) {
        user = userService.getByBean(user);
        UserVo userVo = userService.setVoProperties(user);
        log.info(String.format("根据id获取系统用户信息：s%", JSONUtil.toJsonStr(userVo)));
        return RestResult.ok(userVo);
    }

    /**
     * 根据User对象属性检索所有系统用户信息
     *
     * @param user
     * @return
     */
    @GetMapping("/listByUser")
    @ApiOperation(value="根据User对象属性检索所有系统用户信息", notes="根据User对象属性检索所有系统用户信息信息")
    @ApiImplicitParam(name = "user", value = "系统用户信息对象", required = false, dataType = "User", paramType = "query")
    public RestResult<Collection<UserVo>> listByUser(User user) {
        Collection<User> users = userService.listByBean(user);
        Collection<UserVo> userVos = userService.setVoProperties(users);
        log.info(String.format("根据User对象属性检索所有系统用户信息: %s ",JSONUtil.toJsonStr(userVos)));
        return RestResult.ok(userVos);
    }

    /**
     * 根据User对象属性分页检索系统用户信息
     *
     * @param user
     * @return
     */
    @GetMapping("/pageByUser")
    @ApiOperation(value="根据User对象属性分页检索系统用户信息", notes="根据User对象属性分页检索系统用户信息信息")
    @ApiImplicitParam(name = "user", value = "系统用户信息对象", required = false, dataType = "User", paramType = "query")
    public RestResult<IPage<UserVo>> pageByUser(UserVo user) {
        /*IPage<UserVo> users = userService.pageByBean(user);
        users.setRecords(userService.setVoProperties(users.getRecords()));*/
        IPage<UserVo> users = userService.findPage(user);
        log.info(String.format("根据User对象属性分页检索系统用户信息: %s ",JSONUtil.toJsonStr(users)));
        return RestResult.ok(users);
    }

    /**
     * 检查帐号是否已经存在
     *
     * @param id
     * @param account
     * @return
     */
    @GetMapping("/checkAccountExist")
    @ApiOperation(value="检查帐号是否已经存在", notes="检查帐号是否已经存在")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "用户id", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "account", value = "用户登录帐号", required = true, dataType = "String", paramType = "query")
    })
    public RestResult checkAccountExist(String id, String account) {
        boolean exist = userService.checkAccountExist(id, account);
        return RestResult.ok(exist);
    }

}
