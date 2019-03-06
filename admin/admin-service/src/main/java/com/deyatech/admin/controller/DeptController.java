package com.deyatech.admin.controller;

import com.deyatech.admin.entity.Dept;
import com.deyatech.admin.vo.DeptVo;
import com.deyatech.admin.service.DeptService;
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
 * @since 2019-03-06
 */
@Slf4j
@RestController
@RequestMapping("/admin/dept")
@Api(tags = {"系统部门信息接口"})
public class DeptController extends BaseController {
    @Autowired
    DeptService deptService;

    /**
     * 单个保存或者更新系统部门信息
     *
     * @param dept
     * @return
     */
    @PostMapping("/saveOrUpdate")
    @ApiOperation(value="单个保存或者更新系统部门信息", notes="根据系统部门信息对象保存或者更新系统部门信息信息")
    @ApiImplicitParam(name = "dept", value = "系统部门信息对象", required = true, dataType = "Dept", paramType = "query")
    public RestResult<Boolean> saveOrUpdate(Dept dept) {
        log.info(String.format("保存或者更新系统部门信息: %s ", JSONUtil.toJsonStr(dept)));
        boolean result = deptService.saveOrUpdate(dept);
        return RestResult.ok(result);
    }

    /**
     * 批量保存或者更新系统部门信息
     *
     * @param deptList
     * @return
     */
    @PostMapping("/saveOrUpdateBatch")
    @ApiOperation(value="批量保存或者更新系统部门信息", notes="根据系统部门信息对象集合批量保存或者更新系统部门信息信息")
    @ApiImplicitParam(name = "deptList", value = "系统部门信息对象集合", required = true, allowMultiple = true, dataType = "Dept", paramType = "query")
    public RestResult<Boolean> saveOrUpdateBatch(Collection<Dept> deptList) {
        log.info(String.format("批量保存或者更新系统部门信息: %s ", JSONUtil.toJsonStr(deptList)));
        boolean result = deptService.saveOrUpdateBatch(deptList);
        return RestResult.ok(result);
    }

    /**
     * 根据Dept对象属性逻辑删除系统部门信息
     *
     * @param dept
     * @return
     */
    @PostMapping("/removeByDept")
    @ApiOperation(value="根据Dept对象属性逻辑删除系统部门信息", notes="根据系统部门信息对象逻辑删除系统部门信息信息")
    @ApiImplicitParam(name = "dept", value = "系统部门信息对象", required = true, dataType = "Dept", paramType = "query")
    public RestResult<Boolean> removeByDept(Dept dept) {
        log.info(String.format("根据Dept对象属性逻辑删除系统部门信息: %s ", dept));
        boolean result = deptService.removeByBean(dept);
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
        boolean result = deptService.removeByIds(ids);
        return RestResult.ok(result);
    }

    /**
     * 根据Dept对象属性获取系统部门信息
     *
     * @param dept
     * @return
     */
    @GetMapping("/getByDept")
    @ApiOperation(value="根据Dept对象属性获取系统部门信息", notes="根据系统部门信息对象属性获取系统部门信息信息")
    @ApiImplicitParam(name = "dept", value = "系统部门信息对象", required = false, dataType = "Dept", paramType = "query")
    public RestResult<DeptVo> getByDept(Dept dept) {
        dept = deptService.getByBean(dept);
        DeptVo deptVo = deptService.setVoProperties(dept);
        log.info(String.format("根据id获取系统部门信息：s%", JSONUtil.toJsonStr(deptVo)));
        return RestResult.ok(deptVo);
    }

    /**
     * 根据Dept对象属性检索所有系统部门信息
     *
     * @param dept
     * @return
     */
    @GetMapping("/listByDept")
    @ApiOperation(value="根据Dept对象属性检索所有系统部门信息", notes="根据Dept对象属性检索所有系统部门信息信息")
    @ApiImplicitParam(name = "dept", value = "系统部门信息对象", required = false, dataType = "Dept", paramType = "query")
    public RestResult<Collection<DeptVo>> listByDept(Dept dept) {
        Collection<Dept> depts = deptService.listByBean(dept);
        Collection<DeptVo> deptVos = deptService.setVoProperties(depts);
        log.info(String.format("根据Dept对象属性检索所有系统部门信息: %s ",JSONUtil.toJsonStr(deptVos)));
        return RestResult.ok(deptVos);
    }

    /**
     * 根据Dept对象属性分页检索系统部门信息
     *
     * @param dept
     * @return
     */
    @GetMapping("/pageByDept")
    @ApiOperation(value="根据Dept对象属性分页检索系统部门信息", notes="根据Dept对象属性分页检索系统部门信息信息")
    @ApiImplicitParam(name = "dept", value = "系统部门信息对象", required = false, dataType = "Dept", paramType = "query")
    public RestResult<IPage<DeptVo>> pageByDept(Dept dept) {
        IPage<DeptVo> depts = deptService.pageByBean(dept);
        depts.setRecords(deptService.setVoProperties(depts.getRecords()));
        log.info(String.format("根据Dept对象属性分页检索系统部门信息: %s ",JSONUtil.toJsonStr(depts)));
        return RestResult.ok(depts);
    }

    /**
     * 获取系统部门信息的tree对象
     *
     * @param dept
     * @return
     */
    @GetMapping("/getTree")
    @ApiOperation(value="获取系统部门信息的tree对象", notes="获取系统部门信息的tree对象")
    public RestResult<Collection<DeptVo>> getDeptTree(Dept dept) {
        Collection<DeptVo> deptTree = deptService.getDeptTree(dept);
        log.info(String.format("获取系统部门信息的tree对象: %s ",JSONUtil.toJsonStr(deptTree)));
        return RestResult.ok(deptTree);
    }

    /**
     * 获取系统部门信息的级联对象
     *
     * @param dept
     * @return
     */
    @GetMapping("/getCascader")
    @ApiOperation(value="获取系统部门信息的级联对象", notes="获取系统部门信息的级联对象")
    @ApiImplicitParam(name = "dept", value = "dept", required = false, dataType = "Dept", paramType = "query")
    public RestResult<List<CascaderResult>> getCascader(Dept dept) {
        Collection<DeptVo> deptVos = deptService.getDeptTree(dept);
        List<CascaderResult> cascaderResults = CascaderUtil.getResult("Id", "Name","TreePosition", dept.getId(), deptVos);
        log.info(String.format("获取系统部门信息的级联对象: %s ",JSONUtil.toJsonStr(cascaderResults)));
        return RestResult.ok(cascaderResults);
    }
}