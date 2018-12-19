package org.land.admin.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableField;
import org.land.common.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
/**
 * <p>
 * 系统数据字典索引信息
 * </p>
 *
 * @author lee.
 * @since 2018-12-19
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("admin_logs")
public class Logs extends BaseEntity {

    /**
    * 执行方法说明
    */
    @TableField("notes_")
    private String notes;

    /**
    * 执行的类跟方法
    */
    @TableField("method_")
    private String method;

    /**
    * 请求url
    */
    @TableField("request_url")
    private String requestUrl;

    /**
    * 用户id
    */
    @TableField("user_id")
    private String userId;

    /**
    * 请求参数
    */
    @TableField("params_")
    private String params;

    /**
    * 消耗时间  毫秒
    */
    @TableField("time_")
    private Long time;

    /**
    * 请求者ip地址
    */
    @TableField("ip_")
    private String ip;

}
