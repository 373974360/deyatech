package com.deyatech.message.handler;


import cn.hutool.json.JSONUtil;
import com.deyatech.accept.feign.AcceptFeign;
import com.deyatech.common.Constants;
import com.deyatech.common.model.Logs;
import com.deyatech.message.vo.MessageVo;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;


/**
 * @Description 接收rabbitmq消息，并且发送给对应的WebSocket
 * @Author yxz
 * @Date 2019/4/22
 */
@Slf4j
@Component
public class ReceiveHandler {

    /**
     * 呼叫器前端web页面订阅WebSocket地址
     */
    private final String ringWebSocketTopic = "/topic/ring/";

    /**
     * 窗口屏器前端web页面订阅WebSocket地址
     */
    private final String windowWebSocketTopic = "/topic/window/";

    /**
     * 综合屏前端web页面订阅WebSocket地址
     */
    private final String screenWebSocketTopic = "/topic/screen/";

    /**
     * 评价器前端web页面订阅WebSocket地址
     */
    private final String commentWebSocketTopic = "/topic/comment/";

    /**
     * 系统日志前端web页面订阅WebSocket地址
     */
    private final String logsWebSocketTopic = "/topic/logs/";

    @Autowired
    private SimpMessagingTemplate messagingTemplate;
    @Autowired
    private AcceptFeign acceptFeign;

    /**
     * 接受rabbitmq广播的呼叫器消息
     *
     * @param msg
     */
    @RabbitListener(queues = "#{ringQueue.name}")
    public void receiveRing(List<MessageVo> msg) {
        log.info("receiveRing:" + JSONUtil.toJsonStr(msg));
        sendToWebSocket(ringWebSocketTopic, msg);
    }

    /**
     * 接受rabbitmq广播的窗口屏消息
     *
     * @param msg
     */
    @RabbitListener(queues = "#{windowQueue.name}")
    public void receiveWindow(List<MessageVo> msg) {
        log.info("receiveWindow:" + JSONUtil.toJsonStr(msg));
        sendToWebSocket(windowWebSocketTopic, msg);
    }

    /**
     * 接受rabbitmq广播的综合屏消息
     *
     * @param msg
     */
    @RabbitListener(queues = "#{screenQueue.name}")
    public void receiveScreen(List<MessageVo> msg) {
        log.info("receiveScreen:" + JSONUtil.toJsonStr(msg));
        sendToWebSocket(screenWebSocketTopic, msg);
    }

    /**
     * 接受rabbitmq广播的评价器消息
     *
     * @param msg
     */
    @RabbitListener(queues = "#{commentQueue.name}")
    public void receiveComment(List<MessageVo> msg) {
        log.info("receiveComment:" + JSONUtil.toJsonStr(msg));
        sendToWebSocket(commentWebSocketTopic, msg);
    }

    /**
     * 接受rabbitmq广播的评价器消息
     *
     * @param logs
     */
    @RabbitListener(queues = "logs-queue")
    public void receiveLogs(Logs logs) {
        messagingTemplate.convertAndSend(logsWebSocketTopic, logs);
    }

    @RabbitListener(queues = Constants.QUEUE_MAILBOX_RESERVE_HAND)
    public void mailboxReserveHand(String workNo, Channel channel, Message message) throws IOException {
        try {
            acceptFeign.mailboxReserveHand(workNo);
        } catch (Exception e) {
            log.error("信包箱预约失败：" + e.getMessage());
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        }
    }

    @RabbitListener(queues = Constants.QUEUE_MAILBOX_RESERVE_TAKE)
    public void mailboxReserveTake(String workNo, Channel channel, Message message) throws IOException {
        try {
            acceptFeign.mailboxReserveTake(workNo);
        } catch (Exception e) {
            log.error("信包箱预约失败：" + e.getMessage());
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        }
    }

    @RabbitListener(queues = Constants.QUEUE_EMS_WAYBILL_GOT_HAND_REQUEST)
    public void expressRequestHand(String workNo, Channel channel, Message message) throws IOException {
        try {
            acceptFeign.expressRequestHand(workNo);
        } catch (Exception e) {
            log.error("速递请求失败：" + e.getMessage());
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        }
    }

    @RabbitListener(queues = Constants.QUEUE_MAILBOX_UPDATE)
    public void mailboxUpdate(String mailboxOrderJson, Channel channel, Message message) throws IOException {
        try {
            acceptFeign.handleMailboxUpdate(mailboxOrderJson);
        } catch (Exception e) {
            log.error("信包箱订单状态更新后续处理失败：" + e.getMessage());
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        }
    }

    @RabbitListener(queues = Constants.QUEUE_EXPRESS_UPDATE)
    public void expressUpdate(String expressOrderJson, Channel channel, Message message) throws IOException {
        try {
            acceptFeign.handleExpressUpdate(expressOrderJson);
        } catch (Exception e) {
            log.error("速递订单状态更新后续处理失败：" + e.getMessage());
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        }
    }

    @RabbitListener(queues = Constants.QUEUE_LOGISTICS_UPDATE)
    public void logisticsUpdate(String logisticsJson, Channel channel, Message message) throws IOException {
        try {
            acceptFeign.handleLogisticsUpdate(logisticsJson);
        } catch (Exception e) {
            log.error("物流签收后续处理失败：" + e.getMessage());
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        }
    }

    /**
     * 获取json数据，并发送给WebSocket
     *
     * @param topicUrl
     * @param msg
     */
    private void sendToWebSocket(String topicUrl, List<MessageVo> msg) {
        for (MessageVo messageVo : msg) {
            messagingTemplate.convertAndSend(topicUrl.concat(messageVo.getAddr()), messageVo.getData());
        }
      /*  boolean isJsonArray = JSONUtil.isJsonArray(msg);
        if (isJsonArray) {
            List<MessageVo> messageVos = JSONUtil.toList(JSONUtil.parseArray(msg), MessageVo.class);
            if (CollectionUtil.isNotEmpty(messageVos)) {
                for (MessageVo messageVo : messageVos) {
                    messagingTemplate.convertAndSend(topicUrl.concat(messageVo.getAddr()), messageVo.getData());
                }
            }
        } else {
            MessageVo messageVo = JSONUtil.toBean(msg, MessageVo.class);
            if (ObjectUtil.isNotNull(messageVo)) {
                messagingTemplate.convertAndSend(topicUrl.concat(messageVo.getAddr()), messageVo.getData());
            }
        }*/
    }
}