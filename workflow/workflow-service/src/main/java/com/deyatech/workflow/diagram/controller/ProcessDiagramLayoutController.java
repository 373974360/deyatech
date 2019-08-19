package com.deyatech.workflow.diagram.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/workflow/diagram")
public class ProcessDiagramLayoutController extends BaseProcessDiagramResource {

    @GetMapping(value = "/process-definition/{processDefinitionId}/diagram-layout", produces = "application/json;charset=utf-8")
    public String getByProcessDefinition(@PathVariable String processDefinitionId) {
        return getDiagramNode(null, processDefinitionId).toString();
    }

    @GetMapping(value = "/process-instance/{processInstanceId}/diagram-layout", produces = "application/json;charset=utf-8")
    public String getByProcessInstance(@PathVariable String processInstanceId) {
        return getDiagramNode(processInstanceId, null).toString();
    }
}
