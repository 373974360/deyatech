package com.deyatech.message.config;

import com.deyatech.common.Constants;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author doukang
 * @description 系统通知配置
 * @date 2019/6/25 14:24
 */
@Configuration
public class RabbitConfigSystem {

    @Bean
    public TopicExchange systemTopicExchange() {
        return new TopicExchange("topic-exchange-system");
    }

    /**
     * 信包箱预约-交件
     * @return
     */
    @Bean
    public Queue mailboxReserveHandQueue() {
        return new Queue(Constants.QUEUE_MAILBOX_RESERVE_HAND);
    }

    /**
     * 信包箱预约-取件
     * @return
     */
    @Bean
    public Queue mailboxReserveTakeQueue() {
        return new Queue(Constants.QUEUE_MAILBOX_RESERVE_TAKE);
    }

    /**
     * EMS上门取件请求
     * @return
     */
    @Bean
    public Queue emsWaybillGotHandRequestQueue() {
        return new Queue(Constants.QUEUE_EMS_WAYBILL_GOT_HAND_REQUEST);
    }

    /**
     * 信包箱订单状态更新
     * @return
     */
    @Bean
    public Queue mailboxUpdateQueue() {
        return new Queue(Constants.QUEUE_MAILBOX_UPDATE);
    }

    /**
     * 速递订单状态更新
     * @return
     */
    @Bean
    public Queue expressUpdateQueue() {
        return new Queue(Constants.QUEUE_EXPRESS_UPDATE);
    }

    /**
     * 物流已签收
     * @return
     */
    @Bean
    public Queue logisticsUpdateQueue() {
        return new Queue(Constants.QUEUE_LOGISTICS_UPDATE);
    }

    @Bean
    public Binding bindingHandMailboxReserve(TopicExchange systemTopicExchange, Queue mailboxReserveHandQueue) {
        return BindingBuilder.bind(mailboxReserveHandQueue).to(systemTopicExchange).with(Constants.QUEUE_MAILBOX_RESERVE_HAND);
    }

    @Bean
    public Binding bindingTakeMailboxReserve(TopicExchange systemTopicExchange, Queue mailboxReserveTakeQueue) {
        return BindingBuilder.bind(mailboxReserveTakeQueue).to(systemTopicExchange).with(Constants.QUEUE_MAILBOX_RESERVE_TAKE);
    }

    @Bean
    public Binding bindingEmsWaybillGotHandRequest(TopicExchange systemTopicExchange, Queue emsWaybillGotHandRequestQueue) {
        return BindingBuilder.bind(emsWaybillGotHandRequestQueue).to(systemTopicExchange).with(Constants.QUEUE_EMS_WAYBILL_GOT_HAND_REQUEST);
    }

    @Bean
    public Binding bindingMailboxUpdate(TopicExchange systemTopicExchange, Queue mailboxUpdateQueue) {
        return BindingBuilder.bind(mailboxUpdateQueue).to(systemTopicExchange).with(Constants.QUEUE_MAILBOX_UPDATE);
    }

    @Bean
    public Binding bindingExpressUpdate(TopicExchange systemTopicExchange, Queue expressUpdateQueue) {
        return BindingBuilder.bind(expressUpdateQueue).to(systemTopicExchange).with(Constants.QUEUE_EXPRESS_UPDATE);
    }

    @Bean
    public Binding bindingLogisticsUpdate(TopicExchange systemTopicExchange, Queue logisticsUpdateQueue) {
        return BindingBuilder.bind(logisticsUpdateQueue).to(systemTopicExchange).with(Constants.QUEUE_LOGISTICS_UPDATE);
    }
}
