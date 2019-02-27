package org.land.common.entity;

import cn.hutool.http.HttpStatus;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import springfox.documentation.annotations.ApiIgnore;

import java.io.Serializable;

/**
 * controller公用返回结果类
 *
 * @author : lee.
 * @since : 2018-12-14 13:27
 */
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class RestResult<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Http响应状态码
     */
    @ApiModelProperty(value = "响应状态码", example = "200，500")
    private Integer httpCode;

    /**
     * Http响应消息
     */
    @ApiModelProperty(value = "返回的提示信息", example = "OK")
    private String msg;

    /**
     * Http响应数据
     */
    @ApiModelProperty(value = "返回的具体数据")
    private T data;

    public static RestResult ok() {
        return new RestResult(HttpStatus.HTTP_OK, "OK", null);
    }

    public static RestResult ok(Object data) {
        return new RestResult(HttpStatus.HTTP_OK, "OK", data);
    }

    public static RestResult error(String msg) {
        return new RestResult(HttpStatus.HTTP_INTERNAL_ERROR, msg, null);
    }

    public static RestResult build(Integer httpCode, String msg) {
        return new RestResult(httpCode, msg, null);
    }

    public static RestResult build(Integer httpCode, String msg, Object data) {
        return new RestResult(httpCode, msg, data);
    }

    @ApiModelProperty(hidden = true)
    public boolean isOk() {
        return this.httpCode == HttpStatus.HTTP_OK;
    }

}
