package org.land.admin.controller;

import org.land.admin.entity.Logs;
import org.land.admin.vo.LogsVo;
import org.land.admin.service.LogsService;
import org.land.common.entity.RestResult;
import cn.hutool.core.lang.Assert;
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
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

/**
 * <p>
 * 系统日志信息 前端控制器
 * </p>
 * @author: lee.
 * @since 2019-02-27
 */
@Slf4j
@RestController
@RequestMapping("/admin/logs")
@Api(tags = {"系统日志信息接口"})
public class LogsController extends BaseController {
    @Autowired
    LogsService logsService;

    /**
     * 单个保存或者更新系统日志信息
     *
     * @param logs
     * @return
     */
    @PostMapping("/saveOrUpdate")
    @ApiOperation(value="单个保存或者更新系统日志信息", notes="根据系统日志信息对象保存或者更新系统日志信息信息")
    @ApiImplicitParam(name = "logs", value = "系统日志信息对象", required = true, dataType = "Logs", paramType = "query")
    public RestResult<Boolean> saveOrUpdate(Logs logs) {
        Assert.notNull(logs);
        log.info(String.format("保存或者更新系统日志信息: %s ", JSONUtil.toJsonStr(logs)));
        boolean result = logsService.saveOrUpdate(logs);
        return RestResult.ok(result);
    }

    /**
     * 批量保存或者更新系统日志信息
     *
     * @param logsList
     * @return
     */
    @PostMapping("/saveOrUpdateBatch")
    @ApiOperation(value="批量保存或者更新系统日志信息", notes="根据系统日志信息对象集合批量保存或者更新系统日志信息信息")
    @ApiImplicitParam(name = "logsList", value = "系统日志信息对象集合", required = true, allowMultiple = true, dataType = "Logs", paramType = "query")
    public RestResult<Boolean> saveOrUpdateBatch(Collection<Logs> logsList) {
        Assert.notNull(logsList);
        log.info(String.format("批量保存或者更新系统日志信息: %s ", JSONUtil.toJsonStr(logsList)));
        boolean result = logsService.saveOrUpdateBatch(logsList);
        return RestResult.ok(result);
    }

    /**
     * 根据Logs对象属性逻辑删除系统日志信息
     *
     * @param logs
     * @return
     */
    @PostMapping("/removeByLogs")
    @ApiOperation(value="根据Logs对象属性逻辑删除系统日志信息", notes="根据系统日志信息对象逻辑删除系统日志信息信息")
    @ApiImplicitParam(name = "logs", value = "系统日志信息对象", required = true, dataType = "Logs", paramType = "query")
    public RestResult<Boolean> removeByLogs(Logs logs) {
        Assert.notNull(logs);
        log.info(String.format("根据Logs对象属性逻辑删除系统日志信息: %s ", logs));
        boolean result = logsService.removeByBean(logs);
        return RestResult.ok(result);
    }


    /**
     * 根据ID批量逻辑删除系统日志信息
     *
     * @param ids
     * @return
     */
    @PostMapping("/removeByIds")
    @ApiOperation(value="根据ID批量逻辑删除系统日志信息", notes="根据系统日志信息对象ID批量逻辑删除系统日志信息信息")
    @ApiImplicitParam(name = "ids", value = "系统日志信息对象ID集合", required = true, allowMultiple = true, dataType = "Serializable", paramType = "query")
    public RestResult<Boolean> removeByIds(Collection<Serializable> ids) {
        Assert.notNull(ids);
        log.info(String.format("根据id批量删除系统日志信息: %s ", JSONUtil.toJsonStr(ids)));
        boolean result = logsService.removeByIds(ids);
        return RestResult.ok(result);
    }

    /**
     * 根据Logs对象属性获取系统日志信息
     *
     * @param logs
     * @return
     */
    @GetMapping("/getByLogs")
    @ApiOperation(value="根据Logs对象属性获取系统日志信息", notes="根据系统日志信息对象属性获取系统日志信息信息")
    @ApiImplicitParam(name = "logs", value = "系统日志信息对象", required = false, dataType = "Logs", paramType = "query")
    public RestResult<LogsVo> getByLogs(Logs logs) {
        logs = logsService.getByBean(logs);
        LogsVo logsVo = logsService.setVoProperties(logs);
        log.info(String.format("根据id获取系统日志信息：s%", JSONUtil.toJsonStr(logsVo)));
        return RestResult.ok(logsVo);
    }

    /**
     * 根据Logs对象属性检索所有系统日志信息
     *
     * @param logs
     * @return
     */
    @GetMapping("/listByBean")
    @ApiOperation(value="根据Logs对象属性检索所有系统日志信息", notes="根据Logs对象属性检索所有系统日志信息信息")
    @ApiImplicitParam(name = "logs", value = "系统日志信息对象", required = false, dataType = "Logs", paramType = "query")
    public RestResult<Collection<LogsVo>> listByBean(Logs logs) {
        Collection<Logs> logss = logsService.listByBean(logs);
        Collection<LogsVo> logsVos = logsService.setVoProperties(logss);
        log.info(String.format("根据Logs对象属性检索所有系统日志信息: %s ",JSONUtil.toJsonStr(logsVos)));
        return RestResult.ok(logsVos);
    }

    /**
     * 根据Logs对象属性分页检索系统日志信息
     *
     * @param logs
     * @return
     */
    @GetMapping("/pageByBean")
    @ApiOperation(value="根据Logs对象属性分页检索系统日志信息", notes="根据Logs对象属性分页检索系统日志信息信息")
    @ApiImplicitParam(name = "logs", value = "系统日志信息对象", required = false, dataType = "Logs", paramType = "query")
    public RestResult<IPage<LogsVo>> pageByBean(Logs logs) {
        IPage<LogsVo> logss = logsService.pageByBean(logs);
        logss.setRecords(logsService.setVoProperties(logss.getRecords()));
        log.info(String.format("根据Logs对象属性分页检索系统日志信息: %s ",JSONUtil.toJsonStr(logss)));
        return RestResult.ok(logss);
    }

}
