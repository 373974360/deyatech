package org.land.common.jwt;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpStatus;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.land.common.Constants;
import org.land.common.exception.BusinessException;
import org.land.common.utils.RsaKeyUtil;

import java.util.Date;

/**
 * <p>
 * jwt工具类
 * </p>
 *
 * @author: lee.
 * @since: 2018-12-21 15:37
 */
@Slf4j
public class JwtUtil {

    /**
     * 密钥加密token
     *
     * @param jwtInfo
     * @param priKeyPath
     * @param expire
     * @return
     * @throws Exception
     */
    public static String generateToken(JwtInfo jwtInfo, String priKeyPath, Integer expire) {
        String compactJws = Jwts.builder()
                .setSubject(jwtInfo.getId())
                .claim(Constants.JWT_KEY_UNIQUENAME, jwtInfo.getUniqueName())
                .claim(Constants.JWT_KEY_NAME, jwtInfo.getName())
                .setExpiration(DateUtil.offsetSecond(new Date(), expire))
                .signWith(SignatureAlgorithm.RS256, RsaKeyUtil.getPrivateKey(priKeyPath))
                .compact();
        return compactJws;
    }

    /**
     * 公钥解析token
     *
     * @param token
     * @return
     * @throws Exception
     */
    public static Jws<Claims> parserToken(String token, String pubKeyPath) {
        Jws<Claims> claimsJws = Jwts.parser().setSigningKey(RsaKeyUtil.getPublicKey(pubKeyPath)).parseClaimsJws(token);
        return claimsJws;
    }

    /**
     * 获取token中的用户信息
     *
     * @param token
     * @param pubKeyPath
     * @return
     * @throws Exception
     */
    public static JwtInfo getInfoFromToken(String token, String pubKeyPath) {
        try {
            Jws<Claims> claimsJws = parserToken(token, pubKeyPath);
            Claims body = claimsJws.getBody();
            long buffer = (body.getExpiration().getTime() - System.currentTimeMillis()) / 1000;
            return new JwtInfo(body.getSubject(), StrUtil.toString(body.get(Constants.JWT_KEY_UNIQUENAME)), StrUtil.toString(body.get(Constants.JWT_KEY_NAME)), buffer);
        } catch (Exception e) {
            log.error("解析token信息出错：", e);
            throw new BusinessException(HttpStatus.HTTP_INTERNAL_ERROR, "解析token信息出错", e);
        }

    }
}
