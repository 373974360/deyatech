package com.deyatech.common.utils;


import cn.hutool.core.util.StrUtil;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

public class URLDecoderUtil {

    public static String decode(String encode) {
        if (StrUtil.isNotBlank(encode)) {
            try {
                encode = URLDecoder.decode(encode, "utf-8");
                encode = URLDecoder.decode(encode, "utf-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return encode;
    }
}
