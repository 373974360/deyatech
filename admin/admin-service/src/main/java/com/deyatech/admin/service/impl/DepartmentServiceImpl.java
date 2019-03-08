package com.deyatech.admin.service.impl;

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
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Collection;

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
                if (ObjectUtil.equal(departmentVo.getParentId(), Constants.DEFAULT_PARENT_ROOT)) {
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
}