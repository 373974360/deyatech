package com.deyatech.gateway.controller;

import com.deyatech.common.base.BaseController;
import com.deyatech.common.entity.RestResult;
import com.deyatech.common.jwt.JwtRequest;
import com.deyatech.gateway.config.JwtConfig;
import com.deyatech.gateway.service.MemberAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 会员登录/登出等接口
 * </p>
 *
 * @author: csm
 * @since: 2019-11-04
 */
@RestController
@RequestMapping("/auth/memberJwt")
public class MemberAuthController extends BaseController {

    @Autowired
    RedisTemplate redisTemplate;

    @Autowired
    MemberAuthService memberAuthService;

    @Autowired
    JwtConfig jwtConfig;

    /**
     * 根据用户名和密码验证登录
     *
     * @param jwtInfo
     * @return
     * @throws Exception
     */
    @GetMapping("/login")
    public RestResult login(JwtRequest jwtInfo) throws Exception {
        if (validateVerifyCode(redisTemplate, jwtInfo.getVerifyCode(), jwtInfo.getRandom())) {
            return memberAuthService.login(jwtInfo.getAccount(), jwtInfo.getPassword());
        } else {
            return RestResult.error("验证码不正确");
        }
    }

    @GetMapping("/refresh")
    public RestResult refreshToken(ServerHttpRequest request) throws Exception {
        String token = request.getHeaders().getFirst(jwtConfig.getMemberTokenHeader());
        return memberAuthService.refresh(token);
    }

    /**
     * 作废登录
     *
     * @param request
     * @return
     */
    @GetMapping("/logout")
    public RestResult logout(ServerHttpRequest request) throws Exception {
        String token = request.getHeaders().getFirst(jwtConfig.getMemberTokenHeader());
        return memberAuthService.invalid(token);
    }
}
