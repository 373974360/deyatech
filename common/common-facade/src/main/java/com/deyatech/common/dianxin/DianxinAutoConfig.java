package com.deyatech.common.dianxin;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static com.deyatech.common.dianxin.DianxinConfig.PREFIX;


/**
 * <p>
 * 电信webservice自动配置类
 * </p>
 *
 * @author yxz.
 * @since 2019-04-28
 */
@Configuration
@EnableConfigurationProperties(DianxinConfig.class)
@ConditionalOnProperty(prefix = PREFIX, name = "enabled", havingValue = "true")
public class DianxinAutoConfig {

    @Bean
    public DianxinUtil dianxinUtil(DianxinConfig dianxinConfig){
        return new DianxinUtil(dianxinConfig);
    }
}
