package com.deyatech.common.submail;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static com.deyatech.common.submail.SubMailConfig.PREFIX;

/**
 * <p>
 * submail自动配置类
 * </p>
 *
 * @author lee.
 * @since 2019-03-07
 */
@Configuration
@EnableConfigurationProperties(SubMailConfig.class)
@ConditionalOnProperty(prefix = PREFIX, name = "enabled", havingValue = "true")
public class SubMailAutoConfig {

    @Bean
    public SubMailUtil subMailUtil(SubMailConfig submailConfig) {
        return new SubMailUtil(submailConfig);
    }
}
