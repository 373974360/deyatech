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

    private String userId = UserContextHelper.getUserId();

    /**
     * 分页查询代办任务
     * @param processTaskVo
     * @return
     */
    @GetMapping("/getCurrentTaskList")
    public RestResult<IPage<ProcessTaskVo>> getCurrentTaskList(ProcessTaskVo processTaskVo) {
        if (StrUtil.isNotEmpty(processTaskVo.getTitleOrAuthor())) {
            Map variables = CollectionUtil.newHashMap();
            variables.put("LIKE_title", processTaskVo.getTitleOrAuthor());
            variables.put("LIKE_author", processTaskVo.getTitleOrAuthor());
            processTaskVo.setVariables(variables);
        }
        IPage<Task> taskPage = processTaskService.getTaskList(processTaskVo);
        IPage<ProcessTaskVo> result = new Page<>();
        result.setTotal(taskPage.getTotal());
        result.setRecords(processTaskService.mapToDto(taskPage.getRecords()));
        return RestResult.ok(result);
    }

    @PostMapping("/setTaskVariables")
    public RestResult<Boolean> setTaskVariables(String taskId, @RequestBody Map<String, Object> variables) {
        processTaskService.setVariables(taskId, variables);
        return RestResult.ok(Boolean.TRUE);
    }

    @PostMapping("/completeTask")
    public RestResult<ProcessInstanceStatusEnum> completeTask(String taskId) {
        Map variables = CollectionUtil.newHashMap();
        ProcessInstanceStatusEnum status = processTaskService.completeTask(taskId, userId, variables);
        return RestResult.ok(status);
    }

    @PostMapping("/rejectTask")
    public RestResult<ProcessInstanceStatusEnum> rejectTask(String taskId) {
        ProcessInstanceStatusEnum status = processTaskService.rejectTask(taskId, userId);
        return RestResult.ok(status);
    }

    @PostMapping("/cancelTask")
    public RestResult<ProcessInstanceStatusEnum> cancelTask(String taskId, String userId) {
        ProcessInstanceStatusEnum status = processTaskService.cancelTask(taskId, userId);
        return RestResult.ok(status);
    }

    @PostMapping("/rollBackTask")
    public RestResult<ProcessInstanceStatusEnum> rollBackTask(String taskId) {
        ProcessInstanceStatusEnum status = processTaskService.rollBack(taskId, userId);
        return RestResult.ok(status);
    }

}