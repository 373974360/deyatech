package com.deyatech.admin.service;

import com.deyatech.admin.entity.Department;
import com.deyatech.admin.vo.DepartmentVo;
import com.deyatech.common.base.BaseService;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  系统部门信息 服务类
 * </p>
 *
 * @Author lee.
 * @since 2019-03-07
 */
public interface DepartmentService extends BaseService<Department> {

    /**
     * 根据Department对象属性检索系统部门信息的tree对象
     *
     * @param department
     * @return
     */
    Collection<DepartmentVo> getDepartmentTree(Department department);

    /**
     * 根据Department对象属性检索系统部门信息的tree对象
     *
     * @param department
     * @return
     */
    Collection<DepartmentVo> getAppealDepartmentTree(Department department);

    /**
     * 单个将对象转换为vo系统部门信息
     *
     * @param department
     * @return
     */
    DepartmentVo setVoProperties(Department department);

    /**
     * 批量将对象转换为vo系统部门信息
     *
     * @param departments
     * @return
     */
    List<DepartmentVo> setVoProperties(Collection departments);

    /**
     * 根据部门id批量查询部门信息
     *
     * @param ids
     * @return
     */
    List<Department> findByIds(List<String> ids);

    /**
     * 获取所有子部门ID
     *
     * @param departmentId
     * @return
     */
    List<String> getAllChildrenDepartmentIds(String departmentId);

    /**
     * 可输入可选择控件使用
     *
     * @param department
     * @return
     */
    Map<String, Object> getCascaderAttach(Department department);
}
