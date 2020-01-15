package com.deyatech.admin.config;

import com.deyatech.alibaba.platform.AlibabaPlatForm;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author doukang
 * @description 阿里接口配置
 * @date 2019/10/17 10:00
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "alibaba")
public class AlibabaConfig {

    private String appId;

    private String appSecret;

    private String apiHost;

    @Bean
    public AlibabaPlatForm alibabaPlatForm() {
        return new AlibabaPlatForm(appId, appSecret, apiHost);
    }
}
