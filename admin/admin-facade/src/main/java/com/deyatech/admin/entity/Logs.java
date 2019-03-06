package com.deyatech.admin.entity;

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
 * 系统数据字典索引信息
 * </p>
 *
 * @author lee.
 * @since 2019-03-05
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("admin_logs")
@ApiModel(value="系统数据字典索引信息对象", description="系统数据字典索引信息" ,parent = BaseEntity.class)
public class Logs extends BaseEntity {

    @ApiModelProperty(value = "执行方法说明",dataType = "String")
    @TableField("notes_")
    private String notes;

    @ApiModelProperty(value = "执行的类跟方法",dataType = "String")
    @TableField("method_")
    private String method;

    @ApiModelProperty(value = "请求url",dataType = "String")
    @TableField("request_url")
    private String requestUrl;

    @ApiModelProperty(value = "用户id",dataType = "String")
    @TableField("user_id")
    private String userId;

    @ApiModelProperty(value = "请求参数",dataType = "String")
    @TableField("params_")
    private String params;

    @ApiModelProperty(value = "消耗时间  毫秒",dataType = "Long", example = "1")
    @TableField("time_")
    private Long time;

    @ApiModelProperty(value = "请求者ip地址",dataType = "String")
    @TableField("ip_")
    private String ip;

}
