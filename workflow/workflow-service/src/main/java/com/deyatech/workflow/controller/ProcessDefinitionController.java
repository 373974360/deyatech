package com.deyatech.workflow.controller;

import com.deyatech.common.entity.RestResult;
import com.deyatech.workflow.entity.IProcessDefinition;
import com.deyatech.workflow.service.ProcessDefinitionService;
import com.deyatech.workflow.vo.ProcessDefinitionVo;
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
 * 流程定义 前端控制器
 * </p>
 * @author: lee.
 * @since 2019-07-31
 */
@Slf4j
@RestController
@RequestMapping("/workflow/processDefinition")
@Api(tags = {"流程定义接口"})
public class ProcessDefinitionController extends BaseController {

    @Autowired
    ProcessDefinitionService processDefinitionService;

    /**
     * 单个保存或者更新流程定义
     *
     * @param processDefinition
     * @return
     */
    @PostMapping("/saveOrUpdate")
    @ApiOperation(value="单个保存或者更新流程定义", notes="根据流程定义对象保存或者更新流程定义信息")
    @ApiImplicitParam(name = "processDefinition", value = "流程定义对象", required = true, dataType = "ProcessDefinition", paramType = "query")
    public RestResult<Boolean> saveOrUpdate(IProcessDefinition processDefinition) {
        log.info(String.format("保存或者更新流程定义: %s ", JSONUtil.toJsonStr(processDefinition)));
        boolean result = processDefinitionService.saveOrUpdate(processDefinition);
        return RestResult.ok(result);
    }

    /**
     * 批量保存或者更新流程定义
     *
     * @param processDefinitionList
     * @return
     */
    @PostMapping("/saveOrUpdateBatch")
    @ApiOperation(value="批量保存或者更新流程定义", notes="根据流程定义对象集合批量保存或者更新流程定义信息")
    @ApiImplicitParam(name = "processDefinitionList", value = "流程定义对象集合", required = true, allowMultiple = true, dataType = "ProcessDefinition", paramType = "query")
    public RestResult<Boolean> saveOrUpdateBatch(Collection<IProcessDefinition> processDefinitionList) {
        log.info(String.format("批量保存或者更新流程定义: %s ", JSONUtil.toJsonStr(processDefinitionList)));
        boolean result = processDefinitionService.saveOrUpdateBatch(processDefinitionList);
        return RestResult.ok(result);
    }

    /**
     * 根据ProcessDefinition对象属性逻辑删除流程定义
     *
     * @param processDefinition
     * @return
     */
    @PostMapping("/removeByProcessDefinition")
    @ApiOperation(value="根据ProcessDefinition对象属性逻辑删除流程定义", notes="根据流程定义对象逻辑删除流程定义信息")
    @ApiImplicitParam(name = "processDefinition", value = "流程定义对象", required = true, dataType = "ProcessDefinition", paramType = "query")
    public RestResult<Boolean> removeByProcessDefinition(IProcessDefinition processDefinition) {
        log.info(String.format("根据ProcessDefinition对象属性逻辑删除流程定义: %s ", processDefinition));
        boolean result = processDefinitionService.removeByBean(processDefinition);
        return RestResult.ok(result);
    }


    /**
     * 根据ID批量逻辑删除流程定义
     *
     * @param ids
     * @return
     */
    @PostMapping("/removeByIds")
    @ApiOperation(value="根据ID批量逻辑删除流程定义", notes="根据流程定义对象ID批量逻辑删除流程定义信息")
    @ApiImplicitParam(name = "ids", value = "流程定义对象ID集合", required = true, allowMultiple = true, dataType = "Serializable", paramType = "query")
    public RestResult<Boolean> removeByIds(@RequestParam("ids[]") List<String> ids) {
        log.info(String.format("根据id批量删除流程定义: %s ", JSONUtil.toJsonStr(ids)));
        boolean result = processDefinitionService.removeByIds(ids);
        return RestResult.ok(result);
    }

    /**
     * 根据ProcessDefinition对象属性获取流程定义
     *
     * @param processDefinition
     * @return
     */
    @GetMapping("/getByProcessDefinition")
    @ApiOperation(value="根据ProcessDefinition对象属性获取流程定义", notes="根据流程定义对象属性获取流程定义信息")
    @ApiImplicitParam(name = "processDefinition", value = "流程定义对象", required = false, dataType = "ProcessDefinition", paramType = "query")
    public RestResult<ProcessDefinitionVo> getByProcessDefinition(IProcessDefinition processDefinition) {
        processDefinition = processDefinitionService.getByBean(processDefinition);
        ProcessDefinitionVo processDefinitionVo = processDefinitionService.setVoProperties(processDefinition);
        log.info(String.format("根据id获取流程定义：%s", JSONUtil.toJsonStr(processDefinitionVo)));
        return RestResult.ok(processDefinitionVo);
    }

    /**
     * 根据ProcessDefinition对象属性检索所有流程定义
     *
     * @param processDefinition
     * @return
     */
    @GetMapping("/listByProcessDefinition")
    @ApiOperation(value="根据ProcessDefinition对象属性检索所有流程定义", notes="根据ProcessDefinition对象属性检索所有流程定义信息")
    @ApiImplicitParam(name = "processDefinition", value = "流程定义对象", required = false, dataType = "ProcessDefinition", paramType = "query")
    public RestResult<Collection<ProcessDefinitionVo>> listByProcessDefinition(IProcessDefinition processDefinition) {
        Collection<IProcessDefinition> processDefinitions = processDefinitionService.listByBean(processDefinition);
        Collection<ProcessDefinitionVo> processDefinitionVos = processDefinitionService.setVoProperties(processDefinitions);
        log.info(String.format("根据ProcessDefinition对象属性检索所有流程定义: %s ",JSONUtil.toJsonStr(processDefinitionVos)));
        return RestResult.ok(processDefinitionVos);
    }

    /**
     * 根据ProcessDefinition对象属性分页检索流程定义
     *
     * @param processDefinition
     * @return
     */
    @GetMapping("/pageByProcessDefinition")
    @ApiOperation(value="根据ProcessDefinition对象属性分页检索流程定义", notes="根据ProcessDefinition对象属性分页检索流程定义信息")
    @ApiImplicitParam(name = "processDefinition", value = "流程定义对象", required = false, dataType = "ProcessDefinition", paramType = "query")
    public RestResult<IPage<ProcessDefinitionVo>> pageByProcessDefinition(IProcessDefinition processDefinition) {
//        IPage<ProcessDefinitionVo> processDefinitions = processDefinitionService.pageByBean(processDefinition);
//        processDefinitions.setRecords(processDefinitionService.setVoProperties(processDefinitions.getRecords()));
        IPage<ProcessDefinitionVo> processDefinitions = processDefinitionService.findLastVersionPage(processDefinition);
        log.info(String.format("根据ProcessDefinition对象属性分页检索流程定义: %s ",JSONUtil.toJsonStr(processDefinitions)));
        return RestResult.ok(processDefinitions);
    }

    @PostMapping("/activate")
    @ApiOperation(value="流程激活", notes="流程激活")
    @ApiImplicitParam(name = "actDefinitionIds", value = "流程定义id", required = true, allowMultiple = true, dataType = "String", paramType = "query")
    public RestResult activate(List<String> actDefinitionIds) {
        processDefinitionService.active(actDefinitionIds);
        return RestResult.ok();
    }

    @PostMapping("/suspend")
    @ApiOperation(value="流程挂起", notes="流程挂起")
    @ApiImplicitParam(name = "actDefinitionIds", value = "流程定义id", required = true, allowMultiple = true, dataType = "String", paramType = "query")
    public RestResult suspend(List<String> actDefinitionIds) {
        processDefinitionService.suspend(actDefinitionIds);
        return RestResult.ok();
    }
}
