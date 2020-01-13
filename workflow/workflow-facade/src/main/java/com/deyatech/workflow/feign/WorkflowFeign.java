package com.deyatech.workflow.feign;

import com.deyatech.common.entity.RestResult;
import com.deyatech.common.enums.ProcessInstanceStatusEnum;
import com.deyatech.workflow.entity.IProcessDefinition;
import com.deyatech.workflow.vo.ProcessInstanceVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

/**
 * <p>
 * workflow模块feign远程调用类
 * </p>
 *
 */
@FeignClient(value = "workflow-service")
public interface WorkflowFeign {

    /**
     * 启动流程实例
     *
     * @param processInstanceVo
     * @return
     */
    @RequestMapping(value = "/feign/workflow/startInstance")
    RestResult<ProcessInstanceStatusEnum> startInstance(@RequestBody ProcessInstanceVo processInstanceVo);

    @RequestMapping(value = "/feign/workflow/getActDefinitionIdAndKey")
    RestResult<IProcessDefinition> getActDefinitionIdAndKey(@RequestParam("oldActDefinitionId") String oldActDefinitionId);

    @RequestMapping(value = "/feign/workflow/deleteInstanceByBusinessId")
    RestResult<ProcessInstanceStatusEnum> deleteInstanceByBusinessId(@RequestParam("businessId") String businessId, @RequestParam("reason") String reason);
}
