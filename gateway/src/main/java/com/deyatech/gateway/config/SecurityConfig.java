package com.deyatech.gateway.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @Description: 第三方验证配置
 * @Author: songshanshan
 * @Date: 2019/8/6 10:24
 * @Version: 1.0
 * @Created in idea by autoCode
 */

@Data
@Configuration
@ConfigurationProperties(prefix = "security")
public class SecurityConfig {
    /**
     * appkey
     */
    private String appKey;
    /**
     * 密钥
     */
    private String appSecret;
    /**
     * 需验证的url
     */
    private String url;
}
