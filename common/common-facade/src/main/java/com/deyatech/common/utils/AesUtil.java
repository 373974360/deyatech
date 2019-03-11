package com.deyatech.common.utils;

import cn.hutool.core.util.CharsetUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.symmetric.AES;

/**
 * <p>
 * AES加密解密工具类
 * </p>
 *
 * @author: lee.
 * @since: 2018-12-19 20:58
 */
public class AesUtil {

    //密钥 (需要前端和后端保持一致)
    private static final String key = "deyatech";

    /**
     * aes解密
     *
     * @param encrypt 内容
     * @return
     * @throws Exception
     */
    public static String aesDecrypt(String encrypt) {
        AES aes = SecureUtil.aes(key.concat(key).getBytes());
        return aes.decryptStr(encrypt, CharsetUtil.CHARSET_UTF_8);
    }

    /**
     * aes加密
     *
     * @param content
     * @return
     * @throws Exception
     */
    public static String aesEncrypt(String content) {
        AES aes = SecureUtil.aes(key.concat(key).getBytes());
        return aes.encryptHex(content);
    }

    /**
     * 测试
     */
    public static void main(String[] args) throws Exception {
        String content = "你说我说的对不对";
        System.out.println("加密前：" + content);
        String encrypt = aesEncrypt(content);
        System.out.println("加密后：" + encrypt);
        String decrypt = aesDecrypt(encrypt);
        System.out.println("解密后：" + decrypt);
    }
}
