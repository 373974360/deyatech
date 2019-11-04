package com.deyatech.gateway.service.impl;

import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpStatus;
import com.deyatech.common.Constants;
import com.deyatech.common.entity.RestResult;
import com.deyatech.common.jwt.JwtInfo;
import com.deyatech.common.jwt.JwtUtil;
import com.deyatech.gateway.config.JwtConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * <p>
 * Token验证service基础实现类
 * </p>
 *
 * @author: csm
 * @since: 2019-11-04
 */
@Slf4j
@Service
public class BaseAuthServiceImpl {

    @Autowired
    JwtConfig jwtConfig;

    @Autowired
    RedisTemplate redisTemplate;

    /**
     * 刷新token
     *
     * @param oldToken
     * @return
     * @throws Exception
     */
    public RestResult refresh(String oldToken) {
        if (StrUtil.isNotBlank(oldToken)) {
            JwtInfo jwtInfo = JwtUtil.getInfoFromToken(oldToken, jwtConfig.getPubKeyPath());
            return RestResult.ok(JwtUtil.generateToken(jwtInfo, jwtConfig.getPriKeyPath(), jwtConfig.getXpire()));
        } else {
            return RestResult.build(HttpStatus.HTTP_INTERNAL_ERROR, "token为空");
        }
    }

    /**
     * 注销token之后在redis中保存配置的过期时长，在这期间的token都是无效token，直到token自动过期
     *
     * @param token
     * @return
     * @throws Exception
     */
    public RestResult invalid(String token) {
        if (StrUtil.isNotBlank(token)) {
            JwtInfo jwtInfo = JwtUtil.getInfoFromToken(token, jwtConfig.getPubKeyPath());
            redisTemplate.opsForValue().set(Constants.TOKEN.concat(jwtInfo.getId()), token);
            redisTemplate.expire(Constants.TOKEN.concat(jwtInfo.getId()), jwtConfig.getXpire(), TimeUnit.SECONDS);
            return RestResult.ok();
        } else {
            return RestResult.build(HttpStatus.HTTP_INTERNAL_ERROR, "token为空");
        }
    }
}
