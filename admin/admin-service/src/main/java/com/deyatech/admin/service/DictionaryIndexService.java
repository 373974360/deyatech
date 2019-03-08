package com.deyatech.admin.service;

import com.deyatech.admin.entity.DictionaryIndex;
import com.deyatech.admin.vo.DictionaryIndexVo;
import com.deyatech.common.base.BaseService;
import java.util.Collection;
import java.util.List;

/**
 * <p>
 *  系统数据字典索引信息 服务类
 * </p>
 *
 * @Author lee.
 * @since 2019-03-07
 */
public interface DictionaryIndexService extends BaseService<DictionaryIndex> {

    /**
     * 单个将对象转换为vo系统数据字典索引信息
     *
     * @param dictionaryIndex
     * @return
     */
    DictionaryIndexVo setVoProperties(DictionaryIndex dictionaryIndex);

    /**
     * 批量将对象转换为vo系统数据字典索引信息
     *
     * @param dictionaryIndexs
     * @return
     */
    List<DictionaryIndexVo> setVoProperties(Collection dictionaryIndexs);
}
