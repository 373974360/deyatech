package com.deyatech.common.utils;

import cn.hutool.core.collection.CollectionUtil;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * cookie工具类
 * @author: csm
 * @date 2019/07/05
 */
public class CookieUtils {

    /**
     * 通过cookie名获取对应value
     * @param request
     * @param key cookie名
     * @return cookie的值
     */
    public static Map<String, String> getValueByCookieName(HttpServletRequest request, List<String> keys){
        Cookie[] cookies = request.getCookies();
        Map<String, String> map = CollectionUtil.newHashMap();
        if (cookies != null){
            Arrays.stream(cookies).filter(c -> CollectionUtil.contains(keys, c.getName())).forEach(
                    cookie -> map.put(cookie.getName(), cookie.getValue())
            );
        }
        return map;
    }

    /**
     * 将memberId放入cookie
     * @param memberIdEncoded 加密的memberId
     * @param domain 域
     * @return
     */
    public static Cookie putMemberIdtoCookie(String memberIdEncoded, String domain){
        Cookie cookie = new Cookie("member_id", memberIdEncoded);
        cookie.setPath("/");
        cookie.setDomain(domain);
        return cookie;
    }

    /**
     * 通过cookie名删除对应value
     * @param request
     * @param keys cookie名
     */
    public static void deleteValueByCookieName(HttpServletRequest request, HttpServletResponse response, List<String> keys, String domain){
        Cookie[] cookies = request.getCookies();
        if (cookies != null){
            Arrays.stream(cookies).filter(c -> CollectionUtil.contains(keys, c.getName())).forEach(
                    cookie -> deleteCookie(response, cookie, domain)
            );
        }
    }

    private static void deleteCookie(HttpServletResponse response, Cookie cookie, String domain) {
        cookie.setValue(null);
        cookie.setPath("/");
        cookie.setMaxAge(0);
        cookie.setDomain(domain);
        response.addCookie(cookie);
    }

}

