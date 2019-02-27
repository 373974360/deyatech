package org.land.admin.service.impl;

import org.land.admin.entity.Dept;
import org.land.admin.vo.DeptVo;
import org.land.admin.mapper.DeptMapper;
import org.land.admin.service.DeptService;
import org.land.common.base.BaseServiceImpl;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import org.land.common.Constants;
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
 * @since 2019-02-27
 */
@Service
public class DeptServiceImpl extends BaseServiceImpl<DeptMapper, Dept> implements DeptService {

    /**
     * 根据Dept对象属性检索系统部门信息的tree对象
     *
     * @return
     */
    @Override
    public Collection<DeptVo> getDeptTree() {
        List<DeptVo> deptVos = setVoProperties(super.list());
        List<DeptVo> rootDepts = CollectionUtil.newArrayList();
        if (CollectionUtil.isNotEmpty(deptVos)) {
            for (DeptVo deptVo : deptVos) {
                deptVo.setLabel(deptVo.getName());
                if(StrUtil.isNotBlank(deptVo.getTreePosition())){
                    String[] split = deptVo.getTreePosition().split(Constants.DEFAULT_TREE_POSITION_SPLIT);
                    deptVo.setLevel(split.length);
                }else{
                    deptVo.setLevel(Constants.DEFAULT_ROOT_LEVEL);
                }
                if (ObjectUtil.equal(deptVo.getParentId(), Constants.DEFAULT_PARENT_ROOT)) {
                    rootDepts.add(deptVo);
                }
                for (DeptVo childVo : deptVos) {
                    if (ObjectUtil.equal(childVo.getParentId(), deptVo.getId())) {
                        if (ObjectUtil.isNull(deptVo.getChildren())) {
                            List<DeptVo> children = CollectionUtil.newArrayList();
                            children.add(childVo);
                            deptVo.setChildren(children);
                        } else {
                            deptVo.getChildren().add(childVo);
                        }
                    }
                }
            }
        }
        return rootDepts;
    }

    /**
     * 单个将对象转换为vo系统部门信息
     *
     * @param dept
     * @return
     */
    @Override
    public DeptVo setVoProperties(Dept dept){
        DeptVo deptVo = new DeptVo();
        BeanUtil.copyProperties(dept, deptVo);
        return deptVo;
    }

    /**
     * 批量将对象转换为vo系统部门信息
     *
     * @param depts
     * @return
     */
    @Override
    public List<DeptVo> setVoProperties(Collection depts){
        List<DeptVo> deptVos = CollectionUtil.newArrayList();
        if (CollectionUtil.isNotEmpty(depts)) {
            for (Object dept : depts) {
                DeptVo deptVo = new DeptVo();
                BeanUtil.copyProperties(dept, deptVo);
                deptVos.add(deptVo);
            }
        }
        return deptVos;
    }
}
