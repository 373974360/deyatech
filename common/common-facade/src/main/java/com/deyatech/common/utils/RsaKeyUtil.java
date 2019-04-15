package com.deyatech.common.utils;

import cn.hutool.http.HttpStatus;
import com.deyatech.common.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;

import java.io.DataInputStream;
import java.io.InputStream;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 * <p>
 * RSA密钥工具类
 * </p>
 *
 * @author: lee.
 * @since: 2018-12-21 15:42
 */
@Slf4j
public class RsaKeyUtil {

    /**
     * 获取公钥
     *
     * @param filename
     * @return
     * @throws Exception
     */
    public static PublicKey getPublicKey(String filename) {
        byte[] keyBytes = getByteByFileName(filename);
        X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);
        PublicKey publicKey = null;
        try {
            KeyFactory kf = KeyFactory.getInstance("RSA");
            publicKey = kf.generatePublic(spec);
        } catch (Exception e) {
            log.error("获取公钥出错：", e);
            throw new BusinessException(HttpStatus.HTTP_INTERNAL_ERROR, "获取公钥出错", e);
        }
        return publicKey;
    }

    /**
     * 获取密钥
     *
     * @param filename
     * @return
     * @throws Exception
     */
    public static PrivateKey getPrivateKey(String filename) {
        byte[] keyBytes = getByteByFileName(filename);
        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);
        PrivateKey privateKey = null;
        try {
            KeyFactory kf = KeyFactory.getInstance("RSA");
            privateKey = kf.generatePrivate(spec);
        } catch (Exception e) {
            log.error("获取私钥出错：", e);
            throw new BusinessException(HttpStatus.HTTP_INTERNAL_ERROR, "获取私钥出错", e);
        }
        return privateKey;
    }


    private static byte[] getByteByFileName(String fileName) {
        byte[] keyBytes = null;
        try {
            ClassPathResource resource = new ClassPathResource(fileName);
            InputStream inputStream = resource.getInputStream();
            DataInputStream dis = new DataInputStream(inputStream);
            keyBytes = new byte[inputStream.available()];
            dis.readFully(keyBytes);
            dis.close();
        } catch (Exception e) {
            log.error("获取私钥或者公钥文件出错", e);
            throw new BusinessException(HttpStatus.HTTP_INTERNAL_ERROR, "获取私钥或者公钥文件出错", e);
        }
        return keyBytes;
    }
}
