package org.land.admin.controller;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.extern.slf4j.Slf4j;
import org.land.admin.entity.Dept;
import org.land.admin.service.DeptService;
import org.land.admin.vo.DeptVo;
import org.land.common.base.BaseController;
import org.land.common.entity.CascaderResult;
import org.land.common.entity.RestResult;
import org.land.common.utils.CascaderUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * <p>
 * 系统部门信息 前端控制器
 * </p>
 * @author: lee.
 * @since 2018-12-21
 */
@Slf4j
@RestController
@RequestMapping("/admin/dept")
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
    public RestResult saveOrUpdate(Dept dept) {
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
    public RestResult saveOrUpdateBatch(Collection<Dept> deptList) {
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
    public RestResult removeByDept(Dept dept) {
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
    public RestResult removeByIds(Collection<Serializable> ids) {
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
    public RestResult getByDept(Dept dept) {
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
    @GetMapping("/listByBean")
    public RestResult listByBean(Dept dept) {
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
    @GetMapping("/pageByBean")
    public RestResult pageByBean(Dept dept) {
        IPage depts = deptService.pageByBean(dept);
        depts.setRecords(deptService.setVoProperties(depts.getRecords()));
        log.info(String.format("根据Dept对象属性分页检索系统部门信息: %s ",JSONUtil.toJsonStr(depts)));
        return RestResult.ok(depts);
    }

    /**
     * 获取系统部门信息的tree对象
     *
     * @return
     */
    @GetMapping("/getTree")
    public RestResult getDeptTree() {
        Collection<DeptVo> deptTree = deptService.getDeptTree();
        log.info(String.format("获取系统部门信息的tree对象: %s ",JSONUtil.toJsonStr(deptTree)));
        return RestResult.ok(deptTree);
    }

    /**
     * 获取系统部门信息的级联对象
     *
     * @param id
     * @return
     */
    @GetMapping("/getCascader")
    public RestResult getCascader(String id) {
        Collection<DeptVo> deptVos = deptService.getDeptTree();
        List<CascaderResult> cascaderResults = CascaderUtil.getResult("Id", "Name","TreePosition", id, deptVos);
        log.info(String.format("获取系统部门信息的级联对象: %s ",JSONUtil.toJsonStr(cascaderResults)));
        return RestResult.ok(cascaderResults);
    }
}
