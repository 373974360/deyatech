package com.deyatech.common.wechat;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.bean.WxJsapiSignature;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.result.WxMpOAuth2AccessToken;
import me.chanjar.weixin.mp.bean.result.WxMpUser;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateData;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateMessage;

/**
 * <p>
 * 微信接口工具类
 * </p>
 *
 * @author: lee.
 * @since: 2018-12-14 16:17
 */
@Slf4j
@Data
public class WeChatUtil {

    public WeChatUtil(WxMpService wxMpService) {
        this.wxMpService = wxMpService;
    }

    private WxMpService wxMpService;

    /**
     * 获取jsapi签名
     *
     * @param url
     * @return
     */
    public WxJsapiSignature jsApiSignature(String url) {
        log.info(StrUtil.format("微信hall传过来的url：%s", url));
        try {
            WxJsapiSignature jsapiSignature = wxMpService.createJsapiSignature(url);
            return jsapiSignature;
        } catch (WxErrorException e) {
            e.printStackTrace();
            log.error(StrUtil.format("获取失败：%s", JSONUtil.toJsonStr(e)));
            return null;
        }
    }

    /**
     * 根据code获取accesstoken
     *
     * @param code
     * @return
     */
    public WxMpOAuth2AccessToken oauth2AccessToken(String code) {
        log.info(StrUtil.format("微信hall传过来的code：%s", code));
        try {
            WxMpOAuth2AccessToken wxMpOAuth2AccessToken = wxMpService.oauth2getAccessToken(code);
            return wxMpOAuth2AccessToken;
        } catch (WxErrorException e) {
            e.printStackTrace();
            log.error(StrUtil.format("获取失败：%s", JSONUtil.toJsonStr(e)));
            return null;
        }
    }

    /**
     * 根据openId和access_token获取用户信息
     *
     * @param
     * @return
     */
    public WxMpUser oauth2getUserInfo(WxMpOAuth2AccessToken wxMpOAuth2AccessToken, String lang) {
        log.info(StrUtil.format("微信传过来的wxMpOAuth2AccessToken：%s", JSONUtil.toJsonStr(wxMpOAuth2AccessToken)));
        try {
            WxMpUser wxMpUser = wxMpService.getUserService().userInfo(wxMpOAuth2AccessToken.getOpenId(), lang);
            return wxMpUser;
        } catch (WxErrorException e) {
            e.printStackTrace();
            log.error(StrUtil.format("获取用户信息失败：%s", JSONUtil.toJsonStr(e)));
            return null;
        }
    }

    /**
     * 发送微信模板消息
     *
     * @param weChatTemplateParam
     * @return
     */
    public String sendTemplateMessage(WeChatTemplateParam weChatTemplateParam) {
        WxMpTemplateMessage templateMessage = WxMpTemplateMessage.builder()
                .toUser(weChatTemplateParam.getToUser())
                .templateId(weChatTemplateParam.getTemplateId())
                .url(weChatTemplateParam.getUrl())
                .build();
        if (CollectionUtil.isNotEmpty(weChatTemplateParam.getDataList())) {
            for (WxMpTemplateData wxMpTemplateData : weChatTemplateParam.getDataList()) {
                templateMessage.addData(wxMpTemplateData);
            }
        }
        try {
            String s = wxMpService.getTemplateMsgService().sendTemplateMsg(templateMessage);
            log.info("******************:"+s);
            return s;
        } catch (WxErrorException e) {
            log.info("******************"+String.valueOf(e));
            log.error(StrUtil.format("发送模板消息失败[%s]", JSONUtil.toJsonStr(templateMessage)));
            e.printStackTrace();
            return null;
        }
    }
}
