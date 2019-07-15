package com.deyatech.common.aliyun;

import cn.hutool.json.JSONUtil;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dyvmsapi.model.v20170525.SingleCallByTtsRequest;
import com.aliyuncs.dyvmsapi.model.v20170525.SingleCallByTtsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.deyatech.common.submail.SubMailMessage;
import lombok.extern.slf4j.Slf4j;

/**
 * <p>
 * 阿里云语音发送工具类
 * </p>
 *
 * @author lee.
 * @since 2019-03-07
 */
@Slf4j
public class AliyunUtil {

    public AliyunUtil(AliyunConfig aliyunConfig) {
        this.aliyunConfig = aliyunConfig;
    }

    private AliyunConfig aliyunConfig;

    public SingleCallByTtsResponse sendVoice(SubMailMessage subMailMessage, String ttsCode) {
        //设置访问超时时间
        System.setProperty("sun.net.client.defaultConnectTimeout", "180000");
        System.setProperty("sun.net.client.defaultReadTimeout", "180000");
        //云通信产品-语音API服务产品名称（产品名固定，无需修改）
        final String product = aliyunConfig.getVoiceProduct();
        //产品域名（接口地址固定，无需修改）
        final String domain = aliyunConfig.getVoiceSendUrl();
//        AK信息
        final String accessKeyId = aliyunConfig.getVoiceAccessKeyId();
        final String accessKeySecret = aliyunConfig.getVoiceAppKey();
        final String calledShowNumber = aliyunConfig.getCalledShowNumber();

        //初始化acsClient 暂时不支持多region
        IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId, accessKeySecret);
        try {
            DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product, domain);
        } catch (ClientException e) {
            e.printStackTrace();
        }
        IAcsClient acsClient = new DefaultAcsClient(profile);
        SingleCallByTtsRequest request = new SingleCallByTtsRequest();
        //必填-被叫显号,可在语音控制台中找到所购买的显号
        request.setCalledShowNumber(calledShowNumber);
        //必填-被叫号码
        request.setCalledNumber(subMailMessage.getTo());
        //必填-Tts模板ID
        request.setTtsCode(ttsCode);

        String multi = JSONUtil.toJsonStr(subMailMessage.getVars());
        log.info("阿里云语音请求数据：" + multi);

        //可选-当模板中存在变量时需要设置此值
        request.setTtsParam(multi);
        //可选-音量 取值范围 0--200
        request.setVolume(100);
        //可选-播放次数
        request.setPlayTimes(2);
        //可选-外部扩展字段,此ID将在回执消息中带回给调用方
        request.setOutId("yourOutId");
        //hint 此处可能会抛出异常，注意catch
        SingleCallByTtsResponse singleCallByTtsResponse = null;
        try {
            if (aliyunConfig.isMock()) {
                log.info(String.format("模拟发送阿里云语音[%s]", JSONUtil.toJsonStr(request)));
            } else {
                singleCallByTtsResponse = acsClient.getAcsResponse(request);
            }
        } catch (ClientException e) {
            e.printStackTrace();
            log.error("阿里语音发送异常", e);
        }
        if (singleCallByTtsResponse.getCode() != null && "OK".equals(singleCallByTtsResponse.getCode())) {
            //请求成功
            log.info("语音文本外呼---------------");
            log.info("RequestId=" + singleCallByTtsResponse.getRequestId());
            log.info("Code=" + singleCallByTtsResponse.getCode());
            log.info("Message=" + singleCallByTtsResponse.getMessage());
            log.info("CallId=" + singleCallByTtsResponse.getCallId());
        }
        return singleCallByTtsResponse;
    }
}
