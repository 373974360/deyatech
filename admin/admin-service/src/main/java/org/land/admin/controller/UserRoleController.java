package org.land.admin.controller;

import org.land.admin.entity.UserRole;
import org.land.admin.vo.UserRoleVo;
import org.land.admin.service.UserRoleService;
import org.land.common.entity.RestResult;
import cn.hutool.core.lang.Assert;
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
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

/**
 * <p>
 * 系统用户角色关联信息 前端控制器
 * </p>
 * @author: lee.
 * @since 2019-02-27
 */
@Slf4j
@RestController
@RequestMapping("/admin/userRole")
@Api(tags = {"系统用户角色关联信息接口"})
public class UserRoleController extends BaseController {
    @Autowired
    UserRoleService userRoleService;

    /**
     * 单个保存或者更新系统用户角色关联信息
     *
     * @param userRole
     * @return
     */
    @PostMapping("/saveOrUpdate")
    @ApiOperation(value="单个保存或者更新系统用户角色关联信息", notes="根据系统用户角色关联信息对象保存或者更新系统用户角色关联信息信息")
    @ApiImplicitParam(name = "userRole", value = "系统用户角色关联信息对象", required = true, dataType = "UserRole", paramType = "query")
    public RestResult<Boolean> saveOrUpdate(UserRole userRole) {
        Assert.notNull(userRole);
        log.info(String.format("保存或者更新系统用户角色关联信息: %s ", JSONUtil.toJsonStr(userRole)));
        boolean result = userRoleService.saveOrUpdate(userRole);
        return RestResult.ok(result);
    }

    /**
     * 批量保存或者更新系统用户角色关联信息
     *
     * @param userRoleList
     * @return
     */
    @PostMapping("/saveOrUpdateBatch")
    @ApiOperation(value="批量保存或者更新系统用户角色关联信息", notes="根据系统用户角色关联信息对象集合批量保存或者更新系统用户角色关联信息信息")
    @ApiImplicitParam(name = "userRoleList", value = "系统用户角色关联信息对象集合", required = true, allowMultiple = true, dataType = "UserRole", paramType = "query")
    public RestResult<Boolean> saveOrUpdateBatch(Collection<UserRole> userRoleList) {
        Assert.notNull(userRoleList);
        log.info(String.format("批量保存或者更新系统用户角色关联信息: %s ", JSONUtil.toJsonStr(userRoleList)));
        boolean result = userRoleService.saveOrUpdateBatch(userRoleList);
        return RestResult.ok(result);
    }

    /**
     * 根据UserRole对象属性逻辑删除系统用户角色关联信息
     *
     * @param userRole
     * @return
     */
    @PostMapping("/removeByUserRole")
    @ApiOperation(value="根据UserRole对象属性逻辑删除系统用户角色关联信息", notes="根据系统用户角色关联信息对象逻辑删除系统用户角色关联信息信息")
    @ApiImplicitParam(name = "userRole", value = "系统用户角色关联信息对象", required = true, dataType = "UserRole", paramType = "query")
    public RestResult<Boolean> removeByUserRole(UserRole userRole) {
        Assert.notNull(userRole);
        log.info(String.format("根据UserRole对象属性逻辑删除系统用户角色关联信息: %s ", userRole));
        boolean result = userRoleService.removeByBean(userRole);
        return RestResult.ok(result);
    }


    /**
     * 根据ID批量逻辑删除系统用户角色关联信息
     *
     * @param ids
     * @return
     */
    @PostMapping("/removeByIds")
    @ApiOperation(value="根据ID批量逻辑删除系统用户角色关联信息", notes="根据系统用户角色关联信息对象ID批量逻辑删除系统用户角色关联信息信息")
    @ApiImplicitParam(name = "ids", value = "系统用户角色关联信息对象ID集合", required = true, allowMultiple = true, dataType = "Serializable", paramType = "query")
    public RestResult<Boolean> removeByIds(Collection<Serializable> ids) {
        Assert.notNull(ids);
        log.info(String.format("根据id批量删除系统用户角色关联信息: %s ", JSONUtil.toJsonStr(ids)));
        boolean result = userRoleService.removeByIds(ids);
        return RestResult.ok(result);
    }

    /**
     * 根据UserRole对象属性获取系统用户角色关联信息
     *
     * @param userRole
     * @return
     */
    @GetMapping("/getByUserRole")
    @ApiOperation(value="根据UserRole对象属性获取系统用户角色关联信息", notes="根据系统用户角色关联信息对象属性获取系统用户角色关联信息信息")
    @ApiImplicitParam(name = "userRole", value = "系统用户角色关联信息对象", required = false, dataType = "UserRole", paramType = "query")
    public RestResult<UserRoleVo> getByUserRole(UserRole userRole) {
        userRole = userRoleService.getByBean(userRole);
        UserRoleVo userRoleVo = userRoleService.setVoProperties(userRole);
        log.info(String.format("根据id获取系统用户角色关联信息：s%", JSONUtil.toJsonStr(userRoleVo)));
        return RestResult.ok(userRoleVo);
    }

    /**
     * 根据UserRole对象属性检索所有系统用户角色关联信息
     *
     * @param userRole
     * @return
     */
    @GetMapping("/listByBean")
    @ApiOperation(value="根据UserRole对象属性检索所有系统用户角色关联信息", notes="根据UserRole对象属性检索所有系统用户角色关联信息信息")
    @ApiImplicitParam(name = "userRole", value = "系统用户角色关联信息对象", required = false, dataType = "UserRole", paramType = "query")
    public RestResult<Collection<UserRoleVo>> listByBean(UserRole userRole) {
        Collection<UserRole> userRoles = userRoleService.listByBean(userRole);
        Collection<UserRoleVo> userRoleVos = userRoleService.setVoProperties(userRoles);
        log.info(String.format("根据UserRole对象属性检索所有系统用户角色关联信息: %s ",JSONUtil.toJsonStr(userRoleVos)));
        return RestResult.ok(userRoleVos);
    }

    /**
     * 根据UserRole对象属性分页检索系统用户角色关联信息
     *
     * @param userRole
     * @return
     */
    @GetMapping("/pageByBean")
    @ApiOperation(value="根据UserRole对象属性分页检索系统用户角色关联信息", notes="根据UserRole对象属性分页检索系统用户角色关联信息信息")
    @ApiImplicitParam(name = "userRole", value = "系统用户角色关联信息对象", required = false, dataType = "UserRole", paramType = "query")
    public RestResult<IPage<UserRoleVo>> pageByBean(UserRole userRole) {
        IPage<UserRoleVo> userRoles = userRoleService.pageByBean(userRole);
        userRoles.setRecords(userRoleService.setVoProperties(userRoles.getRecords()));
        log.info(String.format("根据UserRole对象属性分页检索系统用户角色关联信息: %s ",JSONUtil.toJsonStr(userRoles)));
        return RestResult.ok(userRoles);
    }

}
