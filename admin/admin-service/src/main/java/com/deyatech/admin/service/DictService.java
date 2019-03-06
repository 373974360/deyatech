package com.deyatech.admin.service;

import com.deyatech.admin.entity.Dict;
import com.deyatech.admin.vo.DictVo;
import com.deyatech.common.base.BaseService;
import java.util.Collection;
import java.util.List;

/**
 * <p>
 *  系统数据字典明细信息 服务类
 * </p>
 *
 * @Author lee.
 * @since 2019-03-06
 */
public interface DictService extends BaseService<Dict> {

    /**
     * 单个将对象转换为vo系统数据字典明细信息
     *
     * @param dict
     * @return
     */
    DictVo setVoProperties(Dict dict);

    /**
     * 批量将对象转换为vo系统数据字典明细信息
     *
     * @param dicts
     * @return
     */
    List<DictVo> setVoProperties(Collection dicts);
}
