package com.deyatech.workflow.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpStatus;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.deyatech.admin.feign.AdminFeign;
import com.deyatech.admin.vo.UserVo;
import com.deyatech.common.context.UserContextHelper;
import com.deyatech.common.entity.RestResult;
import com.deyatech.common.enums.CandidateTypeEnum;
import com.deyatech.common.enums.ProcessInstanceStatusEnum;
import com.deyatech.common.exception.BusinessException;
import com.deyatech.workflow.constant.ProcessConstant;
import com.deyatech.workflow.service.ProcessTaskService;
import com.deyatech.workflow.util.WorkFlowUtils;
import com.deyatech.workflow.vo.ProcessTaskVo;
import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.HistoryService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.pvm.PvmTransition;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.impl.pvm.process.TransitionImpl;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.Execution;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.IdentityLink;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.apache.commons.lang3.StringUtils;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@Service
public class ProcessTaskServiceImpl implements ProcessTaskService {

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private TaskService taskService;

    @Autowired
    private RepositoryService repositoryService;

    @Autowired
    private HistoryService historyService;

    @Autowired
    private AdminFeign adminFeign;

    @Autowired
    RabbitTemplate rabbitTemplate;

    /**
     * 查询所有代办任务
     * @param processTaskDto
     * @return
     */
    @Override
    public List<Task> getAllTasks(ProcessTaskVo processTaskDto) {
        TaskQuery query = createQuery(processTaskDto);
        List<Task> list = query.list();
        if (StringUtils.isNotBlank(processTaskDto.getCandidateUser())) {
            list = filterCandidateUser(processTaskDto.getCandidateUser(), list);
        }
        return list;
    }

    /**
     * 分页查询代办任务
     * @param processTaskDto
     * @return
     */
    @Override
    public IPage<Task> getTaskList(ProcessTaskVo processTaskDto) {
        TaskQuery query = createQuery(processTaskDto);

        List<Task> list = query.list();
        if (StringUtils.isNotBlank(processTaskDto.getCandidateUser())) {
            list = filterCandidateUser(processTaskDto.getCandidateUser(), list);
        }
        long count = list.size();
        int page = processTaskDto.getPage().intValue();
        int size = processTaskDto.getSize().intValue();
        int offset = (page - 1) * size;

        if (offset + 1 > list.size()) {
            list = new ArrayList<>();
        } else if (offset + processTaskDto.getSize() + 1 > list.size()) {
            list = list.subList(offset, list.size());
        } else {
            list = list.subList(offset, offset + size);
        }

        IPage<Task> result = new Page<>();
        result.setTotal(count);
        result.setRecords(list);
        return result;
    }

    @Override
    public Task findByActTaskIdAndUserId(String actTaskId, String userId) {
        Task task = null;

        ProcessTaskVo processTaskDto = new ProcessTaskVo();
        processTaskDto.setActTaskId(actTaskId);
        // todo 判断用户是否是超级管理员
//        if (!SimpleConstants.USER_ADMIN_ID.equals(userId)) {
            processTaskDto.setCandidateUser(userId);
//        }

        List<Task> list = getAllTasks(processTaskDto);

        if (CollectionUtil.isNotEmpty(list)) {
            task = list.get(0);
        }

        return task;
    }

    @Override
    public Task findByActTaskIdAndAuth(String actTaskId, String userId) {
        Task task = null;

        ProcessTaskVo processTaskDto = new ProcessTaskVo();
        processTaskDto.setActTaskId(actTaskId);
        // todo 判断用户是否是超级管理员
//        if (!SimpleConstants.USER_ADMIN_ID.equals(userId)) {
            Map<String, Object> variables = new HashMap<>();
            variables.put("EQ_userId", userId);
//        }

        List<Task> list = getAllTasks(processTaskDto);

        if (CollectionUtil.isNotEmpty(list)) {
            task = list.get(0);
        }

        return task;
    }

    /**
     * 领取任务（由组任务转为个人任务）
     * @param actTaskId
     * @param userId
     */
    @Override
    public void claim(String actTaskId, String userId) {
        Task task = findByActTaskIdAndUserId(actTaskId, userId);

        if (task == null) {
            throw new BusinessException(HttpStatus.HTTP_INTERNAL_ERROR, "任务不存在或没有权限处理");
        }

        taskService.claim(actTaskId, userId);
    }

    @Override
    public void setVariables(String actTaskId, Map<String, Object> variables) {
        if (MapUtil.isNotEmpty(variables)) {
            taskService.setVariables(actTaskId, variables);
        }
    }

    @Override
    public ProcessInstanceStatusEnum rollBack(String actTaskId) {
        String userId = UserContextHelper.getUserId();
        try {
            TaskQuery taskQuery = taskService.createTaskQuery().taskId(actTaskId).includeProcessVariables().includeTaskLocalVariables();
            Task currTask = taskQuery.singleResult();
            if (Objects.isNull(currTask)) {
                throw new BusinessException(HttpStatus.HTTP_INTERNAL_ERROR, "任务不存");
            }
            checkRight(currTask, null);

            // 并行任务不能驳回
            List<Execution> executionList = runtimeService.createExecutionQuery()
                    .processInstanceId(currTask.getProcessInstanceId()).list();
            if (CollectionUtil.isNotEmpty(executionList) && executionList.size() > 1) {
                throw new BusinessException(HttpStatus.HTTP_INTERNAL_ERROR, "并行任务不能驳回");
            }
            // 取得流程实例
            ProcessInstance instance = runtimeService.createProcessInstanceQuery()
                    .processInstanceId(currTask.getProcessInstanceId())
                    .singleResult();
            if (instance == null) {
                throw new BusinessException(HttpStatus.HTTP_INTERNAL_ERROR, "流程实例不存在或者流程已结束");
            }
            // 取得流程定义
            ProcessDefinitionEntity definition = (ProcessDefinitionEntity) (
                    repositoryService.getProcessDefinition(currTask.getProcessDefinitionId()));
            if (definition == null) {
                throw new BusinessException(HttpStatus.HTTP_INTERNAL_ERROR, "未找到流程定义");
            }
            // 当前活动节点
            ActivityImpl currActivity = definition.findActivity(currTask.getTaskDefinitionKey());
            // 清除当前活动的出口
            List<PvmTransition> oriPvmTransitionList = new ArrayList<>();
            List<PvmTransition> pvmTransitionList = currActivity.getOutgoingTransitions();
            if (CollectionUtil.isNotEmpty(pvmTransitionList)) {
                oriPvmTransitionList.addAll(currActivity.getOutgoingTransitions());
            }
            pvmTransitionList.clear();
            // 取得上一步并可驳回的活动节点
            List<ActivityImpl> prevActivities = findRebuttable(currActivity);
            if (CollectionUtil.isEmpty(prevActivities)) {
                throw new BusinessException(HttpStatus.HTTP_INTERNAL_ERROR, "未找到可驳回的节点");
            }
            // 建立新出口
            for (ActivityImpl activity : prevActivities) {
                TransitionImpl newTransition = currActivity.createOutgoingTransition();
                newTransition.setDestination(activity);
            }
            // 完成任务
            List<Task> tasks = taskService.createTaskQuery()
                    .processInstanceId(instance.getId())
                    .taskDefinitionKey(currTask.getTaskDefinitionKey()).list();
            for (Task task : tasks) {
                taskService.claim(task.getId(), userId);
                taskService.complete(task.getId());
            }
            // 清除临时出口，恢复原来的出口
            currActivity.getOutgoingTransitions().clear();
            currActivity.getOutgoingTransitions().addAll(oriPvmTransitionList);
            return ProcessInstanceStatusEnum.IN_PROGRESS;
        } catch (Exception e) {
            throw new BusinessException(HttpStatus.HTTP_INTERNAL_ERROR, "执行失败");
        }
    }
    /**
     * 通过
     *
     * @param actTaskId
     * @param variables
     * @return
     */
    @Override
    public ProcessInstanceStatusEnum completeTask(String actTaskId, Map<String, Object> variables) {
        TaskQuery taskQuery = taskService.createTaskQuery().taskId(actTaskId).includeProcessVariables().includeTaskLocalVariables();
        Task task = taskQuery.singleResult();
        if (Objects.isNull(task)) throw new BusinessException(HttpStatus.HTTP_INTERNAL_ERROR, "任务不存");
        Map<String, Object> param = checkRight(task, null);
        taskService.setVariables(actTaskId, variables);
        taskService.claim(actTaskId, UserContextHelper.getUserId());
        taskService.complete(actTaskId);
        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(task.getProcessInstanceId()).singleResult();
        if (processInstance == null) {
            rabbitTemplate.convertAndSend(ProcessConstant.EXCHANGE_PROCESS, ProcessConstant.ROUTING_KEY_PROCESS_FINISH, param);
            return ProcessInstanceStatusEnum.FINISH;
        }
        return ProcessInstanceStatusEnum.IN_PROGRESS;
    }
    /**
     * 驳回
     *
     * @param actTaskId
     * @param reason
     * @return
     */
    @Override
    public ProcessInstanceStatusEnum rejectTask(String actTaskId, String reason) {
        TaskQuery taskQuery = taskService.createTaskQuery().taskId(actTaskId).includeProcessVariables().includeTaskLocalVariables();
        Task task = taskQuery.singleResult();
        if (Objects.isNull(task)) throw new BusinessException(HttpStatus.HTTP_INTERNAL_ERROR, "任务不存");
        Map<String, Object> param = checkRight(task, reason);
        taskService.claim(actTaskId, UserContextHelper.getUserId());
        runtimeService.deleteProcessInstance(task.getProcessInstanceId(), reason);
        rabbitTemplate.convertAndSend(ProcessConstant.EXCHANGE_PROCESS, ProcessConstant.ROUTING_KEY_PROCESS_REJECT, param);
        return ProcessInstanceStatusEnum.FINISH;
    }

    /**
     * 检查
     * @param task
     * @return
     */
    private Map<String, Object> checkRight(Task task, String reason) {
        String userId = UserContextHelper.getUserId();
        boolean hasRight = false;
        if (Objects.nonNull(task)) {
            // 候选角色
            List<String> candidateGroupIds = new ArrayList<>();
            List<IdentityLink> identityLinkList = taskService.getIdentityLinksForTask(task.getId());
            if (CollectionUtil.isNotEmpty(identityLinkList)) {
                for (IdentityLink identityLink : identityLinkList) {
                    if ("candidate".equals(identityLink.getType())) {
                        // 候选人
                        if (StrUtil.isNotEmpty(identityLink.getUserId())) {
                            // 用户是候选人，则权限
                            if (userId.equals(identityLink.getUserId())) {
                                hasRight = true;
                                break;
                            }
                        }
                        // 候选组
                        if (StrUtil.isNotEmpty(identityLink.getGroupId())) {
                            candidateGroupIds.add(identityLink.getGroupId());
                        }
                    }
                }
            }
            if (!hasRight) {
                // 用户角色
                List<String> roleIdList = adminFeign.getRoleIdsByUserId(userId).getData();
                if (CollectionUtil.isNotEmpty(roleIdList) && CollectionUtil.isNotEmpty(candidateGroupIds)) {
                    for (String roleId : roleIdList) {
                        // 用户角色是候选角色，则权限
                        if (candidateGroupIds.contains(roleId)) {
                            hasRight = true;
                            break;
                        }
                    }
                }
            }
            if (!hasRight) {
                // 用户部门
                UserVo userVo = adminFeign.getUserByUserId(userId).getData();
                if (Objects.nonNull(userVo)) {
                    String departmentId = userVo.getDepartmentId();
                    Map<String, Object> taskLocalVariables = task.getTaskLocalVariables();
                    String variableDepartment = (String) taskLocalVariables.get(ProcessConstant.VARIABLE_DEPARTMENT);
                    // 候选部门
                    List<String> candidateDepartmentIds = new ArrayList<>();
                    if (StrUtil.isNotEmpty(variableDepartment)) {
                        // 候选部门
                        candidateDepartmentIds = Arrays.asList(variableDepartment.split(","));
                    }
                    // 用户部门是候选部门，则权限
                    if (candidateDepartmentIds.contains(departmentId)) {
                        hasRight = true;
                    }
                }
            }
        }
        if (!hasRight) {
            throw new BusinessException(HttpStatus.HTTP_INTERNAL_ERROR, "没有权限处理");
        }

        Map<String, Object> param = task.getProcessVariables();
        if (Objects.isNull(param)) {
            param = new HashMap<>();
        }
        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(task.getProcessInstanceId()).singleResult();
        // 返回业务ID
        param.put(ProcessConstant.BUSINESS_ID, processInstance.getBusinessKey());
        if (StrUtil.isNotEmpty(reason)) {
            param.put(ProcessConstant.REASON, reason);
        }
        return param;
    }

    @Override
    public List<ProcessTaskVo> mapToDto(List<Task> taskList) {
        if (CollectionUtil.isEmpty(taskList)) {
            return null;
        }
        List<ProcessTaskVo> list = new ArrayList<>();
        for (Task task : taskList) {
            ProcessInstance instance = runtimeService.createProcessInstanceQuery()
                    .processInstanceId(task.getProcessInstanceId()).singleResult();
            ProcessTaskVo processTaskDto = new ProcessTaskVo();
            processTaskDto.setActTaskId(task.getId());
            processTaskDto.setName(task.getName());
            processTaskDto.setStartTime(task.getCreateTime());
            processTaskDto.setBusinessId(instance.getBusinessKey());
            processTaskDto.setVariables(task.getProcessVariables());
            list.add(processTaskDto);
        }
        return list;
    }

    private List<ActivityImpl> findRebuttable(ActivityImpl currActivity) {
        List<ActivityImpl> list = new ArrayList<>();
        // 当前节点的流入源
        List<PvmTransition> incoming = currActivity.getIncomingTransitions();

        for (PvmTransition pvmTransition : incoming) {
            TransitionImpl transition = (TransitionImpl) pvmTransition;
            ActivityImpl activity = transition.getSource();
            String type = (String) activity.getProperty("type");
            if ("startEvent".equals(type)) {
                return null;
            } else if ("userTask".equals(type)) {
                list.add(activity);
            } else if ("parallelGateway".equals(type)) {
                List<ActivityImpl> rebuttable = findRebuttable(activity);
                if (CollectionUtil.isNotEmpty(rebuttable)) {
                    list.addAll(rebuttable);
                }
            } else if ("exclusiveGateway".equals(type)) {
                List<ActivityImpl> rebuttable = findRebuttable(activity);
                if (CollectionUtil.isNotEmpty(rebuttable)) {
                    list.addAll(rebuttable);
                }
            }
        }
        return list;
    }

    private List<Task> filterCandidateUser(String userId, List<Task> taskList) {
        List<Task> list = new ArrayList<>();
        if (CollectionUtil.isNotEmpty(taskList)) {
            for (Task task : taskList) {
                ProcessDefinition definition = repositoryService.getProcessDefinition(task.getProcessDefinitionId());
                Map<String, Object> variables = task.getProcessVariables();
                String source = variables.get(ProcessConstant.VARIABLE_SOURCE).toString();
                String uniqueKey = source + ":" + definition.getKey() + ":" + task.getTaskDefinitionKey();

                List<IdentityLink> identityLinks = taskService.getIdentityLinksForTask(task.getId());
                if (CollectionUtil.isNotEmpty(identityLinks)) {
                    for (IdentityLink identityLink : identityLinks) {
                        if (userId.equals(identityLink.getUserId())) {
                            list.add(task);
                            break;
                        }
                        if (StringUtils.isNotBlank(identityLink.getGroupId())) {
                            List<String> userIds = WorkFlowUtils.getValidUser(uniqueKey, CandidateTypeEnum.GROUP.getCode(), identityLink.getGroupId(), variables);
                            if (CollectionUtil.isNotEmpty(userIds) && userIds.contains(userId)) {
                                list.add(task);
                                break;
                            }
                        }
                    }
                }
                String departmentIds = (String) taskService.getVariableLocal(task.getId(), ProcessConstant.VARIABLE_DEPARTMENT);
                if (StrUtil.isNotEmpty(departmentIds)) {
                    List<String> candidateGroups = Arrays.asList(departmentIds.split(","));
                    for (String candidateGroup : candidateGroups) {
                        List<String> userIds = WorkFlowUtils.getValidUser(uniqueKey, CandidateTypeEnum.DEPARTMENT.getCode(), candidateGroup, variables);
                        if (CollectionUtil.isNotEmpty(userIds) && userIds.contains(userId)) {
                            list.add(task);
                            break;
                        }
                    }
                }
            }
        }
        return list;
    }

    private TaskQuery createQuery(ProcessTaskVo processTaskDto) {
        TaskQuery query = taskService.createTaskQuery().includeProcessVariables();

        if (StringUtils.isNotBlank(processTaskDto.getActTaskId())) {
            query.taskId(processTaskDto.getActTaskId());
        }

        if (StringUtils.isNotBlank(processTaskDto.getActDefinitionKey())) {
            query.processDefinitionKey(processTaskDto.getActDefinitionKey());
        }

        if (StringUtils.isNotBlank(processTaskDto.getSource())) {
            query.processVariableValueEquals(ProcessConstant.VARIABLE_SOURCE, processTaskDto.getSource());
        }

        if (StringUtils.isNotBlank(processTaskDto.getCandidateUser())) {
            query.or();
            query.taskCandidateOrAssigned(processTaskDto.getCandidateUser());
            RestResult<List<String>> roleResult = adminFeign.getRoleIdsByUserId(processTaskDto.getCandidateUser());
            if (roleResult.isOk() && CollectionUtil.isNotEmpty(roleResult.getData())) {
                for (String roleId : roleResult.getData()) {
                    query.taskCandidateGroup(roleId);
                }
            }
            RestResult<UserVo> userResult = adminFeign.getUserByUserId(processTaskDto.getCandidateUser());
            if (userResult.isOk() && ObjectUtil.isNotNull(userResult.getData())) {
                query.taskVariableValueLike(ProcessConstant.VARIABLE_DEPARTMENT, "%" + userResult.getData().getDepartmentId() + "%");
            }
            query.endOr();
        }

        if (MapUtil.isNotEmpty(processTaskDto.getVariables())) {
            addFiltersByVariables(query, processTaskDto.getVariables());
        }

        if (CollectionUtil.isNotEmpty(processTaskDto.getQueryOr())) {
            for (Map<String, Object> variables : processTaskDto.getQueryOr()) {
                if (MapUtil.isNotEmpty(variables)) {
                    query.or();
                    addFiltersByVariables(query, variables);
                    query.endOr();
                }
            }
        }

        query.orderByTaskCreateTime().desc();

        return query;
    }

    private void addFiltersByVariables(TaskQuery query, Map<String, Object> variables) {
        Iterator<Map.Entry<String, Object>> iterator = variables.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, Object> entry = iterator.next();
            String[] terms = entry.getKey().split("_");
            String condition = terms[0];
            String key = terms[1];
            switch (Operator.valueOf(condition)) {
                case EQ:
                    query.processVariableValueEquals(key, entry.getValue());
                    break;
                case NE:
                    query.processVariableValueNotEquals(key, entry.getValue());
                    break;
                case LIKE:
                    query.processVariableValueLike(key, "%" + entry.getValue().toString() + "%");
                    break;
                case GT:
                    query.processVariableValueGreaterThan(key, entry.getValue());
                    break;
                case GTE:
                    query.processVariableValueGreaterThanOrEqual(key, entry.getValue());
                    break;
                case LT:
                    query.processVariableValueLessThan(key, entry.getValue());
                    break;
                case LTE:
                    query.processVariableValueLessThanOrEqual(key, entry.getValue());
                    break;
                case IN:
                    List<Object> inList = convertToList(entry.getValue());
                    query.or();
                    for (Object o : inList) {
                        query.processVariableValueEquals(key, o);
                    }
                    query.endOr();
                    break;
                case NOTIN:
                    List<Object> notInList = convertToList(entry.getValue());
                    for (Object o : notInList) {
                        query.processVariableValueNotEquals(key, o);
                    }
                    break;
                default:
                    break;
            }
        }
    }

    private List<Object> convertToList(Object value) {
        List<Object> list;
        if (value instanceof List) {
            list = (List<Object>) value;
        } else {
            throw new BusinessException(HttpStatus.HTTP_INTERNAL_ERROR, "require List");
        }
        return list;
    }

    private enum Operator {
        EQ,
        NE,
        LIKE,
        RLIKE,
        LLIKE,
        LIKEOR,
        NULL,
        NNULL,
        GT,
        LT,
        GTE,
        LTE,
        IN,
        BETWEEN,
        NOTIN,
        NOTLIKE;
    }
}
