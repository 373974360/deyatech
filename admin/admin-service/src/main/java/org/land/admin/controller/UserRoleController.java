package org.land.admin.controller;

import org.land.admin.entity.UserRole;
import org.land.admin.vo.UserRoleVo;
import org.land.admin.service.UserRoleService;
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
 * 系统数据字典索引信息 前端控制器
 * </p>
 * @author: lee.
 * @since 2018-12-21
 */
@Slf4j
@RestController
@RequestMapping("/admin/userRole")
public class UserRoleController extends BaseController {
    @Autowired
    UserRoleService userRoleService;

    /**
     * 单个保存或者更新系统数据字典索引信息
     *
     * @param userRole
     * @return
     */
    @PostMapping("/saveOrUpdate")
    public RestResult saveOrUpdate(UserRole userRole) {
        log.info(String.format("保存或者更新系统数据字典索引信息: %s ", JSONUtil.toJsonStr(userRole)));
        boolean result = userRoleService.saveOrUpdate(userRole);
        return RestResult.ok(result);
    }

    /**
     * 批量保存或者更新系统数据字典索引信息
     *
     * @param userRoleList
     * @return
     */
    @PostMapping("/saveOrUpdateBatch")
    public RestResult saveOrUpdateBatch(Collection<UserRole> userRoleList) {
        log.info(String.format("批量保存或者更新系统数据字典索引信息: %s ", JSONUtil.toJsonStr(userRoleList)));
        boolean result = userRoleService.saveOrUpdateBatch(userRoleList);
        return RestResult.ok(result);
    }

    /**
     * 根据UserRole对象属性逻辑删除系统数据字典索引信息
     *
     * @param userRole
     * @return
     */
    @PostMapping("/removeByUserRole")
    public RestResult removeByUserRole(UserRole userRole) {
        log.info(String.format("根据UserRole对象属性逻辑删除系统数据字典索引信息: %s ", userRole));
        boolean result = userRoleService.removeByBean(userRole);
        return RestResult.ok(result);
    }


    /**
     * 根据ID批量逻辑删除系统数据字典索引信息
     *
     * @param ids
     * @return
     */
    @PostMapping("/removeByIds")
    public RestResult removeByIds(Collection<Serializable> ids) {
        log.info(String.format("根据id批量删除系统数据字典索引信息: %s ", JSONUtil.toJsonStr(ids)));
        boolean result = userRoleService.removeByIds(ids);
        return RestResult.ok(result);
    }

    /**
     * 根据UserRole对象属性获取系统数据字典索引信息
     *
     * @param userRole
     * @return
     */
    @GetMapping("/getByUserRole")
    public RestResult getByUserRole(UserRole userRole) {
        userRole = userRoleService.getByBean(userRole);
        UserRoleVo userRoleVo = userRoleService.setVoProperties(userRole);
        log.info(String.format("根据id获取系统数据字典索引信息：s%", JSONUtil.toJsonStr(userRoleVo)));
        return RestResult.ok(userRoleVo);
    }

    /**
     * 根据UserRole对象属性检索所有系统数据字典索引信息
     *
     * @param userRole
     * @return
     */
    @GetMapping("/listByBean")
    public RestResult listByBean(UserRole userRole) {
        Collection<UserRole> userRoles = userRoleService.listByBean(userRole);
        Collection<UserRoleVo> userRoleVos = userRoleService.setVoProperties(userRoles);
        log.info(String.format("根据UserRole对象属性检索所有系统数据字典索引信息: %s ",JSONUtil.toJsonStr(userRoleVos)));
        return RestResult.ok(userRoleVos);
    }

    /**
     * 根据UserRole对象属性分页检索系统数据字典索引信息
     *
     * @param userRole
     * @return
     */
    @GetMapping("/pageByBean")
    public RestResult pageByBean(UserRole userRole) {
        IPage userRoles = userRoleService.pageByBean(userRole);
        userRoles.setRecords(userRoleService.setVoProperties(userRoles.getRecords()));
        log.info(String.format("根据UserRole对象属性分页检索系统数据字典索引信息: %s ",JSONUtil.toJsonStr(userRoles)));
        return RestResult.ok(userRoles);
    }

}
