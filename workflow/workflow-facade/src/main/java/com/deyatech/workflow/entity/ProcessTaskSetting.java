package com.deyatech.workflow.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableField;
import com.deyatech.common.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 流程任务节点设置
 * </p>
 *
 * @author lee.
 * @since 2019-07-31
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("workflow_process_task_setting")
@ApiModel(value = "流程任务节点设置对象", description = "流程任务节点设置", parent = BaseEntity.class)
public class ProcessTaskSetting extends BaseEntity {

    @ApiModelProperty(value = "activiti流程定义id", dataType = "String")
    @TableField("act_definition_id")
    private String actDefinitionId;

    @ApiModelProperty(value = "activiti任务定义id", dataType = "String")
    @TableField("act_task_definition_id")
    private String actTaskDefinitionId;

    @ApiModelProperty(value = "来源系统", dataType = "String")
    @TableField("source_")
    private String source;

    @ApiModelProperty(value = "流程定义指定审核人员", dataType = "String")
    @TableField("assignee_")
    private String assignee;

    @ApiModelProperty(value = "审核人类型", dataType = "Integer")
    @TableField("candidate_type")
    private Integer candidateType;

    @ApiModelProperty(value = "审核人员", dataType = "String")
    @TableField("candidate_users")
    private String candidateUsers;

    @ApiModelProperty(value = "审核人角色", dataType = "String")
    @TableField("candidate_groups")
    private String candidateGroups;

    @ApiModelProperty(value = "审核人部门", dataType = "String")
    @TableField("candidate_departments")
    private String candidateDepartments;

    @ApiModelProperty(value = "是否自动审批通过", dataType = "Boolean")
    @TableField("auto_pass")
    private Boolean autoPass;

}
