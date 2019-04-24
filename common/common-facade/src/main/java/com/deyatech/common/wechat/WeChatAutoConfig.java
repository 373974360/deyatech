package com.deyatech.common.wechat;

import me.chanjar.weixin.mp.api.WxMpInMemoryConfigStorage;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.impl.WxMpServiceImpl;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static com.deyatech.common.wechat.WeChatConfig.PREFIX;

/**
 * <p>
 * 微信自动装配类
 * </p>
 *
 * @author: lee.
 * @since: 2018-12-14 16:17
 */
@Configuration
@EnableConfigurationProperties(WeChatConfig.class)
@ConditionalOnProperty(prefix = PREFIX, name = "enabled", havingValue = "true")
public class WeChatAutoConfig {

    @Bean
    public WeChatUtil weChatUtil(WeChatConfig weChatConfig) {
        WxMpService wxMpService = new WxMpServiceImpl();
        WxMpInMemoryConfigStorage wxMpInMemoryConfigStorage = new WxMpInMemoryConfigStorage();
        wxMpInMemoryConfigStorage.setAppId(weChatConfig.getAppId());
        wxMpInMemoryConfigStorage.setSecret(weChatConfig.getSecret());
        wxMpInMemoryConfigStorage.setToken(weChatConfig.getToken());
        wxMpInMemoryConfigStorage.setAesKey(weChatConfig.getAesKey());
        wxMpService.setWxMpConfigStorage(wxMpInMemoryConfigStorage);
        return new WeChatUtil(wxMpService);
    }
}
