package com.deyatech.workflow.vo;

import com.deyatech.workflow.entity.IProcessDefinition;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 流程定义扩展对象
 * </p>
 *
 * @author lee.
 * @since 2019-07-31
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@ApiModel(value = "流程定义扩展对象", description = "流程定义扩展对象", parent = IProcessDefinition.class)
public class ProcessDefinitionVo extends IProcessDefinition {
}
