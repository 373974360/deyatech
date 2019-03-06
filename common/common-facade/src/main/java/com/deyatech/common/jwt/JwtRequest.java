package com.deyatech.common.jwt;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 用户用来获取token的参数对象
 * </p>
 *
 * @author: lee.
 * @since: 2018-12-21 13:32
 */
@Data
@Accessors(chain = true)
@NoArgsConstructor
public class JwtRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 帐号
     */
    private String account;

    /**
     * 密码
     */
    private String password;

    /**
     * 验证码
     */
    private String verifyCode;

    /**
     * 随机数
     */
    private String random;
}
