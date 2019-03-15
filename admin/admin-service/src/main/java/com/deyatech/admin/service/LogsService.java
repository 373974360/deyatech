package com.deyatech.admin.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.deyatech.admin.entity.Logs;
import com.deyatech.admin.vo.LogsVo;
import com.deyatech.common.base.BaseService;
import java.util.Collection;
import java.util.List;

/**
 * <p>
 *  系统日志信息 服务类
 * </p>
 *
 * @Author lee.
 * @since 2019-03-07
 */
public interface LogsService extends BaseService<Logs> {


    /**
     * 根据Logs对象属性分页检索系统日志信息
     *
     * @param logs
     * @return
     */
    IPage<LogsVo> selectListByBean(Logs logs);

    /**
     * 单个将对象转换为vo系统日志信息
     *
     * @param logs
     * @return
     */
    LogsVo setVoProperties(Logs logs);

    /**
     * 批量将对象转换为vo系统日志信息
     *
     * @param logss
     * @return
     */
    List<LogsVo> setVoProperties(Collection logss);
}
