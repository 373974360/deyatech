package com.deyatech.workflow.controller;

import com.deyatech.common.entity.RestResult;
import com.deyatech.workflow.service.ProcessModelService;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.activiti.engine.ActivitiException;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.io.InputStream;

/**
 * @author doukang
 * @description Activiti流程图编辑器接口
 * @date 2019/8/5 17:40
 */
@RestController
@RequestMapping("/workflow/activitiEditor")
public class ActivitiEditorController {

    @Autowired
    private ProcessModelService processModelService;

    @GetMapping(value = "/editor/stencilset", produces = "application/json;charset=utf-8")
    public String getStencilSet() {
        InputStream stencilsetStream = this.getClass().getClassLoader().getResourceAsStream("stencilset.json");
        try {
            return IOUtils.toString(stencilsetStream, "utf-8");
        } catch (Exception e) {
            throw new ActivitiException("Error while loading stencil set", e);
        }
    }

    /**
     * 获取流程图json
     *
     * @param modelId
     * @return
     */
    @GetMapping("/model/{modelId}/json")
    public RestResult<String> getEditorJson(@PathVariable String modelId) {
        ObjectNode node = processModelService.getEditorJson(modelId);
        return RestResult.ok(node.toString());
    }

    /**
     * 保存
     *
     * @param modelId
     * @param name
     * @param description
     * @param json_xml
     * @param svg_xml
     * @return
     */
    @RequestMapping(value = "/model/{modelId}/save", method = RequestMethod.PUT)
    public RestResult save(@PathVariable String modelId, @RequestParam("name") String name,
                           @RequestParam("description") String description, @RequestParam("json_xml") String json_xml,
                           @RequestParam("svg_xml") String svg_xml) {
        MultiValueMap<String, String> values = new LinkedMultiValueMap<>();
        values.add("name", name);
        values.add("description", description);
        values.add("json_xml", json_xml);
        values.add("svg_xml", svg_xml);
        processModelService.saveModel(modelId, values);
        return RestResult.ok();
    }
}
