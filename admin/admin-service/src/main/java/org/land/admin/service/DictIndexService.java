package org.land.admin.service;

import org.land.admin.entity.DictIndex;
import org.land.admin.vo.DictIndexVo;
import org.land.common.base.BaseService;
import java.util.Collection;
import java.util.List;

/**
 * <p>
 *  系统数据字典索引信息 服务类
 * </p>
 *
 * @Author lee.
 * @since 2019-02-27
 */
public interface DictIndexService extends BaseService<DictIndex> {

    /**
     * 单个将对象转换为vo系统数据字典索引信息
     *
     * @param dictIndex
     * @return
     */
    DictIndexVo setVoProperties(DictIndex dictIndex);

    /**
     * 批量将对象转换为vo系统数据字典索引信息
     *
     * @param dictIndexs
     * @return
     */
    List<DictIndexVo> setVoProperties(Collection dictIndexs);
}
