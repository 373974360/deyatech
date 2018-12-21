package org.land.admin.controller;

import org.land.admin.entity.Logs;
import org.land.admin.vo.LogsVo;
import org.land.admin.service.LogsService;
import org.land.common.entity.RestResult;
import lombok.extern.slf4j.Slf4j;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import java.io.Serializable;
import java.util.Collection;
import org.springframework.web.bind.annotation.RestController;
import org.land.common.base.BaseController;

/**
 * <p>
 * 系统数据字典索引信息 前端控制器
 * </p>
 * @author: lee.
 * @since 2018-12-21
 */
@Slf4j
@RestController
@RequestMapping("/admin/logs")
public class LogsController extends BaseController {
    @Autowired
    LogsService logsService;

    /**
     * 单个保存或者更新系统数据字典索引信息
     *
     * @param logs
     * @return
     */
    @PostMapping("/saveOrUpdate")
    public RestResult saveOrUpdate(Logs logs) {
        log.info(String.format("保存或者更新系统数据字典索引信息: %s ", JSONUtil.toJsonStr(logs)));
        boolean result = logsService.saveOrUpdate(logs);
        return RestResult.ok(result);
    }

    /**
     * 批量保存或者更新系统数据字典索引信息
     *
     * @param logsList
     * @return
     */
    @PostMapping("/saveOrUpdateBatch")
    public RestResult saveOrUpdateBatch(Collection<Logs> logsList) {
        log.info(String.format("批量保存或者更新系统数据字典索引信息: %s ", JSONUtil.toJsonStr(logsList)));
        boolean result = logsService.saveOrUpdateBatch(logsList);
        return RestResult.ok(result);
    }

    /**
     * 根据Logs对象属性逻辑删除系统数据字典索引信息
     *
     * @param logs
     * @return
     */
    @PostMapping("/removeByLogs")
    public RestResult removeByLogs(Logs logs) {
        log.info(String.format("根据Logs对象属性逻辑删除系统数据字典索引信息: %s ", logs));
        boolean result = logsService.removeByBean(logs);
        return RestResult.ok(result);
    }


    /**
     * 根据ID批量逻辑删除系统数据字典索引信息
     *
     * @param ids
     * @return
     */
    @PostMapping("/removeByIds")
    public RestResult removeByIds(Collection<Serializable> ids) {
        log.info(String.format("根据id批量删除系统数据字典索引信息: %s ", JSONUtil.toJsonStr(ids)));
        boolean result = logsService.removeByIds(ids);
        return RestResult.ok(result);
    }

    /**
     * 根据Logs对象属性获取系统数据字典索引信息
     *
     * @param logs
     * @return
     */
    @GetMapping("/getByLogs")
    public RestResult getByLogs(Logs logs) {
        logs = logsService.getByBean(logs);
        LogsVo logsVo = logsService.setVoProperties(logs);
        log.info(String.format("根据id获取系统数据字典索引信息：s%", JSONUtil.toJsonStr(logsVo)));
        return RestResult.ok(logsVo);
    }

    /**
     * 根据Logs对象属性检索所有系统数据字典索引信息
     *
     * @param logs
     * @return
     */
    @GetMapping("/listByBean")
    public RestResult listByBean(Logs logs) {
        Collection<Logs> logss = logsService.listByBean(logs);
        Collection<LogsVo> logsVos = logsService.setVoProperties(logss);
        log.info(String.format("根据Logs对象属性检索所有系统数据字典索引信息: %s ",JSONUtil.toJsonStr(logsVos)));
        return RestResult.ok(logsVos);
    }

    /**
     * 根据Logs对象属性分页检索系统数据字典索引信息
     *
     * @param logs
     * @return
     */
    @GetMapping("/pageByBean")
    public RestResult pageByBean(Logs logs) {
        IPage logss = logsService.pageByBean(logs);
        logss.setRecords(logsService.setVoProperties(logss.getRecords()));
        log.info(String.format("根据Logs对象属性分页检索系统数据字典索引信息: %s ",JSONUtil.toJsonStr(logss)));
        return RestResult.ok(logss);
    }

}
