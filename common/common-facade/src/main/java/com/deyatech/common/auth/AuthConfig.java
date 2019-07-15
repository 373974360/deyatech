package com.deyatech.common.auth;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @Description: authenticate-service配置
 * @Author: songshanshan
 * @Date: 2019/5/27 14:05
 * @Version: 1.0
 * @Created in idea by autoCode
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "auth")
public class AuthConfig {
    /**
     * 公众号跳转认证ID
     */
    private String gzhClientId;
    /**
     * 公众号跳转认证密钥
     */
    private String gzhClientSecret;
    /**
     * APP认证ID
     */
    private String appClientId;
    /**
     * APP认证密钥
     */
    private String appClientSecret;
    /**
     * 扫码认证ID
     */
    private String scanClientId;
    /**
     * 扫码认证密钥
     */
    private String scanClientSecret;
    /**
     * 获取接入凭证
     */
    private String getAccessTokenUrl;
    /**
     * 获取认证凭证
     */
    private String getCertTokenUrl;
    /**
     * 获取认证记录
     */
    private String getAuthhistUrl;
    /**
     * 图片路径
     */
    private String pass;
    /**
     * 图片路径
     */
    private String nopass;
}
