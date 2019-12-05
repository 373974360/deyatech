package com.deyatech.workflow.service.impl;

import cn.hutool.http.HttpStatus;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.deyatech.common.exception.BusinessException;
import com.deyatech.workflow.entity.IProcessDefinition;
import com.deyatech.workflow.mapper.ProcessDefinitionMapper;
import com.deyatech.workflow.service.ProcessDefinitionService;
import com.deyatech.common.base.BaseServiceImpl;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import com.deyatech.workflow.vo.ProcessDefinitionVo;
import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.ActivitiException;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.ProcessDefinition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * <p>
 * 流程定义 服务实现类
 * </p>
 *
 * @Author lee.
 * @since 2019-07-31
 */
@Slf4j
@Service
public class ProcessDefinitionServiceImpl extends BaseServiceImpl<ProcessDefinitionMapper, IProcessDefinition> implements ProcessDefinitionService {

    @Autowired
    private RepositoryService repositoryService;

    /**
     * 单个将对象转换为vo流程定义
     *
     * @param processDefinition
     * @return
     */
    @Override
    public ProcessDefinitionVo setVoProperties(IProcessDefinition processDefinition){
        ProcessDefinitionVo processDefinitionVo = new ProcessDefinitionVo();
        BeanUtil.copyProperties(processDefinition, processDefinitionVo);
        return processDefinitionVo;
    }

    /**
     * 批量将对象转换为vo流程定义
     *
     * @param processDefinitions
     * @return
     */
    @Override
    public List<ProcessDefinitionVo> setVoProperties(Collection processDefinitions){
        List<ProcessDefinitionVo> processDefinitionVos = CollectionUtil.newArrayList();
        if (CollectionUtil.isNotEmpty(processDefinitions)) {
            for (Object adminProcessDefinition : processDefinitions) {
                ProcessDefinitionVo processDefinitionVo = new ProcessDefinitionVo();
                BeanUtil.copyProperties(adminProcessDefinition, processDefinitionVo);
                processDefinitionVos.add(processDefinitionVo);
            }
        }
        return processDefinitionVos;
    }

    @Override
    public IPage<ProcessDefinitionVo> findLastVersionPage(IProcessDefinition processDefinition) {
        return getBaseMapper().findLastVersionPage(getPageByBean(processDefinition), processDefinition);
    }

    public IProcessDefinition save(String processModelId, String actDeploymentId) {
        ProcessDefinition actProcessDefinition = repositoryService.createProcessDefinitionQuery()
                .deploymentId(actDeploymentId).singleResult();
        IProcessDefinition processDefinition = new IProcessDefinition();
        processDefinition.setProcessModelId(processModelId);
        processDefinition.setActDeploymentId(actDeploymentId);
        processDefinition.setActDefinitionId(actProcessDefinition.getId());
        processDefinition.setActDefinitionKey(actProcessDefinition.getKey());
        processDefinition.setName(actProcessDefinition.getName());
        processDefinition.setVer(actProcessDefinition.getVersion());
        processDefinition.setDeploymentTime(new Date());
        saveOrUpdate(processDefinition);
        return processDefinition;
    }

    @Override
    public IProcessDefinition getByActDefinitionId(String actDefinitionId) {
        IProcessDefinition processDefinition = new IProcessDefinition();
        processDefinition.setActDefinitionId(actDefinitionId);
        return getByBean(processDefinition);
    }

    @Override
    public IProcessDefinition getLastVersionByActDefinitionKey(String actDefinitionKey, int version) {
        return getBaseMapper().getLastVersionByActDefinitionKey(actDefinitionKey, version);
    }

    @Override
    public void active(List<String> actDefinitionIdList) {
        if (CollectionUtil.isNotEmpty(actDefinitionIdList)) {
            for (String id : actDefinitionIdList) {
                try {
                    repositoryService.activateProcessDefinitionById(id);
                } catch (ActivitiException e) {
                    if (e.getMessage().contains("already in state 'active'")) {
                        log.warn("流程已经是启用状态");
                    } else {
                        throw new BusinessException(HttpStatus.HTTP_INTERNAL_ERROR, "流程定义启用操作失败");
                    }
                }
                getBaseMapper().enableByActDefinitionId(id);
            }
        }
    }

    @Override
    public void suspend(List<String> actDefinitionIdList) {
        if (CollectionUtil.isNotEmpty(actDefinitionIdList)) {
            for (String id : actDefinitionIdList) {
                try {
                    repositoryService.suspendProcessDefinitionById(id);
                } catch (ActivitiException e) {
                    if (e.getMessage().contains("already in state 'suspended'")) {
                        log.warn("流程已经是停用状态");
                    } else {
                        throw new BusinessException(HttpStatus.HTTP_INTERNAL_ERROR, "流程定义停用操作失败");
                    }
                }
                getBaseMapper().disableByActDefinitionId(id);
            }
        }
    }

    /**
     * 根据就ID获取新ID
     *
     * @param oldActDefinitionId
     * @return
     */
    @Override
    public IProcessDefinition getActDefinitionIdAndKey(String oldActDefinitionId) {
        return baseMapper.getActDefinitionByOldId(oldActDefinitionId);
    }
}
