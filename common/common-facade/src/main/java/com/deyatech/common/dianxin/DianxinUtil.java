package com.deyatech.common.dianxin;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSON;
import cn.hutool.json.JSONUtil;
import com.deyatech.common.submail.SubMailMessage;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.cxf.endpoint.Client;
import org.apache.cxf.jaxws.endpoint.dynamic.JaxWsDynamicClientFactory;

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
     * @param calleeNumber 手机号
     * @param ttsCode 模板ID
     * @param TmpParam 模板参数(多个用,隔开)：1,2,3
     * @param playCnt 播放次数
     * @return
     */
    public boolean sendVoice(SubMailMessage subMailMessage, String ttsCode,String playCnt) {
        log.info("电信接口发送语音消息:calleeNumber：" +ttsCode);
        //创建动态客户端
        JaxWsDynamicClientFactory dcf = JaxWsDynamicClientFactory.newInstance();
        Client client = dcf.createClient(dianxinConfig.getWsdlUrl());
        //需要密码的情况需要加上用户名和密码
        Object[] objects = new Object[0];
        try {
            objects = client.invoke(dianxinConfig.getMethodName(), dianxinConfig.getAccountNumber(),subMailMessage.getTo(),ttsCode,
                    JSONUtil.toJsonStr(subMailMessage.getVars()),playCnt);
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