package com.deyatech.common.wechat;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.boot.context.properties.ConfigurationProperties;

import static com.deyatech.common.wechat.WeChatConfig.PREFIX;

/**
 * <p>
 * 微信配置类
 * </p>
 *
 * @author: lee.
 * @since: 2018-12-14 16:17
 */
@Data
@EqualsAndHashCode
@Accessors(chain = true)
@ConfigurationProperties(prefix = PREFIX)
public class WeChatConfig {
    public static final String PREFIX = "we-chat";

    /**
     * 设置微信公众号的appid
     */
    private String appId;

    /**
     * 设置微信公众号的app secret
     */
    private String secret;

    /**
     * 设置微信公众号的token
     */
    private String token;

    /**
     * 设置微信公众号的EncodingAESKey
     */
    private String aesKey;


}
