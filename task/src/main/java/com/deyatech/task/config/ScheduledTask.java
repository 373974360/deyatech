package com.deyatech.task.config;


import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * <p>
 * 定时任务模块类
 * </p>
 *
 * @author: yxz
 * @since 2019-4-24
 */
@Slf4j
@Component
public class ScheduledTask {

    /**
     * 每隔5秒执行, 单位：ms。
     */
    @Scheduled(fixedRate = 10000)
    public void testFixRate() {
        System.out.println("每隔10秒执行一次：" + new Date());
    }

    /**
     *  //每天凌晨1:20执行
     */
    @Scheduled(cron = "0 20 1 * * ?")
    public void testMyBatis() {
        System.out.println("每天凌晨1:20点开始执行");
    }
}
