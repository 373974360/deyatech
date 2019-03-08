package com.deyatech.admin.controller;

import com.deyatech.admin.entity.Department;
import com.deyatech.admin.vo.DepartmentVo;
import com.deyatech.admin.service.DepartmentService;
import com.deyatech.common.entity.RestResult;
import com.deyatech.common.entity.CascaderResult;
import com.deyatech.common.utils.CascaderUtil;
import java.util.List;
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
import com.deyatech.common.base.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

/**
 * <p>
 * 系统部门信息 前端控制器
 * </p>
 * @author: lee.
 * @since 2019-03-07
 */
@Slf4j
@RestController
@RequestMapping("/admin/department")
@Api(tags = {"系统部门信息接口"})
public class DepartmentController extends BaseController {
    @Autowired
    DepartmentService departmentService;

    /**
     * 单个保存或者更新系统部门信息
     *
     * @param department
     * @return
     */
    @PostMapping("/saveOrUpdate")
    @ApiOperation(value="单个保存或者更新系统部门信息", notes="根据系统部门信息对象保存或者更新系统部门信息信息")
    @ApiImplicitParam(name = "department", value = "系统部门信息对象", required = true, dataType = "Department", paramType = "query")
    public RestResult<Boolean> saveOrUpdate(Department department) {
        log.info(String.format("保存或者更新系统部门信息: %s ", JSONUtil.toJsonStr(department)));
        boolean result = departmentService.saveOrUpdate(department);
        return RestResult.ok(result);
    }

    /**
     * 批量保存或者更新系统部门信息
     *
     * @param departmentList
     * @return
     */
    @PostMapping("/saveOrUpdateBatch")
    @ApiOperation(value="批量保存或者更新系统部门信息", notes="根据系统部门信息对象集合批量保存或者更新系统部门信息信息")
    @ApiImplicitParam(name = "departmentList", value = "系统部门信息对象集合", required = true, allowMultiple = true, dataType = "Department", paramType = "query")
    public RestResult<Boolean> saveOrUpdateBatch(Collection<Department> departmentList) {
        log.info(String.format("批量保存或者更新系统部门信息: %s ", JSONUtil.toJsonStr(departmentList)));
        boolean result = departmentService.saveOrUpdateBatch(departmentList);
        return RestResult.ok(result);
    }

    /**
     * 根据Department对象属性逻辑删除系统部门信息
     *
     * @param department
     * @return
     */
    @PostMapping("/removeByDepartment")
    @ApiOperation(value="根据Department对象属性逻辑删除系统部门信息", notes="根据系统部门信息对象逻辑删除系统部门信息信息")
    @ApiImplicitParam(name = "department", value = "系统部门信息对象", required = true, dataType = "Department", paramType = "query")
    public RestResult<Boolean> removeByDepartment(Department department) {
        log.info(String.format("根据Department对象属性逻辑删除系统部门信息: %s ", department));
        boolean result = departmentService.removeByBean(department);
        return RestResult.ok(result);
    }


    /**
     * 根据ID批量逻辑删除系统部门信息
     *
     * @param ids
     * @return
     */
    @PostMapping("/removeByIds")
    @ApiOperation(value="根据ID批量逻辑删除系统部门信息", notes="根据系统部门信息对象ID批量逻辑删除系统部门信息信息")
    @ApiImplicitParam(name = "ids", value = "系统部门信息对象ID集合", required = true, allowMultiple = true, dataType = "Serializable", paramType = "query")
    public RestResult<Boolean> removeByIds(Collection<Serializable> ids) {
        log.info(String.format("根据id批量删除系统部门信息: %s ", JSONUtil.toJsonStr(ids)));
        boolean result = departmentService.removeByIds(ids);
        return RestResult.ok(result);
    }

    /**
     * 根据Department对象属性获取系统部门信息
     *
     * @param department
     * @return
     */
    @GetMapping("/getByDepartment")
    @ApiOperation(value="根据Department对象属性获取系统部门信息", notes="根据系统部门信息对象属性获取系统部门信息信息")
    @ApiImplicitParam(name = "department", value = "系统部门信息对象", required = false, dataType = "Department", paramType = "query")
    public RestResult<DepartmentVo> getByDepartment(Department department) {
        department = departmentService.getByBean(department);
        DepartmentVo departmentVo = departmentService.setVoProperties(department);
        log.info(String.format("根据id获取系统部门信息：s%", JSONUtil.toJsonStr(departmentVo)));
        return RestResult.ok(departmentVo);
    }

    /**
     * 根据Department对象属性检索所有系统部门信息
     *
     * @param department
     * @return
     */
    @GetMapping("/listByDepartment")
    @ApiOperation(value="根据Department对象属性检索所有系统部门信息", notes="根据Department对象属性检索所有系统部门信息信息")
    @ApiImplicitParam(name = "department", value = "系统部门信息对象", required = false, dataType = "Department", paramType = "query")
    public RestResult<Collection<DepartmentVo>> listByDepartment(Department department) {
        Collection<Department> departments = departmentService.listByBean(department);
        Collection<DepartmentVo> departmentVos = departmentService.setVoProperties(departments);
        log.info(String.format("根据Department对象属性检索所有系统部门信息: %s ",JSONUtil.toJsonStr(departmentVos)));
        return RestResult.ok(departmentVos);
    }

    /**
     * 根据Department对象属性分页检索系统部门信息
     *
     * @param department
     * @return
     */
    @GetMapping("/pageByDepartment")
    @ApiOperation(value="根据Department对象属性分页检索系统部门信息", notes="根据Department对象属性分页检索系统部门信息信息")
    @ApiImplicitParam(name = "department", value = "系统部门信息对象", required = false, dataType = "Department", paramType = "query")
    public RestResult<IPage<DepartmentVo>> pageByDepartment(Department department) {
        IPage<DepartmentVo> departments = departmentService.pageByBean(department);
        departments.setRecords(departmentService.setVoProperties(departments.getRecords()));
        log.info(String.format("根据Department对象属性分页检索系统部门信息: %s ",JSONUtil.toJsonStr(departments)));
        return RestResult.ok(departments);
    }

    /**
     * 获取系统部门信息的tree对象
     *
     * @param department
     * @return
     */
    @GetMapping("/getTree")
    @ApiOperation(value="获取系统部门信息的tree对象", notes="获取系统部门信息的tree对象")
    public RestResult<Collection<DepartmentVo>> getDepartmentTree(Department department) {
        Collection<DepartmentVo> departmentTree = departmentService.getDepartmentTree(department);
        log.info(String.format("获取系统部门信息的tree对象: %s ",JSONUtil.toJsonStr(departmentTree)));
        return RestResult.ok(departmentTree);
    }

    /**
     * 获取系统部门信息的级联对象
     *
     * @param department
     * @return
     */
    @GetMapping("/getCascader")
    @ApiOperation(value="获取系统部门信息的级联对象", notes="获取系统部门信息的级联对象")
    @ApiImplicitParam(name = "department", value = "department", required = false, dataType = "Department", paramType = "query")
    public RestResult<List<CascaderResult>> getCascader(Department department) {
        Collection<DepartmentVo> departmentVos = departmentService.getDepartmentTree(department);
        List<CascaderResult> cascaderResults = CascaderUtil.getResult("Id", "Name","TreePosition", department.getId(), departmentVos);
        log.info(String.format("获取系统部门信息的级联对象: %s ",JSONUtil.toJsonStr(cascaderResults)));
        return RestResult.ok(cascaderResults);
    }
}