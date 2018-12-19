package org.land.admin.controller;

import org.land.admin.entity.Role;
import org.land.admin.vo.RoleVo;
import org.land.admin.service.RoleService;
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
 * 系统角色信息 前端控制器
 * </p>
 * @author: lee.
 * @since 2018-12-19
 */
@Slf4j
@RestController
@RequestMapping("/admin/role")
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
    public RestResult saveOrUpdate(Role role) {
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
    public RestResult saveOrUpdateBatch(Collection<Role> roleList) {
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
    public RestResult removeByRole(Role role) {
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
    public RestResult removeByIds(Collection<Serializable> ids) {
        log.info(String.format("根据id批量删除系统角色信息: %s ", JSONUtil.toJsonStr(ids)));
        boolean result = roleService.removeByIds(ids);
        return RestResult.ok(result);
    }

    /**
     * 根据ID获取系统角色信息
     *
     * @param role
     * @return
     */
    @GetMapping("/getByRole")
    public RestResult getByRole(Role role) {
        role = roleService.getByBean(role);
        RoleVo roleVo = roleService.setVoProperties(role);
        log.info(String.format("根据id获取系统角色信息：s%", JSONUtil.toJsonStr(roleVo)));
        return RestResult.ok(roleVo);
    }

    /**
     * 根据Role对象属性检索所有系统角色信息
     *
     * @param role
     * @return
     */
    @GetMapping("/listByBean")
    public RestResult listByBean(Role role) {
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
    @GetMapping("/pageByBean")
    public RestResult pageByBean(Role role) {
        IPage roles = roleService.pageByBean(role);
        roles.setRecords(roleService.setVoProperties(roles.getRecords()));
        log.info(String.format("根据Role对象属性分页检索系统角色信息: %s ",JSONUtil.toJsonStr(roles)));
        return RestResult.ok(roles);
    }

}
