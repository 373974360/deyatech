package com.deyatech.gateway.service;

import com.deyatech.common.entity.RestResult;

/**
 * <p>
 * 用户Token验证service
 * </p>
 *
 * @author: lee.
 * @since: 2018-12-21 15:06
 */
public interface UserAuthService {

    /**
     * 登录
     * @param account
     * @param password
     * @return
     * @throws Exception
     */
    RestResult login(String account, String password) throws Exception;

    /**
     * 刷新
     * @param oldToken
     * @return
     * @throws Exception
     */
    RestResult refresh(String oldToken) throws Exception ;

    /**
     * 注销
     * @param token
     * @return
     * @throws Exception
     */
    RestResult invalid(String token) throws Exception;
}
