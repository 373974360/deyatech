package com.deyatech.common.context;

import cn.hutool.core.collection.CollectionUtil;
import com.deyatech.common.Constants;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 系统当前用户id存储类
 * </p>
 *
 * @author: lee.
 * @since: 2018-12-14 16:13
 */
public class UserContextHelper {
    public static ThreadLocal<Map<String, Object>> threadLocal = new ThreadLocal<Map<String, Object>>();

    public static void set(String key, Object value) {
        Map<String, Object> map = threadLocal.get();
        if (map == null) {
            map = CollectionUtil.newHashMap();
            threadLocal.set(map);
        }
        map.put(key, value);
    }

    public static Object get(String key) {
        Map<String, Object> map = threadLocal.get();
        if (map == null) {
            map = CollectionUtil.newHashMap();
            threadLocal.set(map);
        }
        return map.get(key);
    }

    public static void remove() {
        threadLocal.remove();
    }

    public static String getUserId() {
        Object value = get(Constants.CONTEXT_KEY_USER_ID);
        return returnObjectValue(value);
    }

    public static void setUserID(String userId) {
        set(Constants.CONTEXT_KEY_USER_ID, userId);
    }

    private static String returnObjectValue(Object value) {
        return value == null ? null : value.toString();
    }
}
