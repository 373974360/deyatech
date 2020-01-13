package com.deyatech.workflow.config;

import cn.hutool.json.JSONUtil;
import com.deyatech.workflow.constant.ProcessConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

/**
 * 流程配置
 *
 * @author ycx
 * @since 2020-01-06
 */
@Slf4j
@Configuration
public class RabbitMQProcessConfig {
    /**
     * 交换器
     *
     * @return
     */
    @Bean
    public TopicExchange topicExchangeProcess() {
        return new TopicExchange(ProcessConstant.EXCHANGE_PROCESS);
    }

    /**
     * 队列
     *
     * @return
     */
    @Bean
    public Queue queueProcess() {
        return new Queue(ProcessConstant.QUEUE_PROCESS);
    }
    @Bean
    public Queue queueProcessFinish() {
        return new Queue(ProcessConstant.QUEUE_PROCESS_FINISH);
    }
    @Bean
    public Queue queueProcessReject() {
        return new Queue(ProcessConstant.QUEUE_PROCESS_REJECT);
    }
//    @Bean
//    public Queue queueProcessCancel() {
//        return new Queue(ProcessConstant.QUEUE_PROCESS_DELETE);
//    }

    /**
     * 绑定
     *
     * @return
     */
    @Bean
    public Binding bindingProcess() {
        return BindingBuilder.bind(queueProcess()).to(topicExchangeProcess()).with(ProcessConstant.ROUTING_KEY_PROCESS);
    }
    @Bean
    public Binding bindingProcessFinish() {
        return BindingBuilder.bind(queueProcessFinish()).to(topicExchangeProcess()).with(ProcessConstant.ROUTING_KEY_PROCESS_FINISH);
    }
    @Bean
    public Binding bindingProcessReject() {
        return BindingBuilder.bind(queueProcessReject()).to(topicExchangeProcess()).with(ProcessConstant.ROUTING_KEY_PROCESS_REJECT);
    }
//    @Bean
//    public Binding bindingProcessCancel() {
//        return BindingBuilder.bind(queueProcessCancel()).to(topicExchangeProcess()).with(ProcessConstant.ROUTING_KEY_PROCESS_DELETE);
//    }

    /**
     * 流程处理监听
     *
     * @param param
     */
    @RabbitListener(queues = ProcessConstant.QUEUE_PROCESS)
    public void contentFinish(Map<String,Object> param) {
        log.info(String.format("流程处理参数：%s", JSONUtil.toJsonStr(param)));
    }
}
