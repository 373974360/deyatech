package org.land.common;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

/**
 * <p>
 * common模块微服务启动类
 * </p>
 * @author: lee.
 * @since 2018-12-21
 */
@SpringBootApplication
@EnableDiscoveryClient
@ComponentScan("org.land")
public class CommonServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(CommonServiceApplication.class, args);
    }
}
