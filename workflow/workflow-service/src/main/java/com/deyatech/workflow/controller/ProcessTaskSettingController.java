package com.deyatech.workflow.controller;

import com.deyatech.common.entity.RestResult;
import com.deyatech.workflow.entity.ProcessTaskSetting;
import com.deyatech.workflow.service.ProcessTaskSettingService;
import com.deyatech.workflow.vo.ProcessTaskSettingVo;
import lombok.extern.slf4j.Slf4j;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.Collection;
import java.util.List;
import org.springframework.web.bind.annotation.RestController;
import com.deyatech.common.base.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

/**
 * <p>
 * 流程设置 前端控制器
 * </p>
 * @author: lee.
 * @since 2019-07-31
 */
@Slf4j
@RestController
@RequestMapping("/workflow/processTaskSetting")
@Api(tags = {"流程设置接口"})
public class ProcessTaskSettingController extends BaseController {

    @Autowired
    ProcessTaskSettingService processTaskSettingService;

    /**
     * 单个保存或者更新流程设置
     *
     * @param processTaskSetting
     * @return
     */
    @PostMapping("/saveOrUpdate")
    @ApiOperation(value="单个保存或者更新流程设置", notes="根据流程设置对象保存或者更新流程设置信息")
    @ApiImplicitParam(name = "processTaskSetting", value = "流程设置对象", required = true, dataType = "ProcessTaskSetting", paramType = "query")
    public RestResult<Boolean> saveOrUpdate(ProcessTaskSetting processTaskSetting) {
        log.info(String.format("保存或者更新流程设置: %s ", JSONUtil.toJsonStr(processTaskSetting)));
        boolean result = processTaskSettingService.saveOrUpdate(processTaskSetting);
        return RestResult.ok(result);
    }

    /**
     * 批量保存或者更新流程设置
     *
     * @param processTaskSettingList
     * @return
     */
    @PostMapping("/saveOrUpdateBatch")
    @ApiOperation(value="批量保存或者更新流程设置", notes="根据流程设置对象集合批量保存或者更新流程设置信息")
    @ApiImplicitParam(name = "processTaskSettingList", value = "流程设置对象集合", required = true, allowMultiple = true, dataType = "ProcessTaskSetting", paramType = "query")
    public RestResult<Boolean> saveOrUpdateBatch(Collection<ProcessTaskSetting> processTaskSettingList) {
        log.info(String.format("批量保存或者更新流程设置: %s ", JSONUtil.toJsonStr(processTaskSettingList)));
        boolean result = processTaskSettingService.saveOrUpdateBatch(processTaskSettingList);
        return RestResult.ok(result);
    }

    /**
     * 根据ProcessTaskSetting对象属性逻辑删除流程设置
     *
     * @param processTaskSetting
     * @return
     */
    @PostMapping("/removeByProcessTaskSetting")
    @ApiOperation(value="根据ProcessTaskSetting对象属性逻辑删除流程设置", notes="根据流程设置对象逻辑删除流程设置信息")
    @ApiImplicitParam(name = "processTaskSetting", value = "流程设置对象", required = true, dataType = "ProcessTaskSetting", paramType = "query")
    public RestResult<Boolean> removeByProcessTaskSetting(ProcessTaskSetting processTaskSetting) {
        log.info(String.format("根据ProcessTaskSetting对象属性逻辑删除流程设置: %s ", processTaskSetting));
        boolean result = processTaskSettingService.removeByBean(processTaskSetting);
        return RestResult.ok(result);
    }


    /**
     * 根据ID批量逻辑删除流程设置
     *
     * @param ids
     * @return
     */
    @PostMapping("/removeByIds")
    @ApiOperation(value="根据ID批量逻辑删除流程设置", notes="根据流程设置对象ID批量逻辑删除流程设置信息")
    @ApiImplicitParam(name = "ids", value = "流程设置对象ID集合", required = true, allowMultiple = true, dataType = "Serializable", paramType = "query")
    public RestResult<Boolean> removeByIds(@RequestParam("ids[]") List<String> ids) {
        log.info(String.format("根据id批量删除流程设置: %s ", JSONUtil.toJsonStr(ids)));
        boolean result = processTaskSettingService.removeByIds(ids);
        return RestResult.ok(result);
    }

    /**
     * 根据ProcessTaskSetting对象属性获取流程设置
     *
     * @param processTaskSetting
     * @return
     */
    @GetMapping("/getByProcessTaskSetting")
    @ApiOperation(value="根据ProcessTaskSetting对象属性获取流程设置", notes="根据流程设置对象属性获取流程设置信息")
    @ApiImplicitParam(name = "processTaskSetting", value = "流程设置对象", required = false, dataType = "ProcessTaskSetting", paramType = "query")
    public RestResult<ProcessTaskSettingVo> getByProcessTaskSetting(ProcessTaskSetting processTaskSetting) {
        processTaskSetting = processTaskSettingService.getByBean(processTaskSetting);
        ProcessTaskSettingVo processTaskSettingVo = processTaskSettingService.setVoProperties(processTaskSetting);
        log.info(String.format("根据id获取流程设置：%s", JSONUtil.toJsonStr(processTaskSettingVo)));
        return RestResult.ok(processTaskSettingVo);
    }

    /**
     * 根据ProcessTaskSetting对象属性检索所有流程设置
     *
     * @param processTaskSetting
     * @return
     */
    @GetMapping("/listByProcessTaskSetting")
    @ApiOperation(value="根据ProcessTaskSetting对象属性检索所有流程设置", notes="根据ProcessTaskSetting对象属性检索所有流程设置信息")
    @ApiImplicitParam(name = "processTaskSetting", value = "流程设置对象", required = false, dataType = "ProcessTaskSetting", paramType = "query")
    public RestResult<Collection<ProcessTaskSettingVo>> listByProcessTaskSetting(ProcessTaskSetting processTaskSetting) {
        Collection<ProcessTaskSetting> processTaskSettings = processTaskSettingService.listByBean(processTaskSetting);
        Collection<ProcessTaskSettingVo> processTaskSettingVos = processTaskSettingService.setVoProperties(processTaskSettings);
        log.info(String.format("根据ProcessTaskSetting对象属性检索所有流程设置: %s ",JSONUtil.toJsonStr(processTaskSettingVos)));
        return RestResult.ok(processTaskSettingVos);
    }

    /**
     * 根据ProcessTaskSetting对象属性分页检索流程设置
     *
     * @param processTaskSetting
     * @return
     */
    @GetMapping("/pageByProcessTaskSetting")
    @ApiOperation(value="根据ProcessTaskSetting对象属性分页检索流程设置", notes="根据ProcessTaskSetting对象属性分页检索流程设置信息")
    @ApiImplicitParam(name = "processTaskSetting", value = "流程设置对象", required = false, dataType = "ProcessTaskSetting", paramType = "query")
    public RestResult<IPage<ProcessTaskSettingVo>> pageByProcessTaskSetting(ProcessTaskSetting processTaskSetting) {
        IPage<ProcessTaskSettingVo> processTaskSettings = processTaskSettingService.pageByBean(processTaskSetting);
        processTaskSettings.setRecords(processTaskSettingService.setVoProperties(processTaskSettings.getRecords()));
        log.info(String.format("根据ProcessTaskSetting对象属性分页检索流程设置: %s ",JSONUtil.toJsonStr(processTaskSettings)));
        return RestResult.ok(processTaskSettings);
    }

}
