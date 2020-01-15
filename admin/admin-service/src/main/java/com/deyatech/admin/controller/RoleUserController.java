package com.deyatech.admin.controller;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.deyatech.admin.entity.RoleUser;
import com.deyatech.admin.service.RoleUserService;
import com.deyatech.admin.vo.RoleUserVo;
import com.deyatech.common.base.BaseController;
import com.deyatech.common.entity.RestResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;

/**
 * <p>
 * 系统数据字典索引信息 前端控制器
 * </p>
 *
 * @author: lee.
 * @since 2019-03-07
 */
@Slf4j
@RestController
@RequestMapping("/admin/roleUser")
@Api(tags = {"系统数据字典索引信息接口"})
public class RoleUserController extends BaseController {
    @Autowired
    RoleUserService roleUserService;

    /**
     * 单个保存或者更新系统数据字典索引信息
     *
     * @param roleUser
     * @return
     */
    @PostMapping("/saveOrUpdate")
    @ApiOperation(value = "单个保存或者更新系统数据字典索引信息", notes = "根据系统数据字典索引信息对象保存或者更新系统数据字典索引信息信息")
    @ApiImplicitParam(name = "roleUser", value = "系统数据字典索引信息对象", required = true, dataType = "RoleUser", paramType = "query")
    public RestResult<Boolean> saveOrUpdate(RoleUser roleUser) {
        log.info(String.format("保存或者更新系统数据字典索引信息: %s ", JSONUtil.toJsonStr(roleUser)));
        boolean result = roleUserService.saveOrUpdate(roleUser);
        return RestResult.ok(result);
    }

    /**
     * 批量保存或者更新系统数据字典索引信息
     *
     * @param roleUserList
     * @return
     */
    @PostMapping("/saveOrUpdateBatch")
    @ApiOperation(value = "批量保存或者更新系统数据字典索引信息", notes = "根据系统数据字典索引信息对象集合批量保存或者更新系统数据字典索引信息信息")
    @ApiImplicitParam(name = "roleUserList", value = "系统数据字典索引信息对象集合", required = true, allowMultiple = true, dataType = "RoleUser", paramType = "query")
    public RestResult<Boolean> saveOrUpdateBatch(Collection<RoleUser> roleUserList) {
        log.info(String.format("批量保存或者更新系统数据字典索引信息: %s ", JSONUtil.toJsonStr(roleUserList)));
        boolean result = roleUserService.saveOrUpdateBatch(roleUserList);
        return RestResult.ok(result);
    }

    /**
     * 根据RoleUser对象属性逻辑删除系统数据字典索引信息
     *
     * @param roleUser
     * @return
     */
    @PostMapping("/removeByRoleUser")
    @ApiOperation(value = "根据RoleUser对象属性逻辑删除系统数据字典索引信息", notes = "根据系统数据字典索引信息对象逻辑删除系统数据字典索引信息信息")
    @ApiImplicitParam(name = "roleUser", value = "系统数据字典索引信息对象", required = true, dataType = "RoleUser", paramType = "query")
    public RestResult<Boolean> removeByRoleUser(RoleUser roleUser) {
        log.info(String.format("根据RoleUser对象属性逻辑删除系统数据字典索引信息: %s ", roleUser));
        boolean result = roleUserService.removeByBean(roleUser);
        return RestResult.ok(result);
    }


    /**
     * 根据ID批量逻辑删除系统数据字典索引信息
     *
     * @param ids
     * @return
     */
    @PostMapping("/removeByIds")
    @ApiOperation(value = "根据ID批量逻辑删除系统数据字典索引信息", notes = "根据系统数据字典索引信息对象ID批量逻辑删除系统数据字典索引信息信息")
    @ApiImplicitParam(name = "ids", value = "系统数据字典索引信息对象ID集合", required = true, allowMultiple = true, dataType = "Serializable", paramType = "query")
    public RestResult<Boolean> removeByIds(@RequestParam(value = "ids[]") List<String> ids) {
        log.info(String.format("根据id批量删除系统数据字典索引信息: %s ", JSONUtil.toJsonStr(ids)));
        boolean result = roleUserService.removeByIds(ids);
        return RestResult.ok(result);
    }

    /**
     * 根据RoleUser对象属性获取系统数据字典索引信息
     *
     * @param roleUser
     * @return
     */
    @GetMapping("/getByRoleUser")
    @ApiOperation(value = "根据RoleUser对象属性获取系统数据字典索引信息", notes = "根据系统数据字典索引信息对象属性获取系统数据字典索引信息信息")
    @ApiImplicitParam(name = "roleUser", value = "系统数据字典索引信息对象", required = false, dataType = "RoleUser", paramType = "query")
    public RestResult<RoleUserVo> getByRoleUser(RoleUser roleUser) {
        roleUser = roleUserService.getByBean(roleUser);
        RoleUserVo roleUserVo = roleUserService.setVoProperties(roleUser);
        log.info(String.format("根据id获取系统数据字典索引信息：%s", JSONUtil.toJsonStr(roleUserVo)));
        return RestResult.ok(roleUserVo);
    }

    /**
     * 根据RoleUser对象属性检索所有系统数据字典索引信息
     *
     * @param roleUser
     * @return
     */
    @GetMapping("/listByRoleUser")
    @ApiOperation(value = "根据RoleUser对象属性检索所有系统数据字典索引信息", notes = "根据RoleUser对象属性检索所有系统数据字典索引信息信息")
    @ApiImplicitParam(name = "roleUser", value = "系统数据字典索引信息对象", required = false, dataType = "RoleUser", paramType = "query")
    public RestResult<Collection<RoleUserVo>> listByRoleUser(RoleUser roleUser) {
        Collection<RoleUser> roleUsers = roleUserService.listByBean(roleUser);
        Collection<RoleUserVo> roleUserVos = roleUserService.setVoProperties(roleUsers);
        log.info(String.format("根据RoleUser对象属性检索所有系统数据字典索引信息: %s ", JSONUtil.toJsonStr(roleUserVos)));
        return RestResult.ok(roleUserVos);
    }

    /**
     * 根据RoleUser对象属性分页检索系统数据字典索引信息
     *
     * @param roleUserVo
     * @return
     */
    @GetMapping("/pageByRoleUser")
    @ApiOperation(value = "根据RoleUser对象属性分页检索系统数据字典索引信息", notes = "根据RoleUser对象属性分页检索系统数据字典索引信息信息")
    @ApiImplicitParam(name = "roleUserVo", value = "系统数据字典索引信息对象", required = false, dataType = "RoleUserVo", paramType = "query")
    public RestResult<IPage<RoleUserVo>> pageByRoleUser(RoleUserVo roleUserVo) {
        IPage<RoleUserVo> roleUsers = roleUserService.pageByRoleUserVo(roleUserVo);
        roleUsers.setRecords(roleUserService.setVoProperties(roleUsers.getRecords()));
        log.info(String.format("根据RoleUser对象属性分页检索系统数据字典索引信息: %s ", JSONUtil.toJsonStr(roleUsers)));
        return RestResult.ok(roleUsers);
    }

    /**
     * 设置角色用户
     *
     * @param roleId
     * @param userIds
     * @return
     */
    @PostMapping("/setRoleUsers")
    @ApiOperation(value = "设置角色用户", notes = "设置角色用户")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "roleId", value = "角色id", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "userIds", value = "用户id", required = true, dataType = "String", paramType = "query")
    })
    public RestResult setRoleUsers(String roleId, @RequestParam(value = "userIds[]", required = false) List<String> userIds) {
        roleUserService.setRoleUsers(roleId, userIds);
        return RestResult.ok();
    }

    /**
     * 取得用户角色
     *
     * @param userIds
     * @return
     */
    @RequestMapping("/getUsersRoles")
    @ApiOperation(value="取得用户角色", notes="取得用户角色")
    @ApiImplicitParam(name = "userIds", value = "用户id", required = true, dataType = "List", paramType = "query")
    public RestResult getUsersRoles(@RequestParam("userIds[]") List<String> userIds) {
        return RestResult.ok(roleUserService.getUsersRoles(userIds));
    }

    /**
     * 设置用户角色
     *
     * @param userIds
     * @param roleIds
     * @return
     */
    @RequestMapping("/setUsersRoles")
    @ApiOperation(value="设置用户角色", notes="设置用户角色")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userIds", value = "用户id", required = true, dataType = "List", paramType = "query"),
            @ApiImplicitParam(name = "roleIds", value = "角色id", required = true, dataType = "List", paramType = "query")
    })
    public RestResult setUsersRoles(@RequestParam("userIds[]") List<String> userIds,
                                    @RequestParam(value = "roleIds[]", required = false) List<String> roleIds) {
        roleUserService.setUsersRoles(userIds, roleIds);
        return RestResult.ok();
    }
}
