package com.deyatech.gateway.filter;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpStatus;
import cn.hutool.json.JSONUtil;
import com.deyatech.admin.feign.AdminFeign;
import com.deyatech.common.Constants;
import com.deyatech.common.entity.RestResult;
import com.deyatech.common.exception.BusinessException;
import com.deyatech.common.jwt.JwtInfo;
import com.deyatech.common.jwt.JwtUtil;
import com.deyatech.gateway.config.JwtConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpCookie;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * <p>
 * 前台用户token验证过滤器
 * </p>
 *
 * @author: lee.
 * @since: 2018-12-21 11:49
 */
@Slf4j
@Component
public class MemberTokenCheckGatewayFilterFactory extends AbstractGatewayFilterFactory {

    @Autowired
    JwtConfig jwtConfig;

    @Autowired
    RedisTemplate redisTemplate;

    @Autowired
    AdminFeign adminFeign;

    @Override
    public GatewayFilter apply(Object config) {
        return (exchange, chain) -> {
            String memberToken = getToken(exchange);
            ServerHttpResponse response = exchange.getResponse();
            JwtInfo jwtInfo;
            if (StrUtil.isBlank(memberToken)) {
                return tokenError(response, "登录信息过期，请重新登录！");
            }
            try {
                jwtInfo = JwtUtil.getInfoFromToken(memberToken, jwtConfig.getPubKeyPath());
                Object invlideToken = redisTemplate.opsForValue().get(Constants.TOKEN.concat(jwtInfo.getId()));
                if (ObjectUtil.isNotNull(invlideToken) && memberToken.equals(invlideToken.toString())) {
                    return tokenError(response, "登录信息过期，请重新登录！");
                }
                if (jwtInfo.getBuffer() <= jwtConfig.getBuffer()) {
                    exchange.getResponse().getHeaders().add(Constants.REFRESH_TOKEN, "true");
                }
            } catch (BusinessException e) {
                return tokenError(response, "登录信息过期，请重新登录！");
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
        String memberToken = exchange.getRequest().getHeaders().getFirst(jwtConfig.getMemberTokenHeader());
        if (StrUtil.isBlank(memberToken)) {
            MultiValueMap<String, String> queryParams = exchange.getRequest().getQueryParams();
            memberToken = queryParams.getFirst(jwtConfig.getMemberTokenHeader());
        }
        if (StrUtil.isBlank(memberToken)) {
            MultiValueMap<String, HttpCookie> cookies = exchange.getRequest().getCookies();
            HttpCookie cookie = cookies.getFirst(jwtConfig.getMemberTokenHeader());
            memberToken = cookie != null ? cookie
                    .getValue() : null;
        }
        return memberToken;
    }

    /**
     * 返回token错误的响应，前台判断重新登录
     *
     * @param response
     * @param message
     * @return
     */
    private Mono<Void> tokenError(ServerHttpResponse response, String message) {
        RestResult restResult = RestResult.build(HttpStatus.HTTP_UNAUTHORIZED, message);
        DataBuffer bodyDataBuffer = response.bufferFactory().wrap(JSONUtil.toJsonStr(restResult).getBytes());
        return response.writeWith(Mono.just(bodyDataBuffer));
    }
}
