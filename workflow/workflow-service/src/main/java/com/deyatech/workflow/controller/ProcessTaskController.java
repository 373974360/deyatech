package com.deyatech.workflow.controller;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.deyatech.common.base.BaseController;
import com.deyatech.common.context.UserContextHelper;
import com.deyatech.common.entity.RestResult;
import com.deyatech.common.enums.ProcessInstanceStatusEnum;
import com.deyatech.workflow.service.ProcessTaskService;
import com.deyatech.workflow.vo.ProcessTaskVo;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/workflow/processTask")
public class ProcessTaskController extends BaseController {

    @Autowired
    private ProcessTaskService processTaskService;

    /**
     * 分页查询代办任务
     * @param processTaskVo
     * @return
     */
    @GetMapping("/getCurrentTaskList")
    public RestResult<IPage<ProcessTaskVo>> getCurrentTaskList(ProcessTaskVo processTaskVo) {
        return RestResult.ok(processTaskService.queryTaskList(processTaskVo));
    }

    @PostMapping("/setTaskVariables")
    public RestResult<Boolean> setTaskVariables(String taskId, @RequestBody Map<String, Object> variables) {
        processTaskService.setVariables(taskId, variables);
        return RestResult.ok(Boolean.TRUE);
    }

    @PostMapping("/completeTask")
    public RestResult<ProcessInstanceStatusEnum> completeTask(String taskId) {
        ProcessInstanceStatusEnum status = processTaskService.completeTask(taskId, UserContextHelper.getUserId(), CollectionUtil.newHashMap());
        return RestResult.ok(status);
    }

    @PostMapping("/rejectTask")
    public RestResult<ProcessInstanceStatusEnum> rejectTask(String taskId) {
        ProcessInstanceStatusEnum status = processTaskService.rejectTask(taskId, UserContextHelper.getUserId());
        return RestResult.ok(status);
    }

    @PostMapping("/cancelTask")
    public RestResult<ProcessInstanceStatusEnum> cancelTask(String taskId) {
        ProcessInstanceStatusEnum status = processTaskService.cancelTask(taskId, UserContextHelper.getUserId());
        return RestResult.ok(status);
    }

    @PostMapping("/rollBackTask")
    public RestResult<ProcessInstanceStatusEnum> rollBackTask(String taskId) {
        ProcessInstanceStatusEnum status = processTaskService.rollBack(taskId, UserContextHelper.getUserId());
        return RestResult.ok(status);
    }

}
