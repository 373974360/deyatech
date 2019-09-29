package com.deyatech.common.dianxin;

import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.deyatech.common.submail.SubMailMessage;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.cxf.endpoint.Client;
import org.apache.cxf.jaxws.endpoint.dynamic.JaxWsDynamicClientFactory;

import java.util.Collection;
import java.util.Map;

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
                    params,Integer.parseInt(playCnt));
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

    public static void main(String[] args) {
        DianxinConfig dianxinConfig = new DianxinConfig();
        dianxinConfig.setAccountNumber("02985992688");
        dianxinConfig.setMethodName("playWavTmpFile");
        dianxinConfig.setWsdlUrl("http://htjdzwzx.118399.cn/LogisticsService.asmx?WSDL");
        DianxinUtil  dianxinUtil = new DianxinUtil(dianxinConfig);
        SubMailMessage subMailMessage = new SubMailMessage();
        subMailMessage.setTo("18682925636");
        Map<String,String> params = MapUtil.newHashMap();
        params.put("windowName", "测试窗口1");
        subMailMessage.setVars(params);
        dianxinUtil.sendVoice(subMailMessage,"AD7EC751-F01F-40A9-9FBD-3289CFD43660","1");
    }
}
