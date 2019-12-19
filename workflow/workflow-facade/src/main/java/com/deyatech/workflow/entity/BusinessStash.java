package com.deyatech.workflow.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.deyatech.common.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * <p>
 * 业务暂存
 * </p>
 *
 * @author lee.
 * @since 2019-07-31
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@TableName("workflow_business_stash")
@ApiModel(value = "业务暂存", description = "业务暂存")
public class BusinessStash extends BaseEntity {

    @ApiModelProperty(value = "键", dataType = "String")
    @TableField("key_")
    private String key;

    @ApiModelProperty(value = "值", dataType = "String")
    @TableField("value_")
    private String value;

    @ApiModelProperty(value = "类型", dataType = "String")
    @TableField("type_")
    private String type;

    /**
     * 记录状态，0为禁用，1为启用，-1为已删除
     */
    @TableField(exist = false)
    @ApiModelProperty(value = "记录状态", dataType = "Integer", notes = "0为禁用，1为启用，-1为已删除", example = "1")
    private Integer enable;

    /**
     * 备注
     */
    @TableField(exist = false)
    @ApiModelProperty(value = "备注", dataType = "String")
    private String remark;

    /**
     * 数据记录创建者
     */
    @TableField(exist = false)
    @ApiModelProperty(value = "数据记录创建者", dataType = "String", hidden = true)
    private String createBy;

    /**
     * 数据记录创建时间
     */
    @TableField(exist = false)
    @ApiModelProperty(value = "数据记录创建时间", dataType = "Date", hidden = true)
    private Date createTime;

    /**
     * 数据记录更新者
     */
    @TableField(exist = false)
    @ApiModelProperty(value = "数据记录更新者", dataType = "String", hidden = true)
    private String updateBy;

    /**
     * 数据记录更新时间
     */
    @TableField(exist = false)
    @ApiModelProperty(value = "数据记录更新时间", dataType = "Date", hidden = true)
    private Date updateTime;

    /**
     * 乐观锁字段
     */
    @TableField(exist = false)
    @ApiModelProperty(value = "乐观锁字段", dataType = "Integer", hidden = true)
    private Integer version;
}
