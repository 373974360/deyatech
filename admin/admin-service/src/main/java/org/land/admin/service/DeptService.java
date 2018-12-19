package org.land.admin.service;

import org.land.admin.entity.Dept;
import org.land.admin.vo.DeptVo;
import org.land.common.base.BaseService;
import java.util.Collection;
import java.util.List;

/**
 * <p>
 *  系统部门信息 服务类
 * </p>
 *
 * @Author lee.
 * @since 2018-12-19
 */
public interface DeptService extends BaseService<Dept> {

    /**
     * 根据Dept对象属性检索系统部门信息的tree对象
     *
     * @return
     */
    Collection<DeptVo> getDeptTree();

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
