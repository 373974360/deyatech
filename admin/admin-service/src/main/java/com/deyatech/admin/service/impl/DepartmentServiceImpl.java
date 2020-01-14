package com.deyatech.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.deyatech.admin.entity.Department;
import com.deyatech.admin.vo.DepartmentVo;
import com.deyatech.admin.mapper.DepartmentMapper;
import com.deyatech.admin.service.DepartmentService;
import com.deyatech.common.base.BaseServiceImpl;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import com.deyatech.common.Constants;
import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.ObjectUtil;
import com.deyatech.common.entity.CascaderResult;
import com.deyatech.common.enums.EnableEnum;
import com.deyatech.common.utils.CascaderUtil;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * <p>
 * 系统部门信息 服务实现类
 * </p>
 *
 * @Author lee.
 * @since 2019-03-07
 */
@Service
public class DepartmentServiceImpl extends BaseServiceImpl<DepartmentMapper, Department> implements DepartmentService {

    /**
     * 可输入可选择控件使用
     *
     * @param department
     * @return
     */
    @Override
    public Map<String, Object> getCascaderAttach(Department department) {
        Map<String, Object> result = new HashMap<>();
        List<Map<String, String>> attachData = new ArrayList<>();
        Collection<Department> departments = super.listByBean(department);
        if (CollectionUtil.isNotEmpty(departments)) {
            departments.stream().forEach(d -> {
                Map<String, String> item = new HashMap<>();
                item.put("id", d.getId());
                item.put("parentId", d.getParentId());
                item.put("treePosition", Objects.isNull(d.getTreePosition()) ? "" : d.getTreePosition());
                attachData.add(item);
            });
        }
        Collection<DepartmentVo> departmentVos = this.getDepartmentTree(department);
        List<CascaderResult> cascaderData = CascaderUtil.getResult("Id", "Name","TreePosition", department.getId(), departmentVos);
        result.put("attachData", attachData);
        result.put("cascaderData", cascaderData);
        return result;
    }

    /**
     * 根据Department对象属性检索系统部门信息的tree对象
     *
     * @param department
     * @return
     */
    @Override
    public Collection<DepartmentVo> getDepartmentTree(Department department) {
        department.setSortSql("sortNo asc");
        List<DepartmentVo> departmentVos = setVoProperties(super.listByBean(department));
        List<DepartmentVo> rootDepartments = CollectionUtil.newArrayList();
        if (CollectionUtil.isNotEmpty(departmentVos)) {
            for (DepartmentVo departmentVo : departmentVos) {
                departmentVo.setLabel(departmentVo.getName());
                if(StrUtil.isNotBlank(departmentVo.getTreePosition())){
                    String[] split = departmentVo.getTreePosition().split(Constants.DEFAULT_TREE_POSITION_SPLIT);
                    departmentVo.setLevel(split.length);
                }else{
                    departmentVo.setLevel(Constants.DEFAULT_ROOT_LEVEL);
                }
                if (ObjectUtil.equal(departmentVo.getParentId(), Constants.ZERO)) {
                    rootDepartments.add(departmentVo);
                }
                for (DepartmentVo childVo : departmentVos) {
                    if (ObjectUtil.equal(childVo.getParentId(), departmentVo.getId())) {
                        if (ObjectUtil.isNull(departmentVo.getChildren())) {
                            List<DepartmentVo> children = CollectionUtil.newArrayList();
                            children.add(childVo);
                            departmentVo.setChildren(children);
                        } else {
                            departmentVo.getChildren().add(childVo);
                        }
                    }
                }
            }
        }
        return rootDepartments;
    }

    /**
     * 根据Department对象属性检索系统部门信息的tree对象
     *
     * @param department
     * @return
     */
    @Override
    public Collection<DepartmentVo> getAppealDepartmentTree(Department department) {
        String parentId = "0";
        if(StrUtil.isNotBlank(department.getParentId())){
            parentId = department.getParentId();
        }
        QueryWrapper<Department> queryWrapper = new QueryWrapper<>();
        if(parentId.equals("0")){
            queryWrapper.eq("parent_id",parentId);
        }else{
            queryWrapper.eq("id_",parentId);
        }
        List<DepartmentVo> departmentVos = setVoProperties(super.list(queryWrapper));
        List<DepartmentVo> rootDepartments = CollectionUtil.newArrayList();
        if (CollectionUtil.isNotEmpty(departmentVos)) {
            for (DepartmentVo departmentVo : departmentVos) {
                departmentVo.setLabel(departmentVo.getName());
                departmentVo.setChildren(getAppealDepartmentTree(departmentVo.getId(),0));
                if(StrUtil.isNotBlank(departmentVo.getTreePosition())){
                    String[] split = departmentVo.getTreePosition().split(Constants.DEFAULT_TREE_POSITION_SPLIT);
                    departmentVo.setLevel(split.length);
                }else{
                    departmentVo.setLevel(Constants.DEFAULT_ROOT_LEVEL);
                }
                rootDepartments.add(departmentVo);
            }
        }
        return rootDepartments;
    }

    public List<DepartmentVo> getAppealDepartmentTree(String parentId,Integer index) {
        List<DepartmentVo> rootDepartments = CollectionUtil.newArrayList();
        if(index == 2){
            return null;
        }
        QueryWrapper<Department> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("parent_id",parentId);
        List<DepartmentVo> departmentVos = setVoProperties(super.list(queryWrapper));
        if (CollectionUtil.isNotEmpty(departmentVos)) {
            for (DepartmentVo departmentVo : departmentVos) {
                departmentVo.setLabel(departmentVo.getName());
                if(StrUtil.isNotBlank(departmentVo.getTreePosition())){
                    String[] split = departmentVo.getTreePosition().split(Constants.DEFAULT_TREE_POSITION_SPLIT);
                    departmentVo.setLevel(split.length);
                }else{
                    departmentVo.setLevel(Constants.DEFAULT_ROOT_LEVEL);
                }
                departmentVo.setChildren(getAppealDepartmentTree(departmentVo.getId(),index+1));
                rootDepartments.add(departmentVo);
            }
        }
        return rootDepartments;
    }

    /**
     * 单个将对象转换为vo系统部门信息
     *
     * @param department
     * @return
     */
    @Override
    public DepartmentVo setVoProperties(Department department){
        DepartmentVo departmentVo = new DepartmentVo();
        BeanUtil.copyProperties(department, departmentVo);
        return departmentVo;
    }

    /**
     * 批量将对象转换为vo系统部门信息
     *
     * @param departments
     * @return
     */
    @Override
    public List<DepartmentVo> setVoProperties(Collection departments){
        List<DepartmentVo> departmentVos = CollectionUtil.newArrayList();
        if (CollectionUtil.isNotEmpty(departments)) {
            for (Object department : departments) {
                DepartmentVo departmentVo = new DepartmentVo();
                BeanUtil.copyProperties(department, departmentVo);
                departmentVos.add(departmentVo);
            }
        }
        return departmentVos;
    }

    @Override
    public List<Department> findByIds(List<String> ids) {
        QueryWrapper<Department> wrapper = new QueryWrapper<>();
        wrapper.in("id_", ids);
        wrapper.eq("enable_", EnableEnum.ENABLE.getCode());
        wrapper.orderByAsc("tree_position", "sort_no");
        return list(wrapper);
    }

    /**
     * 获取所有子部门ID
     *
     * @param departmentId
     * @return
     */
    @Override
    public List<String> getAllChildrenDepartmentIds(String departmentId) {
        List<Department> allDepartmentList = super.list();
        List<String> childrenDepartmentIds = new ArrayList<>();
        getChildrenDepartment(departmentId, allDepartmentList, childrenDepartmentIds);
        return childrenDepartmentIds;
    }

    private void getChildrenDepartment(String departmentId, Collection<Department> allDepartmentList, List<String> childrenDepartmentIds) {
        if (StrUtil.isEmpty(departmentId) || CollectionUtil.isEmpty(allDepartmentList) || childrenDepartmentIds == null) return;
        for (Department d : allDepartmentList) {
            if (departmentId.equals(d.getParentId())) {
                childrenDepartmentIds.add(d.getId());
                getChildrenDepartment(d.getId(), allDepartmentList, childrenDepartmentIds);
            }
        }
    }
}
