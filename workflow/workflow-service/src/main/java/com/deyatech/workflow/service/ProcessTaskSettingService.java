package com.deyatech.workflow.service;

import com.deyatech.workflow.entity.ProcessTaskSetting;
import com.deyatech.workflow.vo.ProcessTaskSettingVo;
import com.deyatech.common.base.BaseService;
import java.util.Collection;
import java.util.List;

/**
 * <p>
 *  流程设置 服务类
 * </p>
 *
 * @Author lee.
 * @since 2019-07-31
 */
public interface ProcessTaskSettingService extends BaseService<ProcessTaskSetting> {

    /**
     * 单个将对象转换为vo流程设置
     *
     * @param processTaskSetting
     * @return
     */
    ProcessTaskSettingVo setVoProperties(ProcessTaskSetting processTaskSetting);

    /**
     * 批量将对象转换为vo流程设置
     *
     * @param processTaskSettings
     * @return
     */
    List<ProcessTaskSettingVo> setVoProperties(Collection processTaskSettings);

    /**
     * 查询任务节点配置
     *
     * @param actDefinitionId
     * @param actTaskDefinitionId
     * @param source
     * @return
     */
    ProcessTaskSetting get(String actDefinitionId, String actTaskDefinitionId, String source);

    /**
     * 根据activiti流程定义id查询
     *
     * @param actDefinitionId
     * @return
     */
    List<ProcessTaskSetting> findByActDefinitionId(String actDefinitionId);

    /**
     * 获取用户最后一次设置内容
     *
     * @param actDefinitionId
     * @param userId
     * @return
     */
    ProcessTaskSetting getUserLastSetting(String actDefinitionId, String userId);

    /**
     * 将旧流程的设置拷贝到新流程
     *
     * @param actDefinitionId
     * @return
     */
    List<ProcessTaskSetting> copyOldSettings(String actDefinitionId);
}
