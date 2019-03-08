package com.deyatech.admin.service.impl;

import com.deyatech.admin.entity.Dictionary;
import com.deyatech.admin.vo.DictionaryVo;
import com.deyatech.admin.mapper.DictionaryMapper;
import com.deyatech.admin.service.DictionaryService;
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
 * @since 2019-03-07
 */
@Service
public class DictionaryServiceImpl extends BaseServiceImpl<DictionaryMapper, Dictionary> implements DictionaryService {

    /**
     * 单个将对象转换为vo系统数据字典明细信息
     *
     * @param dictionary
     * @return
     */
    @Override
    public DictionaryVo setVoProperties(Dictionary dictionary){
        DictionaryVo dictionaryVo = new DictionaryVo();
        BeanUtil.copyProperties(dictionary, dictionaryVo);
        return dictionaryVo;
    }

    /**
     * 批量将对象转换为vo系统数据字典明细信息
     *
     * @param dictionarys
     * @return
     */
    @Override
    public List<DictionaryVo> setVoProperties(Collection dictionarys){
        List<DictionaryVo> dictionaryVos = CollectionUtil.newArrayList();
        if (CollectionUtil.isNotEmpty(dictionarys)) {
            for (Object dictionary : dictionarys) {
                DictionaryVo dictionaryVo = new DictionaryVo();
                BeanUtil.copyProperties(dictionary, dictionaryVo);
                dictionaryVos.add(dictionaryVo);
            }
        }
        return dictionaryVos;
    }
}
