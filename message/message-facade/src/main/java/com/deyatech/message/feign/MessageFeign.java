package com.deyatech.message.feign;

import com.deyatech.common.entity.RestResult;
import com.deyatech.message.vo.MessageVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Description 消息队列服务feign接口
 * @Author yxz
 * @Date 2019/4/23
 */
@FeignClient(value = "message-service")
public interface MessageFeign {
    /**
     * 呼叫器消息发送接口
     *
     * @param msg
     * @return
     */
    @PostMapping("/feign/message/sendToRing")
    RestResult sendToRing(@RequestBody List<MessageVo> msg);

    /**
     * 窗口屏消息发送接口
     *
     * @param msg
     * @return
     */
    @PostMapping("/feign/message/sendToWindow")
    RestResult sendToWindow(@RequestBody List<MessageVo> msg);

    /**
     * 综合屏消息发送接口
     *
     * @param msg
     * @return
     */
    @PostMapping("/feign/message/sendToScreen")
    RestResult sendToScreen(@RequestBody List<MessageVo> msg);

    /**
     * 评价器消息发送接口
     *
     * @param msg
     * @return
     */
    @PostMapping("/feign/message/sendToComment")
    RestResult sendToComment(@RequestBody List<MessageVo> msg);

    /**
     * 交件信包箱预约
     *
     * @param workNo
     * @return
     */
    @RequestMapping(value = "/feign/message/mailboxReserveHand", method = RequestMethod.POST)
    RestResult mailboxReserveHand(@RequestParam("workNo") String workNo);

    /**
     * 取件信包箱预约
     *
     * @param workNo
     * @return
     */
    @RequestMapping(value = "/feign/message/mailboxReserveTake", method = RequestMethod.POST)
    RestResult mailboxReserveTake(@RequestParam("workNo") String workNo);

    /**
     * 交件速递请求
     *
     * @param workNo
     * @return
     */
    @RequestMapping(value = "/feign/message/expressRequestHand", method = RequestMethod.POST)
    RestResult expressRequestHand(@RequestParam("workNo") String workNo);

    /**
     * 信包箱订单状态更新
     *
     * @param mailboxOrderJson
     * @return
     */
    @RequestMapping(value = "/feign/message/mailboxUpdate", method = RequestMethod.POST)
    RestResult mailboxUpdate(@RequestParam("mailboxOrderJson") String mailboxOrderJson);

    /**
     * 速递订单状态更新
     *
     * @param expressOrderJson
     * @return
     */
    @RequestMapping(value = "/feign/message/expressUpdate", method = RequestMethod.POST)
    RestResult expressUpdate(@RequestParam("expressOrderJson") String expressOrderJson);

    /**
     * 物流状态更新
     *
     * @param logisticsJson
     * @return
     */
    @RequestMapping(value = "/feign/message/logisticsUpdate", method = RequestMethod.POST)
    RestResult logisticsUpdate(@RequestParam("logisticsJson") String logisticsJson);
}
