package org.land.admin.controller;

import org.land.admin.entity.RoleMenu;
import org.land.admin.vo.RoleMenuVo;
import org.land.admin.service.RoleMenuService;
import org.land.common.entity.RestResult;
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
import org.land.common.base.BaseController;

/**
 * <p>
 * 系统角色菜单关联信息 前端控制器
 * </p>
 * @author: lee.
 * @since 2018-12-19
 */
@Slf4j
@RestController
@RequestMapping("/admin/roleMenu")
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
    public RestResult saveOrUpdate(RoleMenu roleMenu) {
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
    public RestResult saveOrUpdateBatch(Collection<RoleMenu> roleMenuList) {
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
    public RestResult removeByRoleMenu(RoleMenu roleMenu) {
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
    public RestResult removeByIds(Collection<Serializable> ids) {
        log.info(String.format("根据id批量删除系统角色菜单关联信息: %s ", JSONUtil.toJsonStr(ids)));
        boolean result = roleMenuService.removeByIds(ids);
        return RestResult.ok(result);
    }

    /**
     * 根据ID获取系统角色菜单关联信息
     *
     * @param roleMenu
     * @return
     */
    @GetMapping("/getByRoleMenu")
    public RestResult getByRoleMenu(RoleMenu roleMenu) {
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
    @GetMapping("/listByBean")
    public RestResult listByBean(RoleMenu roleMenu) {
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
    @GetMapping("/pageByBean")
    public RestResult pageByBean(RoleMenu roleMenu) {
        IPage roleMenus = roleMenuService.pageByBean(roleMenu);
        roleMenus.setRecords(roleMenuService.setVoProperties(roleMenus.getRecords()));
        log.info(String.format("根据RoleMenu对象属性分页检索系统角色菜单关联信息: %s ",JSONUtil.toJsonStr(roleMenus)));
        return RestResult.ok(roleMenus);
    }

}
