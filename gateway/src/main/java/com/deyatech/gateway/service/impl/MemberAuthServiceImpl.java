package com.deyatech.gateway.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.http.HttpStatus;
import com.deyatech.common.Constants;
import com.deyatech.common.entity.RestResult;
import com.deyatech.common.exception.BusinessException;
import com.deyatech.common.jwt.JwtInfo;
import com.deyatech.common.jwt.JwtUtil;
import com.deyatech.gateway.config.JwtConfig;
import com.deyatech.gateway.service.MemberAuthService;
import com.deyatech.market.entity.Member;
import com.deyatech.market.feign.MarketFeign;
import com.deyatech.market.vo.MemberVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * <p>
 * member - Token验证service实现类
 * </p>
 *
 * @author: csm
 * @since: 2019-11-04
 */
@Slf4j
@Service
public class MemberAuthServiceImpl extends BaseAuthServiceImpl implements MemberAuthService {

    @Autowired
    JwtConfig jwtConfig;

    @Autowired
    MarketFeign marketFeign;

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
        RestResult<MemberVo> result = marketFeign.getByMember(new Member().setAccount(account));
        if (result != null && result.isOk()) {
            MemberVo memberVo = result.getData();
            if (ObjectUtil.isNotNull(memberVo)) {
                if (encoder.matches(password, memberVo.getPassword())) {
                    JwtInfo jwtInfo = new JwtInfo(memberVo.getId(), memberVo.getAccount(), memberVo.getName(), null);
                    String token = JwtUtil.generateToken(jwtInfo, jwtConfig.getPriKeyPath(), jwtConfig.getXpire());
                    memberVo.setToken(token);
                    memberVo.setPassword(null);
                    return RestResult.ok(memberVo);
                } else {
                    return RestResult.build(HttpStatus.HTTP_INTERNAL_ERROR, "密码不正确");
                }
            } else {
                return RestResult.build(HttpStatus.HTTP_INTERNAL_ERROR, "会员不存在");
            }
        } else {
            throw new BusinessException(HttpStatus.HTTP_INTERNAL_ERROR, "调用marketFeign根据账号查找会员出错", result);
        }
    }
}
