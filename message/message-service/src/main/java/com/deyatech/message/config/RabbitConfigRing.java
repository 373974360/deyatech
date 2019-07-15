package com.deyatech.message.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * @Description 广播交换器相关配置，呼叫器
 * @Author yxz
 * @Date 2019/4/22
 */
@Configuration
public class RabbitConfigRing {

    /**
     * 定义（广播）交换器
     * @return
     */
    @Bean
    public FanoutExchange fanoutExchangeRing() {
        return new FanoutExchange("fanout-exchange-ring");
    }

    /**
     * 定义自动删除匿名队列
     * @return
     */
    @Bean
    public Queue ringQueue() {
        return new AnonymousQueue();
    }


    /**
     *
     * @param fanoutExchangeRing 广播交换器
     * @param ringQueue 自动删除队列
     * @return
     */
    @Bean
    public Binding bindingNumber(FanoutExchange fanoutExchangeRing, Queue ringQueue) {
        return BindingBuilder.bind(ringQueue).to(fanoutExchangeRing);
    }


}
