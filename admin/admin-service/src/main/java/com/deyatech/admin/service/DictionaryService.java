package com.deyatech.admin.service;

import com.deyatech.admin.entity.Dictionary;
import com.deyatech.admin.vo.DictionaryVo;
import com.deyatech.common.base.BaseService;
import java.util.Collection;
import java.util.List;

/**
 * <p>
 *  系统数据字典明细信息 服务类
 * </p>
 *
 * @Author lee.
 * @since 2019-03-07
 */
public interface DictionaryService extends BaseService<Dictionary> {


    /**
     * 逻辑删除字典索引的时候，根据字典索引同步逻辑删除字典子项目
     *
     * @param ids
     * @return
     */
    boolean removeByIndexIds(List<String> ids);

    /**
     * 根据字典英文代码和索引验证是否已存在
     *
     * @param dictionary
     * @return
     */
    boolean validataByCodeAndIndexId(Dictionary dictionary);

    /**
     * 单个将对象转换为vo系统数据字典明细信息
     *
     * @param dictionary
     * @return
     */
    DictionaryVo setVoProperties(Dictionary dictionary);

    /**
     * 批量将对象转换为vo系统数据字典明细信息
     *
     * @param dictionarys
     * @return
     */
    List<DictionaryVo> setVoProperties(Collection dictionarys);
}
