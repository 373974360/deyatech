package com.deyatech.common.interceptor;

import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpStatus;
import com.deyatech.common.Constants;
import com.deyatech.common.exception.BusinessException;
import com.deyatech.common.utils.AesUtil;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <p>
 * 应用所有请求过滤器，拦截所有不是从gateway过来的请求
 * </p>
 *
 * @author: lee.
 * @since: 2019/3/11 14:23
 */
public class AuthRestInterceptor extends HandlerInterceptorAdapter {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String gatewayHeader = request.getHeader(Constants.GATEWAY_HEADER);
        if (StrUtil.isBlank(gatewayHeader)) {
            throw new BusinessException(HttpStatus.HTTP_FORBIDDEN, "请求被拒绝！");
        } else {
            String s = AesUtil.aesDecrypt(gatewayHeader);
            if (!Constants.GATEWAY_VALUE.equalsIgnoreCase(s)) {
                throw new BusinessException(HttpStatus.HTTP_FORBIDDEN, "请求被拒绝！");
            }
        }
        return super.preHandle(request, response, handler);
    }
}
