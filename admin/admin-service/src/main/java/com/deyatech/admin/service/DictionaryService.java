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
