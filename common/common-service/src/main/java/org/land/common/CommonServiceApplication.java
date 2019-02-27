package org.land.common;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
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
@ComponentScan("org.land")
@EnableSwagger2
public class CommonServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(CommonServiceApplication.class, args);
    }
}
