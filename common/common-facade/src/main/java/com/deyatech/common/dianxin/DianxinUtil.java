package com.deyatech.common.dianxin;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSON;
import cn.hutool.json.JSONUtil;
import com.deyatech.common.submail.SubMailMessage;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.cxf.endpoint.Client;
import org.apache.cxf.jaxws.endpoint.dynamic.JaxWsDynamicClientFactory;

import java.util.Collection;

/**
 * <p>
 * 电信webservice发送工具类
 * </p>
 *
 * @author yxz
 * @since 2019-04-28
 */
@Slf4j
@Data
public class DianxinUtil {

    public DianxinUtil(DianxinConfig dianxinConfig) {
        this.dianxinConfig = dianxinConfig;
    }

    private DianxinConfig dianxinConfig;

    /**
     * 调用电信webservice接口发送电话
     * @param ttsCode 模板ID
     * @param playCnt 播放次数
     * @return
     */
    public boolean sendVoice(SubMailMessage subMailMessage, String ttsCode,String playCnt) {
        log.info("电信接口发送语音消息:calleeNumber：" +ttsCode);
        //创建动态客户端
        JaxWsDynamicClientFactory dcf = JaxWsDynamicClientFactory.newInstance();
        Client client = dcf.createClient(dianxinConfig.getWsdlUrl());
        String params = "";
        Collection<String> values = subMailMessage.getVars().values();
        if(values.size() > 0){
            for (String value : values) {
                params = params.concat(value).concat(",");
            }
            params = params.substring(0,params.length() - 1);
        }
        //需要密码的情况需要加上用户名和密码
        Object[] objects = null;
        try {
            objects = client.invoke(dianxinConfig.getMethodName(), dianxinConfig.getAccountNumber(),subMailMessage.getTo(),ttsCode,
                    params,playCnt);
            log.info("电信接口返回信息:" + objects[0]);
        } catch (java.lang.Exception e) {
            log.error(StrUtil.format("电信接口返回信息失败：%s", JSONUtil.toJsonStr(e)));
            return false;
        }finally {
            try {
                client.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return true;
    }
}
