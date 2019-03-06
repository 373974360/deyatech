package com.deyatech.admin;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * <p>
 * admin模块微服务启动类
 * </p>
 * @author: lee.
 * @since 2018-12-21
 */
@EnableDiscoveryClient
@SpringBootApplication
@MapperScan("com.deyatech.**.mapper")
@ComponentScan("com.deyatech")
@EnableSwagger2
public class AdminServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(AdminServiceApplication.class, args);
    }



}

