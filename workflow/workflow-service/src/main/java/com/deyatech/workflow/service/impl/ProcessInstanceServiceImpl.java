package com.deyatech.workflow.service.impl;

import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpStatus;
import com.deyatech.common.Constants;
import com.deyatech.common.enums.ProcessInstanceStatusEnum;
import com.deyatech.common.exception.BusinessException;
import com.deyatech.workflow.service.ProcessInstanceService;
import com.deyatech.workflow.vo.ProcessInstanceVo;
import org.activiti.engine.HistoryService;
import org.activiti.engine.IdentityService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.runtime.ProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @author doukang
 * @description TODO
 * @date 2019/8/6 10:53
 */
@Service
public class ProcessInstanceServiceImpl implements ProcessInstanceService {

    @Autowired
    private RepositoryService repositoryService;

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private IdentityService identityService;

    @Autowired
    private HistoryService historyService;

    @Override
    public ProcessInstanceStatusEnum startInstance(ProcessInstanceVo processInstanceVo) {
        String key = processInstanceVo.getActDefinitionKey();
        if (StrUtil.isBlank(key)) {
            throw new BusinessException(HttpStatus.HTTP_INTERNAL_ERROR, "请选择流程");
        }
        long count = repositoryService.createProcessDefinitionQuery().processDefinitionKey(key).count();
        if (count == 0) {
            throw new BusinessException(HttpStatus.HTTP_INTERNAL_ERROR, "未找到流程");
        }

        Map<String, Object> variables = processInstanceVo.getVariables();
        if (variables == null) {
            variables = new HashMap<>();
        }
        variables.put(Constants.VARIABLE_USER_ID, processInstanceVo.getUserId());
        variables.put(Constants.VARIABLE_SOURCE, processInstanceVo.getSource());

        identityService.setAuthenticatedUserId(processInstanceVo.getUserId());
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(key, processInstanceVo.getBusinessId(), variables);
        if (processInstance.isEnded()) {
            return ProcessInstanceStatusEnum.FINISH;
        }
        return ProcessInstanceStatusEnum.IN_PROGRESS;
    }
}
