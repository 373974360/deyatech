package com.deyatech.admin.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.deyatech.admin.entity.Role;
import com.deyatech.admin.vo.RoleVo;
import com.deyatech.admin.service.RoleService;
import com.deyatech.common.entity.RestResult;
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
 * 系统角色信息 前端控制器
 * </p>
 * @author: lee.
 * @since 2019-03-07
 */
@Slf4j
@RestController
@RequestMapping("/admin/role")
@Api(tags = {"系统角色信息接口"})
public class RoleController extends BaseController {
    @Autowired
    RoleService roleService;

    /**
     * 单个保存或者更新系统角色信息
     *
     * @param role
     * @return
     */
    @PostMapping("/saveOrUpdate")
    @ApiOperation(value="单个保存或者更新系统角色信息", notes="根据系统角色信息对象保存或者更新系统角色信息信息")
    @ApiImplicitParam(name = "role", value = "系统角色信息对象", required = true, dataType = "Role", paramType = "query")
    public RestResult<Boolean> saveOrUpdate(Role role) {
        log.info(String.format("保存或者更新系统角色信息: %s ", JSONUtil.toJsonStr(role)));
        boolean result = roleService.saveOrUpdate(role);
        return RestResult.ok(result);
    }

    /**
     * 批量保存或者更新系统角色信息
     *
     * @param roleList
     * @return
     */
    @PostMapping("/saveOrUpdateBatch")
    @ApiOperation(value="批量保存或者更新系统角色信息", notes="根据系统角色信息对象集合批量保存或者更新系统角色信息信息")
    @ApiImplicitParam(name = "roleList", value = "系统角色信息对象集合", required = true, allowMultiple = true, dataType = "Role", paramType = "query")
    public RestResult<Boolean> saveOrUpdateBatch(Collection<Role> roleList) {
        log.info(String.format("批量保存或者更新系统角色信息: %s ", JSONUtil.toJsonStr(roleList)));
        boolean result = roleService.saveOrUpdateBatch(roleList);
        return RestResult.ok(result);
    }

    /**
     * 根据Role对象属性逻辑删除系统角色信息
     *
     * @param role
     * @return
     */
    @PostMapping("/removeByRole")
    @ApiOperation(value="根据Role对象属性逻辑删除系统角色信息", notes="根据系统角色信息对象逻辑删除系统角色信息信息")
    @ApiImplicitParam(name = "role", value = "系统角色信息对象", required = true, dataType = "Role", paramType = "query")
    public RestResult<Boolean> removeByRole(Role role) {
        log.info(String.format("根据Role对象属性逻辑删除系统角色信息: %s ", role));
        boolean result = roleService.removeByBean(role);
        return RestResult.ok(result);
    }


    /**
     * 根据ID批量逻辑删除系统角色信息
     *
     * @param ids
     * @return
     */
    @PostMapping("/removeByIds")
    @ApiOperation(value="根据ID批量逻辑删除系统角色信息", notes="根据系统角色信息对象ID批量逻辑删除系统角色信息信息")
    @ApiImplicitParam(name = "ids", value = "系统角色信息对象ID集合", required = true, allowMultiple = true, dataType = "String", paramType = "query")
    public RestResult<Boolean> removeByIds(@RequestParam("ids[]") List<String> ids) {
        log.info(String.format("根据id批量删除系统角色信息: %s ", JSONUtil.toJsonStr(ids)));
        boolean result = roleService.removeByIds(ids);
        return RestResult.ok(result);
    }

    /**
     * 根据Role对象属性获取系统角色信息
     *
     * @param role
     * @return
     */
    @GetMapping("/getByRole")
    @ApiOperation(value="根据Role对象属性获取系统角色信息", notes="根据系统角色信息对象属性获取系统角色信息信息")
    @ApiImplicitParam(name = "role", value = "系统角色信息对象", required = false, dataType = "Role", paramType = "query")
    public RestResult<RoleVo> getByRole(Role role) {
        role = roleService.getByBean(role);
        RoleVo roleVo = roleService.setVoProperties(role);
        log.info(String.format("根据id获取系统角色信息：%s", JSONUtil.toJsonStr(roleVo)));
        return RestResult.ok(roleVo);
    }

    /**
     * 根据Role对象属性检索所有系统角色信息
     *
     * @param role
     * @return
     */
    @GetMapping("/listByRole")
    @ApiOperation(value="根据Role对象属性检索所有系统角色信息", notes="根据Role对象属性检索所有系统角色信息信息")
    @ApiImplicitParam(name = "role", value = "系统角色信息对象", required = false, dataType = "Role", paramType = "query")
    public RestResult<Collection<RoleVo>> listByRole(Role role) {
        Collection<Role> roles = roleService.listByBean(role);
        Collection<RoleVo> roleVos = roleService.setVoProperties(roles);
        log.info(String.format("根据Role对象属性检索所有系统角色信息: %s ",JSONUtil.toJsonStr(roleVos)));
        return RestResult.ok(roleVos);
    }

    /**
     * 根据Role对象属性分页检索系统角色信息
     *
     * @param role
     * @return
     */
    @GetMapping("/pageByRole")
    @ApiOperation(value="根据Role对象属性分页检索系统角色信息", notes="根据Role对象属性分页检索系统角色信息信息")
    @ApiImplicitParam(name = "role", value = "系统角色信息对象", required = false, dataType = "Role", paramType = "query")
    public RestResult<IPage<RoleVo>> pageByRole(Role role) {
//        IPage<RoleVo> roles = roleService.pageByBean(role);
//        roles.setRecords(roleService.setVoProperties(roles.getRecords()));
//        log.info(String.format("根据Role对象属性分页检索系统角色信息: %s ",JSONUtil.toJsonStr(roles)));
        return RestResult.ok(roleService.pageByRole(role));
    }
    /**
     * 根据角色名统计件数
     * @param name
     * @return
     */
    @GetMapping("/countRoleByName")
    @ApiOperation(value="根据角色名统计件数", notes="根据角色名统计件数")
    @ApiImplicitParam(name = "name", value = "角色名", required = true, dataType = "String", paramType = "query")
    public RestResult<Integer> countRoleByName(@RequestParam String name) {
        QueryWrapper queryWrapper = new QueryWrapper();
        if (StringUtils.isNotEmpty(name)) {
            queryWrapper.eq("name_", name);
        }
        Integer count = roleService.count(queryWrapper);
        return RestResult.ok(count);
    }
}
