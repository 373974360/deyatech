package org.land.admin.service.impl;

import org.land.admin.entity.DictIndex;
import org.land.admin.vo.DictIndexVo;
import org.land.admin.mapper.DictIndexMapper;
import org.land.admin.service.DictIndexService;
import org.land.common.base.BaseServiceImpl;
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
 * @since 2018-12-19
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
