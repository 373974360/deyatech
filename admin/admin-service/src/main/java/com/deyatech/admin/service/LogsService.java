package com.deyatech.admin.service;

import com.deyatech.admin.entity.Logs;
import com.deyatech.admin.vo.LogsVo;
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
public interface LogsService extends BaseService<Logs> {

    /**
     * 单个将对象转换为vo系统数据字典索引信息
     *
     * @param logs
     * @return
     */
    LogsVo setVoProperties(Logs logs);

    /**
     * 批量将对象转换为vo系统数据字典索引信息
     *
     * @param logss
     * @return
     */
    List<LogsVo> setVoProperties(Collection logss);
}
