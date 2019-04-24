package com.deyatech.common.wechat;

import cn.hutool.json.JSONObject;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateData;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * 微信模板消息参数封装
 * </p>
 *
 * @author: lee.
 * @since: 2018-12-14 16:17
 */
@Data
@EqualsAndHashCode
@Accessors(chain = true)
public class WeChatTemplateParam implements Serializable{

    private String toUser;
    private String templateId;
    private String url;

    private List<WxMpTemplateData> dataList;
}
