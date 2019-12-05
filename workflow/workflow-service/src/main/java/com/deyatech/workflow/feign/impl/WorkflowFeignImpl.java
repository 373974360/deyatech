package com.deyatech.workflow.feign.impl;

import com.deyatech.common.entity.RestResult;
import com.deyatech.common.enums.ProcessInstanceStatusEnum;
import com.deyatech.workflow.entity.IProcessDefinition;
import com.deyatech.workflow.feign.WorkflowFeign;
import com.deyatech.workflow.service.ProcessDefinitionService;
import com.deyatech.workflow.service.ProcessInstanceService;
import com.deyatech.workflow.vo.ProcessInstanceVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * <p>
 * WorkflowFeign实现类
 * </p>
 *
 */
@RestController
public class WorkflowFeignImpl implements WorkflowFeign {

    @Autowired
    private ProcessInstanceService processInstanceService;
    @Autowired
    private ProcessDefinitionService processDefinitionService;

    @Override
    public RestResult<ProcessInstanceStatusEnum> startInstance(ProcessInstanceVo processInstanceVo) {
        ProcessInstanceStatusEnum status = processInstanceService.startInstance(processInstanceVo);
        return RestResult.ok(status);
    }

    @Override
    public RestResult<IProcessDefinition> getActDefinitionIdAndKey(String oldActDefinitionId) {
        return RestResult.ok(processDefinitionService.getActDefinitionIdAndKey(oldActDefinitionId));
    }

}
