package org.land.gateway.controller;

import org.land.common.base.BaseController;
import org.land.common.entity.RestResult;
import org.land.common.jwt.JwtRequest;
import org.land.common.utils.AesUtil;
import org.land.gateway.config.JwtConfig;
import org.land.gateway.service.UserAuthService;
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
@RequestMapping("/auth")
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
        if (validateVerifyCode(jwtInfo.getVerifyCode(), jwtInfo.getRandom())) {
            return userAuthService.login(AesUtil.aesDecrypt(jwtInfo.getAccount()), AesUtil.aesDecrypt(jwtInfo.getPassword()));
        } else {
            return RestResult.build(200, "验证码不正确", false);
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

    /**
     * 验证验证码
     *
     * @param verifyCode
     * @return
     */
    public boolean validateVerifyCode(String verifyCode, String random) {
        Object o = redisTemplate.opsForValue().get(random);
        if (o == null) {
            return false;
        }
        String s = o.toString();
        if (verifyCode != null && verifyCode.equalsIgnoreCase(s)) {
            redisTemplate.delete(random);
            return true;
        }
        return false;
    }
}
