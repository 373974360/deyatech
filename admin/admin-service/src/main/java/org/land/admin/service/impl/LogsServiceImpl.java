package org.land.admin.service.impl;

import org.land.admin.entity.Logs;
import org.land.admin.vo.LogsVo;
import org.land.admin.mapper.LogsMapper;
import org.land.admin.service.LogsService;
import org.land.common.base.BaseServiceImpl;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Collection;

/**
 * <p>
 * 系统数据字典索引信息 服务实现类
 * </p>
 *
 * @Author lee.
 * @since 2018-12-19
 */
@Service
public class LogsServiceImpl extends BaseServiceImpl<LogsMapper, Logs> implements LogsService {

    /**
     * 单个将对象转换为vo系统数据字典索引信息
     *
     * @param logs
     * @return
     */
    @Override
    public LogsVo setVoProperties(Logs logs){
        LogsVo logsVo = new LogsVo();
        BeanUtil.copyProperties(logs, logsVo);
        return logsVo;
    }

    /**
     * 批量将对象转换为vo系统数据字典索引信息
     *
     * @param logss
     * @return
     */
    @Override
    public List<LogsVo> setVoProperties(Collection logss){
        List<LogsVo> logsVos = CollectionUtil.newArrayList();
        if (CollectionUtil.isNotEmpty(logss)) {
            for (Object logs : logss) {
                LogsVo logsVo = new LogsVo();
                BeanUtil.copyProperties(logs, logsVo);
                logsVos.add(logsVo);
            }
        }
        return logsVos;
    }
}
