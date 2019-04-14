package com.deyatech.admin.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.deyatech.admin.entity.Dictionary;
import com.deyatech.admin.entity.DictionaryIndex;
import com.deyatech.admin.service.DictionaryService;
import com.deyatech.admin.vo.DictionaryIndexVo;
import com.deyatech.admin.mapper.DictionaryIndexMapper;
import com.deyatech.admin.service.DictionaryIndexService;
import com.deyatech.common.Constants;
import com.deyatech.common.base.BaseServiceImpl;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import com.deyatech.common.entity.EnumsResult;
import com.deyatech.common.entity.RestResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * <p>
 * 系统数据字典索引信息 服务实现类
 * </p>
 *
 * @Author lee.
 * @since 2019-03-07
 */
@Service
@CacheConfig(cacheNames = Constants.CACHE_NAMESPACE + "dicIndex")
public class DictionaryIndexServiceImpl extends BaseServiceImpl<DictionaryIndexMapper, DictionaryIndex> implements DictionaryIndexService {

    @Autowired
    DictionaryService dictionaryService;


    /**
     * 返回所有字典索引和字典子项目集合
     *
     * @return
     */
    @Override
    @Cacheable
    public List<EnumsResult> getDictsAll() {
        List<EnumsResult> resultList = new ArrayList<>();
        List<DictionaryIndex> dictionaryIndexList = getBaseMapper().selectList(null);
        List<Dictionary> dictionaryList = dictionaryService.getBaseMapper().selectList(null);
        if(!dictionaryIndexList.isEmpty()){
            for(DictionaryIndex dictionaryIndex:dictionaryIndexList){
                EnumsResult enumsResult = new EnumsResult();
                enumsResult.setName(dictionaryIndex.getKey());
                List<Map<String,Object>> valueList = new ArrayList<>();
                if(!dictionaryList.isEmpty()){
                    for(Dictionary dictionary:dictionaryList){
                        if(dictionaryIndex.getKey().equals(dictionary.getIndexId())){
                            HashMap<String,Object> map = new HashMap<>();
                            map.put("code",dictionary.getCode());
                            map.put("value",dictionary.getCodeText());
                            valueList.add(map);
                        }
                    }
                }
                enumsResult.setValue(valueList);
                resultList.add(enumsResult);
            }
        }
        return resultList;
    }

    /**
     * 根据字典索引验证是否已存在
     *
     * @param dictionaryIndex
     * @return
     */
    @Override
    public boolean validataByKey(DictionaryIndex dictionaryIndex) {
        if(StrUtil.isEmpty(dictionaryIndex.getId())){
            List<DictionaryIndex> list = getBaseMapper().selectList(new QueryWrapper<DictionaryIndex>()
                    .eq("key_",dictionaryIndex.getKey()));
            if(!list.isEmpty()){
                return false;
            }
        }else{
            List<DictionaryIndex> list = getBaseMapper().selectList(new QueryWrapper<DictionaryIndex>()
                    .eq("key_",dictionaryIndex.getKey())
                    .notIn("id_",dictionaryIndex.getId()));
            if(!list.isEmpty()){
                return false;
            }
        }
        return true;
    }

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
