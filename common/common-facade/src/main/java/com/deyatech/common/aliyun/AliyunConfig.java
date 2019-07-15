package com.deyatech.common.aliyun;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.boot.context.properties.ConfigurationProperties;

import static com.deyatech.common.aliyun.AliyunConfig.PREFIX;

/**
 * <p>
 * 阿里云配置类
 * </p>
 *
 * @author lee.
 * @since 2019-03-07
 */
@Data
@EqualsAndHashCode
@Accessors(chain = true)
@ConfigurationProperties(prefix = PREFIX)
public class AliyunConfig {

    public static final String PREFIX = "aliyun";

    /**
     * 云通信产品-语音API服务产品名称（产品名固定，无需修改）
     */
    private  String voiceProduct;

    /**
     * 产品域名（接口地址固定，无需修改）
     */
    private String voiceSendUrl;

    /**
     * AccessKey
     */
    private String voiceAccessKeyId;

    /**
     * AppKey
     */
    private String voiceAppKey;

    /**
     * 被叫显号
     */
    private String calledShowNumber;

    private boolean mock;
}
