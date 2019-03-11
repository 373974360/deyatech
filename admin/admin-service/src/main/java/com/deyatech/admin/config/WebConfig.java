package com.deyatech.admin.config;

import com.deyatech.common.exception.GlobalExceptionHandler;
import com.deyatech.common.interceptor.AuthRestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

/**
 * <p>
 * web配置，添加全局异常处理器以及请求拦截器
 * </p>
 *
 * @author: lee.
 * @since 2018-12-21
 */
@Configuration
public class WebConfig extends WebMvcConfigurationSupport {
    @Bean
    GlobalExceptionHandler getGlobalExceptionHandler() {
        return new GlobalExceptionHandler();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(getAuthRestInterceptor()).addPathPatterns("/**");
        super.addInterceptors(registry);
    }

    @Bean
    AuthRestInterceptor getAuthRestInterceptor() {
        return new AuthRestInterceptor();
    }
}
