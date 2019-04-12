package com.deyatech.admin;

import com.deyatech.common.exception.GlobalExceptionHandler;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * <p>
 * admin模块微服务启动类
 * </p>
 *
 * @author: lee.
 * @since 2018-12-21
 */
@EnableDiscoveryClient
@SpringBootApplication
@MapperScan("com.deyatech.**.mapper")
@ComponentScan("com.deyatech")
@ServletComponentScan("com.deyatech.common.filter")
@EnableSwagger2
@EnableCaching
public class AdminServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(AdminServiceApplication.class, args);
    }

    @Bean
    GlobalExceptionHandler getGlobalExceptionHandler() {
        return new GlobalExceptionHandler();
    }
}

