package com.deyatech.gateway.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpStatus;
import com.deyatech.admin.entity.User;
import com.deyatech.admin.feign.AdminFeign;
import com.deyatech.admin.vo.UserVo;
import com.deyatech.common.Constants;
import com.deyatech.common.entity.RestResult;
import com.deyatech.common.exception.BusinessException;
import com.deyatech.common.jwt.JwtInfo;
import com.deyatech.common.jwt.JwtUtil;
import com.deyatech.gateway.config.JwtConfig;
import com.deyatech.gateway.service.UserAuthService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * <p>
 * 用户Token验证service实现类
 * </p>
 *
 * @author: lee.
 * @since: 2018-12-21 15:07
 */
@Slf4j
@Service
public class UserAuthServiceImpl implements UserAuthService {

    @Autowired
    JwtConfig jwtConfig;

    @Autowired
    AdminFeign adminFeign;

    @Autowired
    RedisTemplate redisTemplate;

    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(Constants.PASSWORD_ENCORDER_SALT);

    /**
     * 登录获取token
     *
     * @param account
     * @param password
     * @return
     * @throws Exception
     */
    @Override
    public RestResult login(String account, String password) {
        RestResult<UserVo> result = adminFeign.getByUser(new User().setAccount(account));
        if (result != null && result.isOk()) {
            UserVo userVo = result.getData();
            if (ObjectUtil.isNotNull(userVo)) {
                if (encoder.matches(password, userVo.getPassword())) {
                    JwtInfo jwtInfo = new JwtInfo(userVo.getId(), userVo.getAccount(), userVo.getName(), null);
                    String token = JwtUtil.generateToken(jwtInfo, jwtConfig.getPriKeyPath(), jwtConfig.getXpire());
                    userVo.setToken(token);
                    userVo.setPassword(null);
                    RestResult<String[]> allPermissionsByUserId = adminFeign.getAllPermissionsByUserId(userVo.getId());
                    if (allPermissionsByUserId != null && allPermissionsByUserId.isOk()) {
                        userVo.setPermissions(allPermissionsByUserId.getData());
                    }
                    return RestResult.ok(userVo);
                } else {
                    return RestResult.build(HttpStatus.HTTP_INTERNAL_ERROR, "密码不正确");
                }
            } else {
                return RestResult.build(HttpStatus.HTTP_INTERNAL_ERROR, "用户不存在");
            }
        } else {
            throw new BusinessException(HttpStatus.HTTP_INTERNAL_ERROR, "调用userFeign根据用户名查找用户出错", result);
        }
    }

    /**
     * 刷新token
     *
     * @param oldToken
     * @return
     * @throws Exception
     */
    @Override
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
    @Override
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
