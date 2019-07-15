package com.deyatech.common.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * @Description: redis缓存数据封装
 * @Author: like
 * @Date: 2017-07-28 15:29
 * @Version: 1.0
 * @Created in idea by autoCode
 */
@Data
@EqualsAndHashCode
@Accessors(chain = true)
public class RedisResult implements Serializable {

    private String key;
    private Object value;
    private String type;
    private Date ttl;
}
