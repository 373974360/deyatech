package com.deyatech.gateway.controller;

import com.deyatech.common.base.BaseController;
import com.deyatech.common.entity.RestResult;
import com.deyatech.common.jwt.JwtRequest;
import com.deyatech.gateway.config.JwtConfig;
import com.deyatech.gateway.service.UserAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *
 * </p>
 *
 * @author: lee.
 * @since: 2018-12-21 13:38
 */
@RestController
@RequestMapping("/auth/userJwt/")
public class UserAuthController extends BaseController {

    @Autowired
    RedisTemplate redisTemplate;

    @Autowired
    UserAuthService userAuthService;

    @Autowired
    JwtConfig jwtConfig;

    /**
     * 根据用户名和密码验证登录
     *
     * @param jwtInfo
     * @return
     * @throws Exception
     */
    @GetMapping("/token")
    public RestResult creatToken(JwtRequest jwtInfo) throws Exception {
        if (validateVerifyCode(redisTemplate, jwtInfo.getVerifyCode(), jwtInfo.getRandom())) {
            return userAuthService.login(jwtInfo.getAccount(), jwtInfo.getPassword());
        } else {
            return RestResult.error("验证码不正确");
        }
    }

    @GetMapping("/refresh")
    public RestResult refreshToken(ServerHttpRequest request) throws Exception {
        String token = request.getHeaders().getFirst(jwtConfig.getTokenHeader());
        return userAuthService.refresh(token);
    }

    /**
     * 作废登录
     *
     * @param request
     * @return
     */
    @GetMapping("/invalid")
    public RestResult invalid(ServerHttpRequest request) throws Exception {
        String token = request.getHeaders().getFirst(jwtConfig.getTokenHeader());
        return userAuthService.invalid(token);
    }
}
