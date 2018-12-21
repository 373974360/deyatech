package org.land.admin;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

/**
 * <p>
 * admin模块微服务启动类
 * </p>
 * @author: lee.
 * @since 2018-12-21
 */
@EnableDiscoveryClient
@SpringBootApplication
@MapperScan("org.land.**.mapper")
@ComponentScan("org.land")
public class AdminServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(AdminServiceApplication.class, args);
    }



}

