package com.deyatech.admin.service.impl;

import com.deyatech.admin.entity.DictionaryIndex;
import com.deyatech.admin.vo.DictionaryIndexVo;
import com.deyatech.admin.mapper.DictionaryIndexMapper;
import com.deyatech.admin.service.DictionaryIndexService;
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
 * @since 2019-03-07
 */
@Service
public class DictionaryIndexServiceImpl extends BaseServiceImpl<DictionaryIndexMapper, DictionaryIndex> implements DictionaryIndexService {

    /**
     * 单个将对象转换为vo系统数据字典索引信息
     *
     * @param dictionaryIndex
     * @return
     */
    @Override
    public DictionaryIndexVo setVoProperties(DictionaryIndex dictionaryIndex){
        DictionaryIndexVo dictionaryIndexVo = new DictionaryIndexVo();
        BeanUtil.copyProperties(dictionaryIndex, dictionaryIndexVo);
        return dictionaryIndexVo;
    }

    /**
     * 批量将对象转换为vo系统数据字典索引信息
     *
     * @param dictionaryIndexs
     * @return
     */
    @Override
    public List<DictionaryIndexVo> setVoProperties(Collection dictionaryIndexs){
        List<DictionaryIndexVo> dictionaryIndexVos = CollectionUtil.newArrayList();
        if (CollectionUtil.isNotEmpty(dictionaryIndexs)) {
            for (Object dictionaryIndex : dictionaryIndexs) {
                DictionaryIndexVo dictionaryIndexVo = new DictionaryIndexVo();
                BeanUtil.copyProperties(dictionaryIndex, dictionaryIndexVo);
                dictionaryIndexVos.add(dictionaryIndexVo);
            }
        }
        return dictionaryIndexVos;
    }
}
