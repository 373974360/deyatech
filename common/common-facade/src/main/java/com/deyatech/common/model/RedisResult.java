package com.deyatech.common.model;

import java.io.Serializable;
import java.util.Date;

/**
 * @Description: redis缓存数据封装
 * @Author: like
 * @Date: 2017-07-28 15:29
 * @Version: 1.0
 * @Created in idea by autoCode
 */
public class RedisResult implements Serializable {

    private String key;
    private Object value;
    private String type;
    private Date ttl;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Date getTtl() {
        return ttl;
    }

    public void setTtl(Date ttl) {
        this.ttl = ttl;
    }
}
