package com.deyatech.workflow.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableField;
import com.deyatech.common.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * <p>
 * 流程定义
 * </p>
 *
 * @author lee.
 * @since 2019-07-31
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("workflow_process_definition")
@ApiModel(value = "流程定义对象", description = "流程定义", parent = BaseEntity.class)
public class IProcessDefinition extends BaseEntity {

    @ApiModelProperty(value = "流程模型id", dataType = "String")
    @TableField("process_model_id")
    private String processModelId;

    @ApiModelProperty(value = "activiti流程发布id", dataType = "String")
    @TableField("act_deployment_id")
    private String actDeploymentId;

    @ApiModelProperty(value = "activiti流程定义id", dataType = "String")
    @TableField("act_definition_id")
    private String actDefinitionId;

    @ApiModelProperty(value = "流程名称", dataType = "String")
    @TableField("name_")
    private String name;

    @ApiModelProperty(value = "activiti流程定义key", dataType = "String")
    @TableField("act_definition_key")
    private String actDefinitionKey;

    @ApiModelProperty(value = "流程发布时间", dataType = "Date")
    @TableField("deployment_time")
    private Date deploymentTime;

    @ApiModelProperty(value = "版本", dataType = "Integer")
    @TableField("ver_")
    private Integer ver;
}
