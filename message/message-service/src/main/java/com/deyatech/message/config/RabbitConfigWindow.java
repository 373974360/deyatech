package com.deyatech.message.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * @Description 广播交换器相关配置，窗口屏
 * @Author yxz
 * @Date 2019/4/22
 */
@Configuration
public class RabbitConfigWindow {

    /**
     * 定义（广播）交换器
     * @return
     */
    @Bean
    public FanoutExchange fanoutExchangeWindow() {
        return new FanoutExchange("fanout-exchange-window");
    }

    /**
     * 定义自动删除匿名队列
     * @return
     */
    @Bean
    public Queue windowQueue() {
        return new AnonymousQueue();
    }


    /**
     *
     * @param fanoutExchangeWindow 广播交换器
     * @param windowQueue 自动删除队列
     * @return
     */
    @Bean
    public Binding bindingWin(FanoutExchange fanoutExchangeWindow, Queue windowQueue) {
        return BindingBuilder.bind(windowQueue).to(fanoutExchangeWindow);
    }


}
