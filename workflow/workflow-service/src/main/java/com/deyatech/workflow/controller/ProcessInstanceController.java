package com.deyatech.workflow.controller;

import com.deyatech.common.base.BaseController;
import com.deyatech.common.entity.RestResult;
import com.deyatech.common.enums.ProcessInstanceStatusEnum;
import com.deyatech.workflow.service.ProcessInstanceService;
import com.deyatech.workflow.vo.ProcessInstanceVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author doukang
 * @description TODO
 * @date 2019/8/6 16:03
 */
@RestController
@RequestMapping("/processInstance")
public class ProcessInstanceController extends BaseController {

    @Autowired
    private ProcessInstanceService processInstanceService;

    @PostMapping("/start")
    public RestResult<ProcessInstanceStatusEnum> startInstance(ProcessInstanceVo processInstanceVo) {
        ProcessInstanceStatusEnum status = processInstanceService.startInstance(processInstanceVo);
        return RestResult.ok(status);
    }
}
