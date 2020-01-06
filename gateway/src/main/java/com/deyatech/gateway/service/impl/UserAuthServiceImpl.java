package com.deyatech.gateway.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.http.HttpStatus;
import com.deyatech.admin.entity.Department;
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

import java.util.Objects;

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
public class UserAuthServiceImpl extends BaseAuthServiceImpl implements UserAuthService {

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
                Department department = adminFeign.getDepartmentById(userVo.getDepartmentId()).getData();
                if (Objects.nonNull(department)) {
                    userVo.setDepartmentName(department.getName());
                }
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
}
