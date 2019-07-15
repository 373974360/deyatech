package com.deyatech.message.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * @Description 广播交换器相关配置，综合屏
 * @Author yxz
 * @Date 2019/4/22
 */
@Configuration
public class RabbitConfigScreen {

    /**
     * 定义（广播）交换器
     * @return
     */
    @Bean
    public FanoutExchange fanoutExchangeScreen() {
        return new FanoutExchange("fanout-exchange-screen");
    }

    /**
     * 定义自动删除匿名队列
     * @return
     */
    @Bean
    public Queue screenQueue() {
        return new AnonymousQueue();
    }


    /**
     *
     * @param fanoutExchangeScreen 广播交换器
     * @param screenQueue 自动删除队列
     * @return
     */
    @Bean
    public Binding bindingZong(FanoutExchange fanoutExchangeScreen, Queue screenQueue) {
        return BindingBuilder.bind(screenQueue).to(fanoutExchangeScreen);
    }


}
