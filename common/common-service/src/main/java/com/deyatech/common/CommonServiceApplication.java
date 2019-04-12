package com.deyatech.common;

import com.deyatech.common.exception.GlobalExceptionHandler;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * <p>
 * common模块微服务启动类
 * </p>
 *
 * @author: lee.
 * @since 2018-12-21
 */
@SpringBootApplication
@EnableDiscoveryClient
@ComponentScan("com.deyatech")
@EnableSwagger2
@EnableFeignClients({"com.deyatech.*.feign"})
@ServletComponentScan("com.deyatech.common.filter")
public class CommonServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(CommonServiceApplication.class, args);
    }

    @Bean
    GlobalExceptionHandler getGlobalExceptionHandler() {
        return new GlobalExceptionHandler();
    }
}
