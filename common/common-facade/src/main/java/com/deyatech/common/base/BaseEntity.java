package com.deyatech.common.base;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * 基础类
 *
 * @author: lee.
 * @since: 2018-12-13 18:12
 */
@Data
@Accessors(chain = true)
@ApiModel
public class BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID，生成规则为分布式id
     */
    @TableId(value = "id_", type = IdType.ID_WORKER_STR)
    @ApiModelProperty(value = "主键ID", dataType = "String")
    private String id;

    /**
     * 记录状态，0为禁用，1为启用，-1为已删除
     */
    @TableLogic
    @TableField("enable_")
    @ApiModelProperty(value = "记录状态", dataType = "Integer", notes = "0为禁用，1为启用，-1为已删除", example = "1")
    private Integer enable;

    /**
     * 备注
     */
    @TableField("remark_")
    @ApiModelProperty(value = "备注", dataType = "String")
    private String remark;

    /**
     * 数据记录创建者
     */
    @TableField(value = "create_by", fill = FieldFill.INSERT)
    @ApiModelProperty(value = "数据记录创建者", dataType = "String", hidden = true)
    private String createBy;

    /**
     * 数据记录创建时间
     */
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    @ApiModelProperty(value = "数据记录创建时间", dataType = "Date", hidden = true)
    private Date createTime;

    /**
     * 数据记录更新者
     */
    @TableField(value = "update_by", fill = FieldFill.UPDATE)
    @ApiModelProperty(value = "数据记录更新者", dataType = "String", hidden = true)
    private String updateBy;

    /**
     * 数据记录更新时间
     */
    @ApiModelProperty(value = "数据记录更新时间", dataType = "Date", hidden = true)
    @TableField(value = "update_time", fill = FieldFill.UPDATE)

    private Date updateTime;

    /**
     * 乐观锁字段
     */
    @Version
    @TableField("version_")
    @ApiModelProperty(value = "乐观锁字段", dataType = "Integer", hidden = true)
    private Integer version;

    /**
     * 当前页码
     */
    @TableField(exist = false)
    @ApiModelProperty(value = "当前页码", dataType = "Long", example = "1")
    private Long page;

    /**
     * 每页条数
     */
    @TableField(exist = false)
    @ApiModelProperty(value = "每页条数", dataType = "Long", example = "1")
    private Long size;

    /**
     * 排序sql
     */
    @TableField(exist = false)
    @ApiModelProperty(value = "排序sql", dataType = "String")
    private String sortSql;
}
