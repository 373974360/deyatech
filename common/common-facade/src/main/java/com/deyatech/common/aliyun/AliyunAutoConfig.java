package com.deyatech.common.aliyun;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static org.springframework.cloud.commons.util.InetUtilsProperties.PREFIX;


/**
 * <p>
 * 阿里云语音自动配置类
 * </p>
 *
 * @author lee.
 * @since 2019-03-07
 */
@Configuration
@EnableConfigurationProperties(AliyunConfig.class)
@ConditionalOnProperty(prefix = PREFIX, name = "enabled", havingValue = "true")
public class AliyunAutoConfig {

    @Bean
    public AliyunUtil aliyunUtil(AliyunConfig aliyunConfig){
        return new AliyunUtil(aliyunConfig);
    }
}
