package org.land.gateway.filter;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpStatus;
import lombok.extern.slf4j.Slf4j;
import org.land.common.Constants;
import org.land.common.exception.BusinessException;
import org.land.common.jwt.JwtInfo;
import org.land.common.jwt.JwtUtil;
import org.land.gateway.config.JwtConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpCookie;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.server.ServerWebExchange;

/**
 * <p>
 * 后台用户token验证过滤器
 * </p>
 *
 * @author: lee.
 * @since: 2018-12-21 11:49
 */
@Slf4j
@Component
public class UserTokenCheckGatewayFilterFactory extends AbstractGatewayFilterFactory {

    @Autowired
    JwtConfig jwtConfig;

    @Autowired
    RedisTemplate redisTemplate;

    @Override
    public GatewayFilter apply(Object config) {
        return (exchange, chain) -> {
            String userToken = getToken(exchange);
            JwtInfo jwtInfo;
            if (StrUtil.isBlank(userToken)) {
                throw new BusinessException(HttpStatus.HTTP_INTERNAL_ERROR, "登录信息过期，请重新登录！");
            } else {
                jwtInfo = JwtUtil.getInfoFromToken(userToken, jwtConfig.getPubKeyPath());
                Object invlideToken = redisTemplate.opsForValue().get(Constants.TOKEN.concat(jwtInfo.getId()));
                if (ObjectUtil.isNotNull(invlideToken) && userToken.equals(invlideToken.toString())) {
                    throw new BusinessException(HttpStatus.HTTP_INTERNAL_ERROR, "登录信息过期，请重新登录！");
                } else {
                    ServerHttpRequest request = exchange.getRequest().mutate().header(Constants.CONTEXT_KEY_USER_ID, jwtInfo.getId()).build();
                    exchange = exchange.mutate().request(request).build();
                    if (jwtInfo.getBuffer() <= jwtConfig.getBuffer()) {
                        exchange.getResponse().getHeaders().add(Constants.REFRESH_TOKEN, "true");
                    }
                }
            }
            return chain.filter(exchange);
        };
    }

    /**
     * 根据请求信息获取token
     *
     * @param exchange
     * @return
     */
    private String getToken(ServerWebExchange exchange) {
        String authToken = exchange.getRequest().getHeaders().getFirst(jwtConfig.getTokenHeader());
        if (StrUtil.isBlank(authToken)) {
            MultiValueMap<String, String> queryParams = exchange.getRequest().getQueryParams();
            authToken = queryParams.getFirst(jwtConfig.getTokenHeader());
        }
        if (StrUtil.isBlank(authToken)) {
            MultiValueMap<String, HttpCookie> cookies = exchange.getRequest().getCookies();
            HttpCookie cookie = cookies.getFirst(jwtConfig.getTokenHeader());
            authToken = cookie != null ? cookie
                    .getValue() : null;
        }
        return authToken;
    }
}
