package com.deyatech.workflow.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.deyatech.common.enums.ProcessInstanceStatusEnum;
import com.deyatech.workflow.vo.ProcessTaskVo;
import org.activiti.engine.task.Task;

import java.util.List;
import java.util.Map;

public interface ProcessTaskService {

    /**
     * 查询所有代办任务
     * @param processTaskVo
     * @return
     */
    List<Task> getAllTasks(ProcessTaskVo processTaskVo);

    /**
     * 分页查询代办任务
     * @param processTaskVo
     * @return
     */
    IPage<Task> getTaskList(ProcessTaskVo processTaskVo);

    Task findByActTaskIdAndUserId(String actTaskId, String userId);

    Task findByActTaskIdAndAuth(String actTaskId, String userId);

    /**
     * 领取任务（由组任务转为个人任务）
     * @param actTaskId
     * @param userId
     */
    void claim(String actTaskId, String userId);

    void setVariables(String actTaskId, Map<String, Object> variables);

    ProcessInstanceStatusEnum rollBack(String actTaskId);

    ProcessInstanceStatusEnum completeTask(String actTaskId, Map<String, Object> variables);

    ProcessInstanceStatusEnum rejectTask(String actTaskId, String reason);

    ProcessInstanceStatusEnum cancelTask(String actTaskId, String reason);

    List<ProcessTaskVo> mapToDto(List<Task> taskList);
}
