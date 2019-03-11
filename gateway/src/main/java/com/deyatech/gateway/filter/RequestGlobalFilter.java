package com.deyatech.gateway.filter;


import cn.hutool.core.util.StrUtil;
import com.deyatech.common.Constants;
import com.deyatech.common.utils.AesUtil;
import com.deyatech.gateway.swagger.SwaggerProvider;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.springframework.cloud.gateway.support.ServerWebExchangeUtils.GATEWAY_REQUEST_URL_ATTR;

/**
 * <p>
 * 全局拦截器，处理swagger生成的接口url错误
 * <p>
 *
 * @author: lee.
 * @since: 2019/3/1 19:17
 */
@Component
public class RequestGlobalFilter implements GlobalFilter, Ordered {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        String rawPath = request.getURI().getRawPath();
        if (StrUtil.endWithIgnoreCase(rawPath, SwaggerProvider.API_URI)) {
            // 处理swagger生成的接口url错误
            Stream<String> stream = Arrays.stream(StringUtils.tokenizeToStringArray(rawPath, "/"));
            String first = stream.findFirst().get();
            String newPath = "/" + first + "/" + Arrays.stream(StringUtils.tokenizeToStringArray(rawPath, "/"))
                    .skip(2L).collect(Collectors.joining("/"));
            ServerHttpRequest newRequest = request.mutate()
                    .path(newPath)
                    .build();
            exchange.getAttributes().put(GATEWAY_REQUEST_URL_ATTR, newRequest.getURI());
            request = newRequest;
        }
        return chain.filter(exchange.mutate().request(request.mutate().headers(httpHeaders -> httpHeaders.add(Constants.GATEWAY_HEADER, AesUtil.aesEncrypt(Constants.GATEWAY_VALUE))).build()).build());
    }

    @Override
    public int getOrder() {
        return -1000;
    }

}