package com.deyatech.admin.controller;

import cn.hutool.core.util.ObjectUtil;
import com.deyatech.admin.config.CasConfig;
import com.deyatech.alibaba.platform.AlibabaPlatForm;
import com.deyatech.alibaba.platform.model.req.SelectOrgDetailsReq;
import com.deyatech.alibaba.platform.model.req.SelectUserInfoReq;
import com.deyatech.common.entity.RestResult;
import com.deyatech.common.utils.AesUtil;
import com.deyatech.common.utils.CookieUtils;
import org.jasig.cas.client.util.AssertionHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.concurrent.TimeUnit;

/**
 * @author doukang
 * @description 需要统一登录验证的接口
 * @date 2019/10/16 16:26
 */
@RequestMapping("/admin/cas")
@Controller
public class CasController {

    @Autowired
    private AlibabaPlatForm alibabaPlatForm;

    @Value("${front-url}")
    private String frontUrl;

    @Value("${domain}")
    private String domain;

    @Autowired
    RedisTemplate redisTemplate;

    @GetMapping("/getUserProfile")
    @ResponseBody
    public RestResult getUserProfile() {
        String userId = AssertionHolder.getAssertion().getPrincipal().getName();
        SelectUserInfoReq req = new SelectUserInfoReq();
        req.setUserId(userId);
        return RestResult.ok(alibabaPlatForm.selectUserInfo(req));
    }

    @GetMapping("/login")
    public String login(HttpServletResponse response) {
        String userId = AssertionHolder.getAssertion().getPrincipal().getName();
        setUserCookie(userId, response);
        setUserSession(userId);
        return "redirect:" + frontUrl + "/";
    }

    /**
     * 后台路由，需要登录的通过后台转发
     * @param url 转发路径
     * @return
     */
    @GetMapping("/redirect")
    public String redirect(@RequestParam("url") String url, HttpServletResponse response) {
        String userId = AssertionHolder.getAssertion().getPrincipal().getName();
        setUserCookie(userId, response);
        Object userInfo = redisTemplate.opsForValue().get("session:" + userId);
        if (ObjectUtil.isNull(userInfo)) {
            setUserSession(userId);
        } else {
            //将session存在时间延长5min
            redisTemplate.expire("session:" + userId, 5, TimeUnit.MINUTES);
        }
        return "redirect:" + frontUrl + url;
    }

    /**
     * 根据机构id获取机构详情
     *
     * @param orgId
     * @return
     */
    @GetMapping("/getOrgDetail")
    @ResponseBody
    public RestResult getOrgDetail(String orgId) {
        SelectOrgDetailsReq req = new SelectOrgDetailsReq();
        req.setOrgId(orgId);
        return RestResult.ok(alibabaPlatForm.selectOrgDetails(req));
    }

    private void setUserSession(String userId) {
        // 将用户信息保存到redis中，前缀为session:  , 过期时间为30min
        redisTemplate.opsForValue().set("session:" + userId, userId, 30, TimeUnit.MINUTES);
    }

    private void setUserCookie(String userId, HttpServletResponse response) {
        // userId加密放入cookie中
        String userIdEncoded = AesUtil.aesEncrypt(userId);
        Cookie cookie = CookieUtils.putCookie(CasConfig.SESSION_KEY, userIdEncoded, domain);
        response.addCookie(cookie);
    }
}
