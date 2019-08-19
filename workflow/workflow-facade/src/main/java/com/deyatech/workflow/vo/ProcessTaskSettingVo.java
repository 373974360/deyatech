package com.deyatech.workflow.vo;

import com.deyatech.workflow.entity.ProcessTaskSetting;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 流程任务节点设置扩展对象
 * </p>
 *
 * @author lee.
 * @since 2019-07-31
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@ApiModel(value = "流程任务节点设置扩展对象", description = "流程任务节点设置扩展对象", parent = ProcessTaskSetting.class)
public class ProcessTaskSettingVo extends ProcessTaskSetting {
}
