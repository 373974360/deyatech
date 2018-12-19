package org.land.admin.service;

import org.land.admin.entity.Dict;
import org.land.admin.vo.DictVo;
import org.land.common.base.BaseService;
import java.util.Collection;
import java.util.List;

/**
 * <p>
 *  系统数据字典明细信息 服务类
 * </p>
 *
 * @Author lee.
 * @since 2018-12-19
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
