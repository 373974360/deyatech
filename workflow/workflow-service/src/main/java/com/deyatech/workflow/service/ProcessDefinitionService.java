package com.deyatech.workflow.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.deyatech.common.base.BaseService;
import com.deyatech.workflow.entity.IProcessDefinition;
import com.deyatech.workflow.vo.ProcessDefinitionVo;

import java.util.Collection;
import java.util.List;

/**
 * <p>
 *  流程定义 服务类
 * </p>
 *
 * @Author lee.
 * @since 2019-07-31
 */
public interface ProcessDefinitionService extends BaseService<IProcessDefinition> {

    /**
     * 单个将对象转换为vo流程定义
     *
     * @param processDefinition
     * @return
     */
    ProcessDefinitionVo setVoProperties(IProcessDefinition processDefinition);

    /**
     * 批量将对象转换为vo流程定义
     *
     * @param processDefinitions
     * @return
     */
    List<ProcessDefinitionVo> setVoProperties(Collection processDefinitions);

    /**
     * 查询最新版本的流程定义列表
     *
     * @param processDefinition
     * @return
     */
    IPage<ProcessDefinitionVo> findLastVersionPage(IProcessDefinition processDefinition);

    /**
     * 根据流程模型部署时保存流程定义信息
     *
     * @param processModelId
     * @param actDeploymentId
     * @return
     */
    IProcessDefinition save(String processModelId, String actDeploymentId);

    /**
     * 根据activiti流程定义id查询
     *
     * @param actDefinitionId
     * @return
     */
    IProcessDefinition getByActDefinitionId(String actDefinitionId);

    /**
     * 根据流程定义key查询上一版本的流程定义
     *
     * @param actDefinitionKey
     * @param version
     * @return
     */
    IProcessDefinition getLastVersionByActDefinitionKey(String actDefinitionKey, int version);

    /**
     * 激活
     *
     * @param keys
     */
    void active(List<String> keys);

    /**
     * 挂起
     *
     * @param keys
     */
    void suspend(List<String> keys);
}
