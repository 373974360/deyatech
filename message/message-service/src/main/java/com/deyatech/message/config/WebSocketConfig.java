package com.deyatech.message.config;


import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

/**
 * @Description 配置开启 webSocket
 * @Author yxz
 * @Date 2019/4/22
 */
@Configuration
@EnableWebSocket
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    public static final String END_POINT = "/websocket";

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        //允许使用socketJs方式访问，访问点为websocket，允许跨域
        registry.addEndpoint(END_POINT).setAllowedOrigins("*").withSockJS().setHeartbeatTime(5000)
                .setDisconnectDelay(3000).setStreamBytesLimit(512);
    }
}