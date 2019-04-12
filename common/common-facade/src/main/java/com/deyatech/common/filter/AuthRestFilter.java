package com.deyatech.common.filter;

import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpStatus;
import cn.hutool.json.JSONUtil;
import com.deyatech.common.Constants;
import com.deyatech.common.context.UserContextHelper;
import com.deyatech.common.entity.RestResult;
import com.deyatech.common.utils.AesUtil;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

/**
 * <p>
 * http请求过滤器，拦截不是从路由过来的请求
 * </p>
 *
 * @author: lee.
 * @since: 2019/4/11 16:45
 */
@Slf4j
@WebFilter(filterName = "authRestFilter", urlPatterns = {"/*"})
public class AuthRestFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String gatewayHeader = request.getHeader(Constants.GATEWAY_HEADER);
        if (StrUtil.isBlank(gatewayHeader)) {
            error(response);
            return;
        } else {
            String s = AesUtil.aesDecrypt(gatewayHeader);
            if (!Constants.GATEWAY_VALUE.equalsIgnoreCase(s)) {
                error(response);
                return;
            }
        }
        String userId = request.getHeader(Constants.CONTEXT_KEY_USER_ID);
        if (StrUtil.isNotBlank(userId)) {
            UserContextHelper.setUserID(userId);
        }
        filterChain.doFilter(request, servletResponse);
    }

    private void error(HttpServletResponse response){
        PrintWriter writer = null;
        try {
            response.setContentType("application/json; charset=utf-8");
            writer = response.getWriter();
            String jsonStr = JSONUtil.toJsonStr(RestResult.build(HttpStatus.HTTP_FORBIDDEN, "请求被拒绝！"));
            writer.write(jsonStr);
            writer.flush();
            writer.close();
        } catch (UnsupportedEncodingException e) {
            log.error("过滤器返回信息失败:" + e.getMessage(), e);
        } catch (IOException e) {
            log.error("过滤器返回信息失败:" + e.getMessage(), e);
        } finally {
            if (null != writer) {
                writer.close();
            }
        }
    }
}
