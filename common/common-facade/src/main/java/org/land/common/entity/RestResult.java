package org.land.common.entity;

import cn.hutool.http.HttpStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * controller公用返回结果类
 * @author : lee.
 * @since : 2018-12-14 13:27
 */
@Data
@Accessors(chain = true)
@AllArgsConstructor
public class RestResult<T> implements Serializable {

    /**
     * Http响应状态码
     */
    private Integer httpCode;

    /**
     * Http响应消息
     */
    private String msg;

    /**
     * Http响应数据
     */
    private T data;

    public static RestResult ok() {
        return new RestResult(HttpStatus.HTTP_OK,"OK",null);
    }

    public static RestResult ok(Object data) {
        return new RestResult(HttpStatus.HTTP_OK,"OK",data);
    }

    public static RestResult error(String msg) {
        return new RestResult(HttpStatus.HTTP_INTERNAL_ERROR, msg, null);
    }

    public static RestResult build(Integer httpCode, String msg) {
        return new RestResult(httpCode,msg,null);
    }

    public static RestResult build(Integer httpCode, String msg, Object data) {
        return new RestResult(httpCode,msg,data);
    }

}
