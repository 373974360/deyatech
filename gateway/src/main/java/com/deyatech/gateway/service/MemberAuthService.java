package com.deyatech.gateway.service;

import com.deyatech.common.entity.RestResult;

/**
 * <p>
 * member - Token验证service
 * </p>
 *
 * @author: csm
 * @since: 2019-11-04
 */
public interface MemberAuthService {

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
