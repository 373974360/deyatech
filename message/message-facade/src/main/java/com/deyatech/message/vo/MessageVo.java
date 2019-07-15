package com.deyatech.message.vo;


import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 消息传递对象
 * </p>
 *
 * @author lee.
 * @since 2019-03-07
 */
@Data
@EqualsAndHashCode
@Accessors(chain = true)
public class MessageVo implements java.io.Serializable {

    private Object data;
    private String addr;

}
