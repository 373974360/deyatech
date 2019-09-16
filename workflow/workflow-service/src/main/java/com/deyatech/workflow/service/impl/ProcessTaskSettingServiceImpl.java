package com.deyatech.workflow.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.deyatech.common.base.BaseServiceImpl;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import com.deyatech.workflow.entity.IProcessDefinition;
import com.deyatech.workflow.entity.ProcessTaskSetting;
import com.deyatech.workflow.mapper.ProcessTaskSettingMapper;
import com.deyatech.workflow.service.ProcessDefinitionService;
import com.deyatech.workflow.service.ProcessTaskSettingService;
import com.deyatech.workflow.vo.ProcessTaskSettingVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Collection;

/**
 * <p>
 * 流程设置 服务实现类
 * </p>
 *
 * @Author lee.
 * @since 2019-07-31
 */
@Service
public class ProcessTaskSettingServiceImpl extends BaseServiceImpl<ProcessTaskSettingMapper, ProcessTaskSetting> implements ProcessTaskSettingService {

    @Autowired
    private ProcessDefinitionService processDefinitionService;

    /**
     * 单个将对象转换为vo流程设置
     *
     * @param processTaskSetting
     * @return
     */
    @Override
    public ProcessTaskSettingVo setVoProperties(ProcessTaskSetting processTaskSetting){
        ProcessTaskSettingVo processTaskSettingVo = new ProcessTaskSettingVo();
        BeanUtil.copyProperties(processTaskSetting, processTaskSettingVo);
        return processTaskSettingVo;
    }

    /**
     * 批量将对象转换为vo流程设置
     *
     * @param processTaskSettings
     * @return
     */
    @Override
    public List<ProcessTaskSettingVo> setVoProperties(Collection processTaskSettings){
        List<ProcessTaskSettingVo> processTaskSettingVos = CollectionUtil.newArrayList();
        if (CollectionUtil.isNotEmpty(processTaskSettings)) {
            for (Object adminProcessTaskSetting : processTaskSettings) {
                ProcessTaskSettingVo processTaskSettingVo = new ProcessTaskSettingVo();
                BeanUtil.copyProperties(adminProcessTaskSetting, processTaskSettingVo);
                processTaskSettingVos.add(processTaskSettingVo);
            }
        }
        return processTaskSettingVos;
    }

    @Override
    public ProcessTaskSetting get(String actDefinitionId, String actTaskDefinitionId, String source) {
        ProcessTaskSetting processTaskSetting = new ProcessTaskSetting();
        processTaskSetting.setActDefinitionId(actDefinitionId);
        processTaskSetting.setActTaskDefinitionId(actTaskDefinitionId);
//        processTaskSetting.setSource(source);
        return getByBean(processTaskSetting);
    }

    @Override
    public List<ProcessTaskSetting> findByActDefinitionId(String actDefinitionId) {
        QueryWrapper<ProcessTaskSetting> wrapper = new QueryWrapper<>();
        wrapper.eq("act_definition_id", actDefinitionId);
        return list(wrapper);
    }

    @Override
    public ProcessTaskSetting getUserLastSetting(String actDefinitionId, String userId) {
        return null;
    }

    @Override
    public List<ProcessTaskSetting> copyOldSettings(String actDefinitionId) {
        IProcessDefinition processDefinition = processDefinitionService.getByActDefinitionId(actDefinitionId);
        IProcessDefinition old = processDefinitionService.getLastVersionByActDefinitionKey(
                processDefinition.getActDefinitionKey(), processDefinition.getVer());
        if (old == null) {
            return null;
        }
        List<ProcessTaskSetting> oldSettings = findByActDefinitionId(old.getActDefinitionId());
        List<ProcessTaskSetting> newSettings = new ArrayList<>();
        if (CollectionUtil.isNotEmpty(oldSettings)) {
            ProcessTaskSetting processTaskSetting = null;
            for (ProcessTaskSetting setting : oldSettings) {
                processTaskSetting = new ProcessTaskSetting();
                processTaskSetting.setActDefinitionId(actDefinitionId);
                processTaskSetting.setActTaskDefinitionId(setting.getActTaskDefinitionId());
                processTaskSetting.setAssignee(setting.getAssignee());
                processTaskSetting.setCandidateType(setting.getCandidateType());
                processTaskSetting.setCandidateUsers(setting.getCandidateUsers());
                processTaskSetting.setCandidateGroups(setting.getCandidateGroups());
                processTaskSetting.setCandidateDepartments(setting.getCandidateDepartments());
                processTaskSetting.setAutoPass(setting.getAutoPass());
                processTaskSetting.setSource(setting.getSource());
                saveOrUpdate(processTaskSetting);
                newSettings.add(processTaskSetting);
            }
        }
        return newSettings;
    }
}
