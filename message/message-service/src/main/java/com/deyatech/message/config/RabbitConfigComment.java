package com.deyatech.message.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * @Description 广播交换器相关配置，评价器
 * @Author yxz
 * @Date 2019/4/22
 */
@Configuration
public class RabbitConfigComment {

    /**
     * 定义（广播）交换器
     * @return
     */
    @Bean
    public FanoutExchange fanoutExchangeComment() {
        return new FanoutExchange("fanout-exchange-comment");
    }

    /**
     * 定义自动删除匿名队列
     * @return
     */
    @Bean
    public Queue commentQueue() {
        return new AnonymousQueue();
    }


    /**
     *
     * @param fanoutExchangeComment 广播交换器
     * @param commentQueue 自动删除队列
     * @return
     */
    @Bean
    public Binding bindingComment(FanoutExchange fanoutExchangeComment, Queue commentQueue) {
        return BindingBuilder.bind(commentQueue).to(fanoutExchangeComment);
    }


}
