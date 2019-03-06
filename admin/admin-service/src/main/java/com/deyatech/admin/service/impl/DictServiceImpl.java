package com.deyatech.admin.service.impl;

import com.deyatech.admin.entity.Dict;
import com.deyatech.admin.vo.DictVo;
import com.deyatech.admin.mapper.DictMapper;
import com.deyatech.admin.service.DictService;
import com.deyatech.common.base.BaseServiceImpl;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Collection;

/**
 * <p>
 * 系统数据字典明细信息 服务实现类
 * </p>
 *
 * @Author lee.
 * @since 2019-03-06
 */
@Service
public class DictServiceImpl extends BaseServiceImpl<DictMapper, Dict> implements DictService {

    /**
     * 单个将对象转换为vo系统数据字典明细信息
     *
     * @param dict
     * @return
     */
    @Override
    public DictVo setVoProperties(Dict dict){
        DictVo dictVo = new DictVo();
        BeanUtil.copyProperties(dict, dictVo);
        return dictVo;
    }

    /**
     * 批量将对象转换为vo系统数据字典明细信息
     *
     * @param dicts
     * @return
     */
    @Override
    public List<DictVo> setVoProperties(Collection dicts){
        List<DictVo> dictVos = CollectionUtil.newArrayList();
        if (CollectionUtil.isNotEmpty(dicts)) {
            for (Object dict : dicts) {
                DictVo dictVo = new DictVo();
                BeanUtil.copyProperties(dict, dictVo);
                dictVos.add(dictVo);
            }
        }
        return dictVos;
    }
}
