package com.deyatech.admin.controller;

import com.deyatech.admin.entity.RoleMenu;
import com.deyatech.admin.vo.RoleMenuVo;
import com.deyatech.admin.service.RoleMenuService;
import com.deyatech.common.entity.RestResult;
import io.swagger.annotations.ApiImplicitParams;
import lombok.extern.slf4j.Slf4j;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import com.deyatech.common.base.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

/**
 * <p>
 * 系统角色菜单关联信息 前端控制器
 * </p>
 * @author: lee.
 * @since 2019-03-07
 */
@Slf4j
@RestController
@RequestMapping("/admin/roleMenu")
@Api(tags = {"系统角色菜单关联信息接口"})
public class RoleMenuController extends BaseController {
    @Autowired
    RoleMenuService roleMenuService;

    /**
     * 单个保存或者更新系统角色菜单关联信息
     *
     * @param roleMenu
     * @return
     */
    @PostMapping("/saveOrUpdate")
    @ApiOperation(value="单个保存或者更新系统角色菜单关联信息", notes="根据系统角色菜单关联信息对象保存或者更新系统角色菜单关联信息信息")
    @ApiImplicitParam(name = "roleMenu", value = "系统角色菜单关联信息对象", required = true, dataType = "RoleMenu", paramType = "query")
    public RestResult<Boolean> saveOrUpdate(RoleMenu roleMenu) {
        log.info(String.format("保存或者更新系统角色菜单关联信息: %s ", JSONUtil.toJsonStr(roleMenu)));
        boolean result = roleMenuService.saveOrUpdate(roleMenu);
        return RestResult.ok(result);
    }

    /**
     * 批量保存或者更新系统角色菜单关联信息
     *
     * @param roleMenuList
     * @return
     */
    @PostMapping("/saveOrUpdateBatch")
    @ApiOperation(value="批量保存或者更新系统角色菜单关联信息", notes="根据系统角色菜单关联信息对象集合批量保存或者更新系统角色菜单关联信息信息")
    @ApiImplicitParam(name = "roleMenuList", value = "系统角色菜单关联信息对象集合", required = true, allowMultiple = true, dataType = "RoleMenu", paramType = "query")
    public RestResult<Boolean> saveOrUpdateBatch(Collection<RoleMenu> roleMenuList) {
        log.info(String.format("批量保存或者更新系统角色菜单关联信息: %s ", JSONUtil.toJsonStr(roleMenuList)));
        boolean result = roleMenuService.saveOrUpdateBatch(roleMenuList);
        return RestResult.ok(result);
    }

    /**
     * 根据RoleMenu对象属性逻辑删除系统角色菜单关联信息
     *
     * @param roleMenu
     * @return
     */
    @PostMapping("/removeByRoleMenu")
    @ApiOperation(value="根据RoleMenu对象属性逻辑删除系统角色菜单关联信息", notes="根据系统角色菜单关联信息对象逻辑删除系统角色菜单关联信息信息")
    @ApiImplicitParam(name = "roleMenu", value = "系统角色菜单关联信息对象", required = true, dataType = "RoleMenu", paramType = "query")
    public RestResult<Boolean> removeByRoleMenu(RoleMenu roleMenu) {
        log.info(String.format("根据RoleMenu对象属性逻辑删除系统角色菜单关联信息: %s ", roleMenu));
        boolean result = roleMenuService.removeByBean(roleMenu);
        return RestResult.ok(result);
    }


    /**
     * 根据ID批量逻辑删除系统角色菜单关联信息
     *
     * @param ids
     * @return
     */
    @PostMapping("/removeByIds")
    @ApiOperation(value="根据ID批量逻辑删除系统角色菜单关联信息", notes="根据系统角色菜单关联信息对象ID批量逻辑删除系统角色菜单关联信息信息")
    @ApiImplicitParam(name = "ids", value = "系统角色菜单关联信息对象ID集合", required = true, allowMultiple = true, dataType = "Serializable", paramType = "query")
    public RestResult<Boolean> removeByIds(@RequestParam(value="ids[]") List<String> ids) {
        log.info(String.format("根据id批量删除系统角色菜单关联信息: %s ", JSONUtil.toJsonStr(ids)));
        boolean result = roleMenuService.removeByIds(ids);
        return RestResult.ok(result);
    }

    /**
     * 根据RoleMenu对象属性获取系统角色菜单关联信息
     *
     * @param roleMenu
     * @return
     */
    @GetMapping("/getByRoleMenu")
    @ApiOperation(value="根据RoleMenu对象属性获取系统角色菜单关联信息", notes="根据系统角色菜单关联信息对象属性获取系统角色菜单关联信息信息")
    @ApiImplicitParam(name = "roleMenu", value = "系统角色菜单关联信息对象", required = false, dataType = "RoleMenu", paramType = "query")
    public RestResult<RoleMenuVo> getByRoleMenu(RoleMenu roleMenu) {
        roleMenu = roleMenuService.getByBean(roleMenu);
        RoleMenuVo roleMenuVo = roleMenuService.setVoProperties(roleMenu);
        log.info(String.format("根据id获取系统角色菜单关联信息：s%", JSONUtil.toJsonStr(roleMenuVo)));
        return RestResult.ok(roleMenuVo);
    }

    /**
     * 根据RoleMenu对象属性检索所有系统角色菜单关联信息
     *
     * @param roleMenu
     * @return
     */
    @GetMapping("/listByRoleMenu")
    @ApiOperation(value="根据RoleMenu对象属性检索所有系统角色菜单关联信息", notes="根据RoleMenu对象属性检索所有系统角色菜单关联信息信息")
    @ApiImplicitParam(name = "roleMenu", value = "系统角色菜单关联信息对象", required = false, dataType = "RoleMenu", paramType = "query")
    public RestResult<Collection<RoleMenuVo>> listByRoleMenu(RoleMenu roleMenu) {
        Collection<RoleMenu> roleMenus = roleMenuService.listByBean(roleMenu);
        Collection<RoleMenuVo> roleMenuVos = roleMenuService.setVoProperties(roleMenus);
        log.info(String.format("根据RoleMenu对象属性检索所有系统角色菜单关联信息: %s ",JSONUtil.toJsonStr(roleMenuVos)));
        return RestResult.ok(roleMenuVos);
    }

    /**
     * 根据RoleMenu对象属性分页检索系统角色菜单关联信息
     *
     * @param roleMenu
     * @return
     */
    @GetMapping("/pageByRoleMenu")
    @ApiOperation(value="根据RoleMenu对象属性分页检索系统角色菜单关联信息", notes="根据RoleMenu对象属性分页检索系统角色菜单关联信息信息")
    @ApiImplicitParam(name = "roleMenu", value = "系统角色菜单关联信息对象", required = false, dataType = "RoleMenu", paramType = "query")
    public RestResult<IPage<RoleMenuVo>> pageByRoleMenu(RoleMenu roleMenu) {
        IPage<RoleMenuVo> roleMenus = roleMenuService.pageByBean(roleMenu);
        roleMenus.setRecords(roleMenuService.setVoProperties(roleMenus.getRecords()));
        log.info(String.format("根据RoleMenu对象属性分页检索系统角色菜单关联信息: %s ",JSONUtil.toJsonStr(roleMenus)));
        return RestResult.ok(roleMenus);
    }

    /**
     * 设置权限菜单
     *
     * @param roleId
     * @param menuIds
     * @return
     */
    @PostMapping("/setRoleMenus")
    @ApiOperation(value="设置权限菜单", notes="设置权限菜单")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "roleId", value = "角色id", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "menuIds", value = "菜单id", required = true, dataType = "String", paramType = "query")
    })
    public RestResult setRoleMenus(String roleId, @RequestParam(value = "menuIds[]", required = false) List<String> menuIds) {
        roleMenuService.setRoleMenus(roleId, menuIds);
        return RestResult.ok();
    }
}
