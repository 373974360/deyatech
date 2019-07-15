package com.deyatech.message.feign.impl;

import com.deyatech.common.Constants;
import com.deyatech.common.entity.RestResult;
import com.deyatech.message.feign.MessageFeign;
import com.deyatech.message.vo.MessageVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Description MqserverFeign实现类
 * @Author yxz
 * @Date 2019/4/23
 */
@Slf4j
@RestController
public class MessageFeignImpl implements MessageFeign {

    @Autowired
    private AmqpTemplate rabbitmqTemplate;

    @Qualifier("fanoutExchangeRing")
    @Autowired
    private FanoutExchange fanoutExchangeRing;

    @Qualifier("fanoutExchangeWindow")
    @Autowired
    private FanoutExchange fanoutExchangeWindow;

    @Qualifier("fanoutExchangeScreen")
    @Autowired
    private FanoutExchange fanoutExchangeScreen;

    @Qualifier("fanoutExchangeComment")
    @Autowired
    private FanoutExchange fanoutExchangeComment;

    @Qualifier("systemTopicExchange")
    @Autowired
    private TopicExchange systemTopicExchange;

    @Override
    public RestResult sendToRing(@RequestBody List<MessageVo> msg) {
        log.info("sendRing:" +msg);
        this.rabbitmqTemplate.convertAndSend(fanoutExchangeRing.getName(),"", msg);
        return RestResult.ok();
    }

    @Override
    public RestResult sendToWindow(@RequestBody List<MessageVo> msg) {
        log.info("sendWindow:" +msg);
        this.rabbitmqTemplate.convertAndSend(fanoutExchangeWindow.getName(),"", msg);
        return RestResult.ok();
    }

    @Override
    public RestResult sendToScreen(@RequestBody List<MessageVo> msg) {
        log.info("sendScreen:" +msg);
        this.rabbitmqTemplate.convertAndSend(fanoutExchangeScreen.getName(),"", msg);
        return RestResult.ok();
    }

    @Override
    public RestResult sendToComment(@RequestBody List<MessageVo> msg) {
        log.info("sendComment:" +msg);
        this.rabbitmqTemplate.convertAndSend(fanoutExchangeComment.getName(),"", msg);
        return RestResult.ok();
    }

    @Override
    public RestResult mailboxReserveHand(String workNo) {
        this.rabbitmqTemplate.convertAndSend(systemTopicExchange.getName(), Constants.QUEUE_MAILBOX_RESERVE_HAND, workNo);
        return RestResult.ok();
    }

    @Override
    public RestResult mailboxReserveTake(String workNo) {
        this.rabbitmqTemplate.convertAndSend(systemTopicExchange.getName(), Constants.QUEUE_MAILBOX_RESERVE_TAKE, workNo);
        return RestResult.ok();
    }

    @Override
    public RestResult expressRequestHand(String workNo) {
        this.rabbitmqTemplate.convertAndSend(systemTopicExchange.getName(), Constants.QUEUE_EMS_WAYBILL_GOT_HAND_REQUEST, workNo);
        return RestResult.ok();
    }

    @Override
    public RestResult mailboxUpdate(String mailboxOrderJson) {
        this.rabbitmqTemplate.convertAndSend(systemTopicExchange.getName(), Constants.QUEUE_MAILBOX_UPDATE, mailboxOrderJson);
        return RestResult.ok();
    }

    @Override
    public RestResult expressUpdate(String expressOrderJson) {
        this.rabbitmqTemplate.convertAndSend(systemTopicExchange.getName(), Constants.QUEUE_EXPRESS_UPDATE, expressOrderJson);
        return RestResult.ok();
    }

    @Override
    public RestResult logisticsUpdate(String logisticsJson) {
        this.rabbitmqTemplate.convertAndSend(systemTopicExchange.getName(), Constants.QUEUE_LOGISTICS_UPDATE, logisticsJson);
        return RestResult.ok();
    }
}
