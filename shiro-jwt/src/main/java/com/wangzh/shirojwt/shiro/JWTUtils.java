package com.wangzh.shirojwt.shiro;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Date;

/**
 * @Description:
 * @Auther:wangzh
 * @Date: 2019/05/29 18:22
 */

public class JWTUtils {

    /**
     * 签名过期时间
     */
    private static final long EXPIRE_TIME = 5 * 60 * 1000;


    /**
     * 生成签名 默认5分钟过期
     *
     * @param userName 用户名
     * @param secret   密码
     * @return
     */
    public static String signature(String userName, String secret) {
        Date date = new Date(System.currentTimeMillis() + EXPIRE_TIME);
        Algorithm algorithm = Algorithm.HMAC256(secret);
        // 附带username信息
        return JWT.create()
                .withClaim("userName", userName)
                .withExpiresAt(date)
                .sign(algorithm);
    }

    /**
     * 验证token
     *
     * @param token
     * @param userName
     * @param secret
     * @return
     */
    public static boolean verify(String token, String userName, String secret) {
        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(secret))
                .withClaim("userName", userName)
                .build();
        DecodedJWT jwt = verifier.verify(token);
        return true;
    }

    /**
     * 获取token中的用户名
     *
     * @param token
     * @return
     */
    public static String getUserName(String token) {
        DecodedJWT jwt = JWT.decode(token);
        return jwt.getClaim("userName").asString();
    }
}
