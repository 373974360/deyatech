package com.deyatech.admin.service;

import com.deyatech.admin.entity.DictionaryIndex;
import com.deyatech.admin.vo.DictionaryIndexVo;
import com.deyatech.common.base.BaseService;
import com.deyatech.common.entity.EnumsResult;

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
     * 返回所有字典索引和字典子项目集合
     *
     * @return
     */
    List<EnumsResult> getDictsAll();


    /**
     * 根据字典索引验证是否已存在
     *
     * @param dictionaryIndex
     * @return
     */
    boolean validataByKey(DictionaryIndex dictionaryIndex);

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
