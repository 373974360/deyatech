package com.deyatech.admin.service.impl;

import com.deyatech.admin.entity.DictIndex;
import com.deyatech.admin.vo.DictIndexVo;
import com.deyatech.admin.mapper.DictIndexMapper;
import com.deyatech.admin.service.DictIndexService;
import com.deyatech.common.base.BaseServiceImpl;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Collection;

/**
 * <p>
 * 系统数据字典索引信息 服务实现类
 * </p>
 *
 * @Author lee.
 * @since 2019-03-06
 */
@Service
public class DictIndexServiceImpl extends BaseServiceImpl<DictIndexMapper, DictIndex> implements DictIndexService {

    /**
     * 单个将对象转换为vo系统数据字典索引信息
     *
     * @param dictIndex
     * @return
     */
    @Override
    public DictIndexVo setVoProperties(DictIndex dictIndex){
        DictIndexVo dictIndexVo = new DictIndexVo();
        BeanUtil.copyProperties(dictIndex, dictIndexVo);
        return dictIndexVo;
    }

    /**
     * 批量将对象转换为vo系统数据字典索引信息
     *
     * @param dictIndexs
     * @return
     */
    @Override
    public List<DictIndexVo> setVoProperties(Collection dictIndexs){
        List<DictIndexVo> dictIndexVos = CollectionUtil.newArrayList();
        if (CollectionUtil.isNotEmpty(dictIndexs)) {
            for (Object dictIndex : dictIndexs) {
                DictIndexVo dictIndexVo = new DictIndexVo();
                BeanUtil.copyProperties(dictIndex, dictIndexVo);
                dictIndexVos.add(dictIndexVo);
            }
        }
        return dictIndexVos;
    }
}
