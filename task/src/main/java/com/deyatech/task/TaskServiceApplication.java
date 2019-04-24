package com.deyatech.task;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * <p>
 * task模块微服务启动类
 * </p>
 *
 * @author: yxz
 * @since 2019-4-24
 */

@EnableScheduling
@SpringBootApplication
public class TaskServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(TaskServiceApplication.class, args);
    }

}
