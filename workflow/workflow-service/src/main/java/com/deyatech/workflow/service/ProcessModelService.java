package com.deyatech.workflow.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.deyatech.workflow.entity.ProcessModel;
import com.deyatech.workflow.vo.ProcessModelVo;
import com.deyatech.common.base.BaseService;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.util.MultiValueMap;

import java.util.Collection;
import java.util.List;

/**
 * <p>
 *  流程模型 服务类
 * </p>
 *
 * @Author lee.
 * @since 2019-07-31
 */
public interface ProcessModelService extends BaseService<ProcessModel> {

    /**
     * 单个将对象转换为vo流程模型
     *
     * @param processModel
     * @return
     */
    ProcessModelVo setVoProperties(ProcessModel processModel);

    /**
     * 批量将对象转换为vo流程模型
     *
     * @param processModels
     * @return
     */
    List<ProcessModelVo> setVoProperties(Collection processModels);

    /**
     * 查询最新版本流程模型列表
     *
     * @param processModel
     * @return
     */
    IPage<ProcessModelVo> findLastVersionPage(ProcessModel processModel);

    /**
     * 新建一个流程模型
     *
     * @param processModelVo
     * @return
     */
    ProcessModel add(ProcessModelVo processModelVo);

    /**
     * 获取流程模型的流程图
     *
     * @param modelId
     * @return
     */
    ObjectNode getEditorJson(String modelId);

    /**
     * 保存流程模型流程图
     *
     * @param modelId
     * @param values
     */
    void saveModel(String modelId, MultiValueMap<String, String> values);

    /**
     * 根据activiti流程模型id查询最新版本
     *
     * @param actModelId
     * @return
     */
    ProcessModel getLastByActModelId(String actModelId);

    /**
     * 流程模型部署
     *
     * @param actModelIds
     * @return
     */
    List<ProcessModel> deploy(List<String> actModelIds);
}
