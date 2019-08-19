package com.deyatech.workflow.controller;

import com.deyatech.workflow.entity.ProcessModel;
import com.deyatech.workflow.service.ProcessModelService;
import com.deyatech.workflow.vo.ProcessModelVo;
import com.deyatech.common.entity.RestResult;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.extern.slf4j.Slf4j;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.MultiValueMap;
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
 * 流程模型 前端控制器
 * </p>
 * @author: lee.
 * @since 2019-07-31
 */
@Slf4j
@RestController
@RequestMapping("/workflow/processModel")
@Api(tags = {"流程模型接口"})
public class ProcessModelController extends BaseController {

    @Autowired
    ProcessModelService processModelService;

    /**
     * 新建流程模型
     *
     * @param processModelVo
     * @return
     */
    @PostMapping("/saveOrUpdate")
    @ApiOperation(value="新建流程模型", notes="新建流程模型")
    @ApiImplicitParam(name = "processModelVo", value = "流程模型对象", required = true, dataType = "ProcessModelVo", paramType = "query")
    public RestResult<ProcessModelVo> saveOrUpdate(ProcessModelVo processModelVo) {
        log.info(String.format("新建流程模型: %s ", JSONUtil.toJsonStr(processModelVo)));
        ProcessModel processModel = processModelService.add(processModelVo);
        return RestResult.ok(processModelService.setVoProperties(processModel));
    }

    /**
     * 批量保存或者更新流程模型
     *
     * @param processModelList
     * @return
     */
    @PostMapping("/saveOrUpdateBatch")
    @ApiOperation(value="批量保存或者更新流程模型", notes="根据流程模型对象集合批量保存或者更新流程模型信息")
    @ApiImplicitParam(name = "processModelList", value = "流程模型对象集合", required = true, allowMultiple = true, dataType = "ProcessModel", paramType = "query")
    public RestResult<Boolean> saveOrUpdateBatch(Collection<ProcessModel> processModelList) {
        log.info(String.format("批量保存或者更新流程模型: %s ", JSONUtil.toJsonStr(processModelList)));
        boolean result = processModelService.saveOrUpdateBatch(processModelList);
        return RestResult.ok(result);
    }

    /**
     * 根据ProcessModel对象属性逻辑删除流程模型
     *
     * @param processModel
     * @return
     */
    @PostMapping("/removeByProcessModel")
    @ApiOperation(value="根据ProcessModel对象属性逻辑删除流程模型", notes="根据流程模型对象逻辑删除流程模型信息")
    @ApiImplicitParam(name = "processModel", value = "流程模型对象", required = true, dataType = "ProcessModel", paramType = "query")
    public RestResult<Boolean> removeByProcessModel(ProcessModel processModel) {
        log.info(String.format("根据ProcessModel对象属性逻辑删除流程模型: %s ", processModel));
        boolean result = processModelService.removeByBean(processModel);
        return RestResult.ok(result);
    }


    /**
     * 根据ID批量逻辑删除流程模型
     *
     * @param ids
     * @return
     */
    @PostMapping("/removeByIds")
    @ApiOperation(value="根据ID批量逻辑删除流程模型", notes="根据流程模型对象ID批量逻辑删除流程模型信息")
    @ApiImplicitParam(name = "ids", value = "流程模型对象ID集合", required = true, allowMultiple = true, dataType = "Serializable", paramType = "query")
    public RestResult<Boolean> removeByIds(@RequestParam("ids[]") List<String> ids) {
        log.info(String.format("根据id批量删除流程模型: %s ", JSONUtil.toJsonStr(ids)));
        boolean result = processModelService.removeByIds(ids);
        return RestResult.ok(result);
    }

    /**
     * 根据ProcessModel对象属性获取流程模型
     *
     * @param processModel
     * @return
     */
    @GetMapping("/getByProcessModel")
    @ApiOperation(value="根据ProcessModel对象属性获取流程模型", notes="根据流程模型对象属性获取流程模型信息")
    @ApiImplicitParam(name = "processModel", value = "流程模型对象", required = false, dataType = "ProcessModel", paramType = "query")
    public RestResult<ProcessModelVo> getByProcessModel(ProcessModel processModel) {
        processModel = processModelService.getByBean(processModel);
        ProcessModelVo processModelVo = processModelService.setVoProperties(processModel);
        log.info(String.format("根据id获取流程模型：%s", JSONUtil.toJsonStr(processModelVo)));
        return RestResult.ok(processModelVo);
    }

    /**
     * 根据ProcessModel对象属性检索所有流程模型
     *
     * @param processModel
     * @return
     */
    @GetMapping("/listByProcessModel")
    @ApiOperation(value="根据ProcessModel对象属性检索所有流程模型", notes="根据ProcessModel对象属性检索所有流程模型信息")
    @ApiImplicitParam(name = "processModel", value = "流程模型对象", required = false, dataType = "ProcessModel", paramType = "query")
    public RestResult<Collection<ProcessModelVo>> listByProcessModel(ProcessModel processModel) {
        Collection<ProcessModel> processModels = processModelService.listByBean(processModel);
        Collection<ProcessModelVo> processModelVos = processModelService.setVoProperties(processModels);
        log.info(String.format("根据ProcessModel对象属性检索所有流程模型: %s ",JSONUtil.toJsonStr(processModelVos)));
        return RestResult.ok(processModelVos);
    }

    /**
     * 根据ProcessModel对象属性分页检索流程模型
     *
     * @param processModel
     * @return
     */
    @GetMapping("/pageByProcessModel")
    @ApiOperation(value="根据ProcessModel对象属性分页检索流程模型", notes="根据ProcessModel对象属性分页检索流程模型信息")
    @ApiImplicitParam(name = "processModel", value = "流程模型对象", required = false, dataType = "ProcessModel", paramType = "query")
    public RestResult<IPage<ProcessModelVo>> pageByProcessModel(ProcessModel processModel) {
//        IPage<ProcessModelVo> processModels = processModelService.pageByBean(processModel);
//        processModels.setRecords(processModelService.setVoProperties(processModels.getRecords()));
        IPage<ProcessModelVo> processModels = processModelService.findLastVersionPage(processModel);
        log.info(String.format("根据ProcessModel对象属性分页检索流程模型: %s ",JSONUtil.toJsonStr(processModels)));
        return RestResult.ok(processModels);
    }

    /**
     * 流程发布
     *
     * @param actModelIds
     * @return
     */
    @PostMapping("/deploy")
    @ApiOperation(value="将流程模型发布成流程定义", notes="将流程模型发布成流程定义")
    @ApiImplicitParam(name = "actModelIds", value = "流程模型id", required = true, allowMultiple = true, dataType = "String", paramType = "query")
    public RestResult<List<ProcessModelVo>> deploy(@RequestParam("actModelIds[]") List<String> actModelIds) {
        List<ProcessModel> list = processModelService.deploy(actModelIds);
        return RestResult.ok(processModelService.setVoProperties(list));
    }
}
