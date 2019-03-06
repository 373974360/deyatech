package com.deyatech.admin.service;

import com.deyatech.admin.entity.Dept;
import com.deyatech.admin.vo.DeptVo;
import com.deyatech.common.base.BaseService;
import java.util.Collection;
import java.util.List;

/**
 * <p>
 *  系统部门信息 服务类
 * </p>
 *
 * @Author lee.
 * @since 2019-03-06
 */
public interface DeptService extends BaseService<Dept> {

    /**
     * 根据Dept对象属性检索系统部门信息的tree对象
     *
     * @param dept
     * @return
     */
    Collection<DeptVo> getDeptTree(Dept dept);

    /**
     * 单个将对象转换为vo系统部门信息
     *
     * @param dept
     * @return
     */
    DeptVo setVoProperties(Dept dept);

    /**
     * 批量将对象转换为vo系统部门信息
     *
     * @param depts
     * @return
     */
    List<DeptVo> setVoProperties(Collection depts);
}
