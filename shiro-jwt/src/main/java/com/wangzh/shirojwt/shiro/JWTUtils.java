package com.wangzh.shirojwt.shiro;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Description:
 * @Auther:wangzh
 * @Date: 2019/05/29 18:22
 */

public class JWTUtils {

    private static final Logger log = LoggerFactory.getLogger(JWTUtils.class);

    /**
     * 签名过期时间
     */
    private static final long EXPIRE_TIME = 5 * 60 * 1000;
}
