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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpCookie;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

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

    @Autowired
    AdminFeign adminFeign;

    @Value("${userToken.ignore}")
    String userTokenIgnore;

    @Override
    public GatewayFilter apply(Object config) {
        return (exchange, chain) -> {
            if (!checkRequestIsIgnore(exchange)) {
                String userToken = getToken(exchange);
                ServerHttpResponse response = exchange.getResponse();
                JwtInfo jwtInfo;
                if (StrUtil.isBlank(userToken)) {
                    RestResult restResult = RestResult.build(HttpStatus.HTTP_INTERNAL_ERROR, "登录信息过期，请重新登录！");
                    DataBuffer bodyDataBuffer = response.bufferFactory().wrap(JSONUtil.toJsonStr(restResult).getBytes());
                    return response.writeWith(Mono.just(bodyDataBuffer));
                } else {
                    jwtInfo = JwtUtil.getInfoFromToken(userToken, jwtConfig.getPubKeyPath());
                    Object invlideToken = redisTemplate.opsForValue().get(Constants.TOKEN.concat(jwtInfo.getId()));
                    if (ObjectUtil.isNotNull(invlideToken) && userToken.equals(invlideToken.toString())) {
                        throw new BusinessException(HttpStatus.HTTP_INTERNAL_ERROR, "登录信息过期，请重新登录！");
                    } else {
                        if (!checkRequestIsAllow(exchange, jwtInfo.getId())) {
                            RestResult restResult = RestResult.build(HttpStatus.HTTP_FORBIDDEN, "权限不足，请联系管理员！");
                            DataBuffer bodyDataBuffer = response.bufferFactory().wrap(JSONUtil.toJsonStr(restResult).getBytes());
                            return response.writeWith(Mono.just(bodyDataBuffer));
                        } else {
                            ServerHttpRequest request = exchange.getRequest().mutate().header(Constants.CONTEXT_KEY_USER_ID, jwtInfo.getId()).build();
                            exchange = exchange.mutate().request(request).build();
                            if (jwtInfo.getBuffer() <= jwtConfig.getBuffer()) {
                                exchange.getResponse().getHeaders().add(Constants.REFRESH_TOKEN, "true");
                            }
                        }
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

    /**
     * 根据配置文件检查请求是否需要验证token
     *
     * @param exchange
     * @return
     */
    private boolean checkRequestIsIgnore(ServerWebExchange exchange) {
        String path = exchange.getRequest().getPath().value();
        if (StrUtil.isNotBlank(userTokenIgnore)) {
            for (String s : userTokenIgnore.split(Constants.DEFAULT_STRING_SPLIT)) {
                if (s.equalsIgnoreCase(path)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 根据用户ID判断是否拥有该请求权限
     *
     * @param exchange
     * @param userId
     * @return
     */
    private boolean checkRequestIsAllow(ServerWebExchange exchange, String userId) {
        RestResult<String[]> allRequestsByUserId = adminFeign.getAllRequestsByUserId(userId);
        if (allRequestsByUserId.isOk()) {
            String path = exchange.getRequest().getPath().value();
            for (String datum : allRequestsByUserId.getData()) {
                if (path.contains(datum)) {
                    return true;
                }
            }
        }
        return false;
    }
}
