package com.deyatech.admin.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.deyatech.admin.entity.Logs;
import com.deyatech.admin.mapper.LogsMapper;
import com.deyatech.admin.service.LogsService;
import com.deyatech.admin.vo.LogsVo;
import com.deyatech.common.base.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

/**
 * <p>
 * 系统数据字典索引信息 服务实现类
 * </p>
 *
 * @Author lee.
 * @since 2019-03-07
 */
@Service
public class LogsServiceImpl extends BaseServiceImpl<LogsMapper, Logs> implements LogsService {

    @Autowired
    LogsMapper logsMapper;


    /**
     * 根据Logs对象属性分页检索系统日志信息
     *
     * @param logs
     * @return
     */
    @Override
    public IPage<LogsVo> findPage(Logs logs,String startTime,String endTime) {
        if(StrUtil.isNotEmpty(startTime)){
            startTime+=" 00:00:00";
        }
        if(StrUtil.isNotEmpty(endTime)){
            endTime+=" 23:59:59";
        }
        return logsMapper.findPage(new Page<>(logs.getPage(),logs.getSize()),logs,startTime,endTime);
    }

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
