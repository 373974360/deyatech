package com.deyatech.workflow.service.impl;

import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpStatus;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.deyatech.common.exception.BusinessException;
import com.deyatech.workflow.custom.MyBpmnJsonConverter;
import com.deyatech.workflow.entity.IProcessDefinition;
import com.deyatech.workflow.entity.ProcessModel;
import com.deyatech.workflow.mapper.ProcessModelMapper;
import com.deyatech.workflow.service.ProcessDefinitionService;
import com.deyatech.workflow.service.ProcessModelService;
import com.deyatech.common.base.BaseServiceImpl;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import com.deyatech.workflow.service.ProcessTaskSettingService;
import com.deyatech.workflow.vo.ProcessModelVo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.common.collect.ImmutableMap;
import lombok.extern.slf4j.Slf4j;
import org.activiti.bpmn.exceptions.XMLException;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.editor.constants.ModelDataJsonConstants;
import org.activiti.engine.ActivitiException;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.impl.persistence.entity.ModelEntity;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.DeploymentBuilder;
import org.activiti.engine.repository.Model;
import org.activiti.engine.repository.ModelQuery;
import org.apache.batik.transcoder.TranscoderInput;
import org.apache.batik.transcoder.TranscoderOutput;
import org.apache.batik.transcoder.image.PNGTranscoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;

import java.io.*;
import java.util.*;

/**
 * <p>
 * 流程模型 服务实现类
 * </p>
 *
 * @Author lee.
 * @since 2019-07-31
 */
@Slf4j
@Service
public class ProcessModelServiceImpl extends BaseServiceImpl<ProcessModelMapper, ProcessModel> implements ModelDataJsonConstants, ProcessModelService {

    private ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private RepositoryService repositoryService;

    @Autowired
    private ProcessDefinitionService processDefinitionService;

    @Autowired
    private ProcessTaskSettingService processTaskSettingService;

    /**
     * 单个将对象转换为vo流程模型
     *
     * @param processModel
     * @return
     */
    @Override
    public ProcessModelVo setVoProperties(ProcessModel processModel){
        ProcessModelVo processModelVo = new ProcessModelVo();
        BeanUtil.copyProperties(processModel, processModelVo);
        return processModelVo;
    }

    /**
     * 批量将对象转换为vo流程模型
     *
     * @param processModels
     * @return
     */
    @Override
    public List<ProcessModelVo> setVoProperties(Collection processModels){
        List<ProcessModelVo> processModelVos = CollectionUtil.newArrayList();
        if (CollectionUtil.isNotEmpty(processModels)) {
            for (Object adminProcessModel : processModels) {
                ProcessModelVo processModelVo = new ProcessModelVo();
                BeanUtil.copyProperties(adminProcessModel, processModelVo);
                processModelVos.add(processModelVo);
            }
        }
        return processModelVos;
    }

    @Override
    public IPage<ProcessModelVo> findLastVersionPage(ProcessModel processModel) {
        return getBaseMapper().findLastVersionPage(getPageByBean(processModel), processModel);
    }

    @Override
    public ProcessModel add(ProcessModelVo processModelVo) {
        long count = repositoryService.createModelQuery().modelName(processModelVo.getName()).count();
        if (count > 0) {
            throw new BusinessException(HttpStatus.HTTP_INTERNAL_ERROR, "模型名称已经被使用");
        }

        Model model = new ModelEntity();
        model.setName(processModelVo.getName());
        model.setMetaInfo(processModelVo.getMetaInfo());
        model.setVersion(1);
        repositoryService.saveModel(model);

        ProcessModel processModel = new ProcessModel();
        processModel.setActModelId(model.getId());
        processModel.setName(model.getName());
        processModel.setVer(1);
        saveOrUpdate(processModel);

        try {
            Map<String, Object> objectMap = new HashMap<>();
            objectMap.put("resourceId", model.getId());
            objectMap.put("properties", modelProperties(model));
            objectMap.put("stencil", ImmutableMap.of("id", "BPMNDiagram"));
            objectMap.put("childShapes", Collections.emptyList());
            objectMap.put("bounds", ImmutableMap.of("lowerRight", ImmutableMap.of("x", 1200, "y", 1050),
                    "upperLeft", ImmutableMap.of("x", 0, "y", 0)));
            objectMap.put("stencilset", ImmutableMap.of("url", "stencilsets/bpmn2.0/bpmn2.0.json",
                    "namespace", "http://b3mn.org/stencilset/bpmn2.0#"));
            objectMap.put("ssextensions", Collections.emptyList());

            repositoryService.addModelEditorSource(model.getId(), objectMapper.writeValueAsString(objectMap).getBytes("utf-8"));
            return processModel;
        } catch (JsonProcessingException e) {
            log.error("创建流程模型出错", e);
        } catch (UnsupportedEncodingException e) {
            log.error("创建流程模型出错", e);
        }
        return null;
    }

    @Override
    public ObjectNode getEditorJson(String modelId) {
        ObjectNode modelNode = null;
        Model model = repositoryService.getModel(modelId);
        if (model != null) {
            try {
                if (StrUtil.isNotBlank(model.getMetaInfo())) {
                    modelNode = (ObjectNode) objectMapper.readTree(model.getMetaInfo());
                } else {
                    modelNode = objectMapper.createObjectNode();
                    modelNode.put(MODEL_NAME, model.getName());
                }
                modelNode.put(MODEL_ID, model.getId());
                ObjectNode editorJsonNode = (ObjectNode) objectMapper.readTree(
                        new String(repositoryService.getModelEditorSource(model.getId()), "utf-8"));
                modelNode.put("model", editorJsonNode);
            } catch (Exception e) {
                throw new ActivitiException("Error creating model JSON", e);
            }
        }
        return modelNode;
    }

    @Override
    public void saveModel(String modelId, MultiValueMap<String, String> values) {
        try {
            List<Model> modelList = repositoryService.createModelQuery().modelName(values.getFirst("name")).list();
            if (CollectionUtil.isNotEmpty(modelList)) {
                for (Model m : modelList) {
                    // 存在同名ID不相同
                    if (!m.getId().equals(modelId)) {
                        throw new BusinessException(HttpStatus.HTTP_INTERNAL_ERROR, "模型名称已经被使用");
                    }
                }
            }

            Model model = repositoryService.getModel(modelId);
            ObjectNode modelJson = objectMapper.createObjectNode();
            if (StrUtil.isNotBlank(model.getMetaInfo())) {
                modelJson = (ObjectNode) objectMapper.readTree(model.getMetaInfo());
            }
            modelJson.put(MODEL_NAME, values.getFirst("name"));
            modelJson.put(MODEL_DESCRIPTION, values.getFirst("description"));
            model.setMetaInfo(modelJson.toString());
            model.setName(values.getFirst("name"));
            model.setDeploymentId(null);
            repositoryService.saveModel(model);

            ProcessModel old = getLastByActModelId(modelId);

            ProcessModel processModel = new ProcessModel();
            if (old.getUpdateTime().equals(old.getCreateTime()) && old.getVer() == 1) {
                processModel = old;
            } else {
                processModel.setActModelId(modelId);
                processModel.setVer(old.getVer() + 1);
                processModel.setCreateTime(old.getCreateTime());
                processModel.setCreateBy(old.getCreateBy());
                removeById(old.getId());
            }
            processModel.setName(model.getName());
            saveOrUpdate(processModel);

            repositoryService.addModelEditorSource(model.getId(), values.getFirst("json_xml").getBytes("utf-8"));
            InputStream svgStream = new ByteArrayInputStream(values.getFirst("svg_xml").getBytes("utf-8"));
            TranscoderInput input = new TranscoderInput(svgStream);

            PNGTranscoder transcoder = new PNGTranscoder();
            ByteArrayOutputStream outStream = new ByteArrayOutputStream();
            TranscoderOutput output = new TranscoderOutput(outStream);

            transcoder.transcode(input, output);
            final byte[] result = outStream.toByteArray();
            repositoryService.addModelEditorSourceExtra(model.getId(), result);
            outStream.close();
        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            throw new ActivitiException("Error saving model", e);
        }
    }

    @Override
    public ProcessModel getLastByActModelId(String actModelId) {
        return getBaseMapper().getLastByActModelId(actModelId);
    }

    @Override
    public List<ProcessModel> deploy(List<String> actModelIds) {
        List<ProcessModel> list = new ArrayList<>();
        if (CollectionUtil.isNotEmpty(actModelIds)) {
            for (String id : actModelIds) {
                if (StrUtil.isNotBlank(id)) {
                    list.add(deployModel(id));
                }
            }
        }
        return list;
    }

    private ProcessModel deployModel(String actModelId) {
        Model model = repositoryService.createModelQuery().modelId(actModelId).singleResult();
        String name = model.getName() + ".bpmn20.xml";
        byte[] json_byte = repositoryService.getModelEditorSource(actModelId);
        try {
            JsonNode modelNode = objectMapper.readTree(json_byte);
            BpmnModel bpmnModel = new MyBpmnJsonConverter().convertToBpmnModel(modelNode);
            DeploymentBuilder deploymentBuilder = repositoryService.createDeployment().addBpmnModel(name, bpmnModel);
//            deploymentBuilder.disableSchemaValidation();// 不禁用的话会开启xml验证
            deploymentBuilder.name(model.getName());
            deploymentBuilder.category(model.getCategory());
            deploymentBuilder.tenantId(model.getTenantId());

            Deployment deployment = deploymentBuilder.deploy();
            model.setDeploymentId(deployment.getId());
            repositoryService.saveModel(model);

            ProcessModel processModel = getLastByActModelId(actModelId);
            IProcessDefinition processDefinition = processDefinitionService.save(processModel.getId(), deployment.getId());
            processTaskSettingService.copyOldSettings(processDefinition.getActDefinitionId());
            processModel.setProcessDefinitionId(processDefinition.getId());
            saveOrUpdate(processModel);
            return processModel;
        } catch (XMLException e) {
            log.error("流程部署出错", e);
        } catch (IOException e) {
            log.error("流程部署出错", e);
        }
        throw new BusinessException(HttpStatus.HTTP_INTERNAL_ERROR, "流程部署出错，请检查流程是否编辑完整");
    }

    private Map<String, Object> modelProperties(Model model) {
        Map<String, Object> props = new HashMap<>();
        props.put("process_id", "new_process");
        props.put("name", model.getName());
        props.put("documentation", "");
        props.put("process_author", "");
        props.put("process_version", "");
        props.put("process_namespace", "http://www.activiti.org/processdef");
        props.put("executionlisteners", "");
        props.put("eventlisteners", "");
        props.put("signaldefinitions", "");
        props.put("messagedefinitions", "");
        return props;
    }
}
