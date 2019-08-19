package com.deyatech.workflow.listener;

import cn.hutool.core.collection.CollectionUtil;
import com.deyatech.admin.feign.AdminFeign;
import com.deyatech.admin.vo.UserVo;
import com.deyatech.common.Constants;
import com.deyatech.common.entity.RestResult;
import com.deyatech.common.enums.CandidateTypeEnum;
import com.deyatech.common.enums.EnableEnum;
import com.deyatech.workflow.entity.ProcessTaskSetting;
import com.deyatech.workflow.service.ProcessTaskSettingService;
import com.deyatech.workflow.util.WorkFlowUtils;
import org.activiti.engine.TaskService;
import org.activiti.engine.delegate.event.ActivitiEntityEvent;
import org.activiti.engine.delegate.event.ActivitiEvent;
import org.activiti.engine.delegate.event.ActivitiEventListener;
import org.activiti.engine.impl.persistence.entity.TaskEntity;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class TaskOnCreateListener implements ActivitiEventListener {

    @Lazy
    @Autowired
    private ProcessTaskSettingService processTaskSettingService;

    @Lazy
    @Autowired
    private AdminFeign adminFeign;

    @Override
    public void onEvent(ActivitiEvent activitiEvent) {
        //判断创建的是用户任务
        if (activitiEvent instanceof ActivitiEntityEvent
                && ((ActivitiEntityEvent) activitiEvent).getEntity() instanceof TaskEntity) {
            TaskEntity task = (TaskEntity) ((ActivitiEntityEvent) activitiEvent).getEntity();
            TaskService taskService = activitiEvent.getEngineServices().getTaskService();

            Map<String, Object> variables = taskService.getVariables(task.getId());

            String source = variables.get(Constants.VARIABLE_SOURCE).toString();

            ProcessTaskSetting taskSetting = processTaskSettingService.get(task.getProcessDefinitionId(),
                    task.getTaskDefinitionKey(), source);

            if (CandidateTypeEnum.USER.getCode().equals(taskSetting.getCandidateType())
                    && StringUtils.isNotBlank(taskSetting.getCandidateUsers())) {
                List<String> candidateUsers = Arrays.asList(taskSetting.getCandidateUsers().split(","));
                for (String candidateUser : candidateUsers) {
                    taskService.addCandidateUser(task.getId(), candidateUser);
                }
            } else if (CandidateTypeEnum.GROUP.getCode().equals(taskSetting.getCandidateType())
                    && StringUtils.isNotBlank(taskSetting.getCandidateGroups())) {
                List<String> candidateGroups = Arrays.asList(taskSetting.getCandidateGroups().split(","));
                for (String candidateGroup : candidateGroups) {
                    taskService.addCandidateGroup(task.getId(), candidateGroup);
                }
            }

            if (taskSetting.getAutoPass()) {
                if (CandidateTypeEnum.USER.getCode().equals(taskSetting.getCandidateType())
                        && StringUtils.isNotBlank(taskSetting.getCandidateUsers())) {
                    List<String> candidateUsers = Arrays.asList(taskSetting.getCandidateUsers().split(","));
                    for (String candidateUser : candidateUsers) {
                        RestResult<UserVo> user = adminFeign.getUserByUserId(candidateUser);
                        if (user.isOk() && user.getData() != null && EnableEnum.ENABLE.getCode().equals(user.getData().getEnable())) {
                            return;
                        }
                    }
                } else if (CandidateTypeEnum.GROUP.getCode().equals(taskSetting.getCandidateType())
                        && StringUtils.isNotBlank(taskSetting.getCandidateGroups())) {
                    String definitionKey = task.getProcessInstance().getProcessDefinitionKey();
                    String uniqueKey = source + ":" + definitionKey + ":" + task.getTaskDefinitionKey();

                    List<String> candidateGroups = Arrays.asList(taskSetting.getCandidateGroups().split(","));
                    for (String candidateGroup : candidateGroups) {
                        List<String> userIds = WorkFlowUtils.getValidUser(uniqueKey, candidateGroup, variables);
                        if (CollectionUtil.isNotEmpty(userIds)) {
                            return;
                        }
                    }
                }
                new Thread() {
                    public void run() {
                        try {
                            Thread.sleep(10000);
                            taskService.complete(task.getId());
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }.start();
            }
        }
    }

    @Override
    public boolean isFailOnException() {
        return false;
    }

}
