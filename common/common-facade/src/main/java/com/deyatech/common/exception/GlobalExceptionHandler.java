package com.deyatech.common.exception;

import cn.hutool.http.HttpStatus;
import lombok.extern.slf4j.Slf4j;
import com.deyatech.common.entity.RestResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ServerWebExchange;

/**
 * <p>
 * 统一异常处理类
 * </p>
 *
 * @author: lee.
 * @since: 2018-12-21 16:25
 */
@Slf4j
@RestControllerAdvice
@ResponseBody
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class)
    public Object serverExceptionHandler(ServerWebExchange serverWebExchange, Exception ex) {
        log.error("错误发生在请求：" + serverWebExchange.getRequest().getURI() + "  " + ex.getMessage(), ex);
        if (ex instanceof BusinessException) {
            BusinessException businessException = (BusinessException) ex;
            return businessException;
        } else {
            return RestResult.build(HttpStatus.HTTP_INTERNAL_ERROR, "系统错误(".concat(ex.getMessage().concat(")，请联系管理员")), getStackTraceString(ex));
        }
    }

    private String getStackTraceString(Exception ex) {
        String str = "";
        StackTraceElement[] stackTrace = ex.getStackTrace();
        for (StackTraceElement stackTraceElement : stackTrace) {
            str += "\t at".concat(stackTraceElement.toString()).concat(" \r\n");
        }
        return str;
    }

}
