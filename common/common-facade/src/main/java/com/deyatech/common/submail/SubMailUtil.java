package com.deyatech.common.submail;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;

import java.io.IOException;
import java.util.List;

/**
 * <p>
 * SubMail短信发送工具类
 * </p>
 *
 * @author lee.
 * @since 2019-03-07
 */
@Slf4j
public class SubMailUtil {

    public SubMailUtil(SubMailConfig submailConfig) {
        this.submailConfig = submailConfig;
    }

    private SubMailConfig submailConfig;

    public boolean sendSubMailMessageMock(String projectId, List<SubMailMessage> subMailMessageList) {
        log.info(String.format("模拟发送短信[%s]给[%s]", projectId, JSONUtil.toJsonStr(subMailMessageList)));
        return true;
    }

    public boolean sendSubMailMessage(String projectId, SubMailMessage subMailMessage) {
        return sendSubMailMessage(projectId, CollectionUtil.toList(subMailMessage));
    }

    public boolean sendSubMailMessage(String projectId, List<SubMailMessage> subMailMessageList) {
        if (submailConfig.isMock()) {
            return sendSubMailMessageMock(projectId, subMailMessageList);
        }
        String multi = "";
        if (CollectionUtil.isNotEmpty(subMailMessageList)) {
            multi = JSONUtil.toJsonStr(subMailMessageList);
        }
        String info;
        HttpClient httpclient = new HttpClient();
        PostMethod post = new PostMethod(this.submailConfig.getSendUrl());
        post.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "utf-8");
        post.addParameter("appid", submailConfig.getAppId());
        post.addParameter("project", projectId);
        post.addParameter("multi", multi);
        post.addParameter("signature", submailConfig.getAppKey());
        try {
            httpclient.executeMethod(post);
            info = new String(post.getResponseBody(), "utf-8");
            log.debug("短信接口返回：" + info);
            JSONArray jsonArray = JSONUtil.parseArray(info);

            for (int i = 0; i < jsonArray.size(); i++) {
                SubMailResult messageResultVo = JSONUtil.toBean(JSONUtil.parseObj(jsonArray.get(i)), SubMailResult.class);
                if ("success".equals(messageResultVo.getStatus())) {
                    if (log.isInfoEnabled()) {
                        log.info("成功发送短信给[" + messageResultVo.getTo() + "]");
                    }
                } else {
                    String logContent = "发送短信给[" + messageResultVo.getTo() + "]失败";
                    if (log.isInfoEnabled()) {
                        log.error(logContent);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            log.error("发送短信错误", e);
        }
        return true;
    }
}
