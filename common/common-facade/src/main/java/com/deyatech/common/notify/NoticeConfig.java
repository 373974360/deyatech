package com.deyatech.common.notify;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;


/**
 * <p>
 * 抽号叫号通知方式配置类
 * </p>
 *
 * @author yxz
 * @since 2019-04-29
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "setting")
public class NoticeConfig {

    /**
     * 是否语音通知
     */
    private String voiceNotice;
    /**
     * 是否短信通知
     */
    private String smsNotice;
    /**
     * 是否微信通知
     */
    private String wechatNotice;
    /**
     * 是否支付宝通知
     */
    private String alipayNotice;
    /**
     * 语音通知当前用户模板ID
     */
    private String voiceCurrentTemplate;
    /**
     * 语音通知下一个用户模板ID
     */
    private String voiceNextTemplate;
    /**
     * 语音播放次数
     */
    private String voicePlayCount;
    /**
     * 抽号短信通知模板ID
     */
    private String smsGetNumber;
    /**
     * 叫号短信通知模板ID
     */
    private String smsCallNumber;
    /**
     * 抽号微信通知模板ID
     */
    private String wechatGetNumber;
    /**
     * 叫号微信通知模板ID
     */
    private String wechatCallNumber;
    /**
     * 预约通知模板ID
     */
    private String wechatOrderNumber;
    /**
     * 取消预约通知模板ID
     */
    private String wechatCancelNumber;
    /**
     * 微警认证消息通知模板
     */
    private String wechatAuthTemplate;
    /**
     * 预叫提醒消息通知模板
     */
    private String wechatPreCallNumber;


}
