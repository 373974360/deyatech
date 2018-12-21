package org.land.common.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * <p>
 * 业务异常类
 * </p>
 *
 * @author: lee.
 * @since: 2018-12-21 16:25
 */
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class BusinessException extends RuntimeException {

    /**
     * Http响应状态码
     */
    private Integer httpCode;

    /**
     * 报错提示消息
     */
    private String msg;

    /**
     * 报错信息详情
     */
    private Object e;

    public BusinessException(Integer httpCode, String msg) {
        this.httpCode = httpCode;
        this.msg = msg;
    }
}
