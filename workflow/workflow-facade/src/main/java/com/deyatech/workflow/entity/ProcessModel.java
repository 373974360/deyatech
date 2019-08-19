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
 * 流程模型
 * </p>
 *
 * @author lee.
 * @since 2019-07-31
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("workflow_process_model")
@ApiModel(value = "流程模型对象", description = "流程模型", parent = BaseEntity.class)
public class ProcessModel extends BaseEntity {

    @ApiModelProperty(value = "activiti流程模型id", dataType = "String")
    @TableField("act_model_id")
    private String actModelId;

    @ApiModelProperty(value = "流程模型名称", dataType = "String")
    @TableField("name_")
    private String name;

    @ApiModelProperty(value = "流程定义id", dataType = "String")
    @TableField("process_definition_id")
    private String processDefinitionId;

    @ApiModelProperty(value = "版本", dataType = "Integer")
    @TableField("ver_")
    private Integer ver;
}
