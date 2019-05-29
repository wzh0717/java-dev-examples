package com.wangzh.app.commons.security;

import org.bouncycastle.util.encoders.Base64;

import javax.crypto.Cipher;

import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;

/**
 * @Description:AES对称加密
 * @CreatedDate:2019-03-25 16:21
 * @Author:wangzh
 */
public abstract class AESUtils {
    private static final String AES_TYPE = "AES";
    private static final String DEFAULT_CIPHER_ALGORITHM = "AES/CBC/PKCS5Padding";
    private static final byte[] iv = {0x30, 0x31, 0x30, 0x32, 0x30, 0x33, 0x30, 0x34, 0x30, 0x35, 0x30, 0x36, 0x30, 0x37, 0x30, 0x38};
    private static final String CHARSET_NAME = "UTF-8";

    /**
     * 生成加密密钥
     *
     * @param password
     * @return
     */
    private static SecretKeySpec getSecretKey(final String password) throws UnsupportedEncodingException {
        int keyLength = 128;//256 长度超
        byte[] keyBytes = new byte[keyLength / 8];
        Arrays.fill(keyBytes, (byte) 0x0);
        byte[] passwordBytes = password.getBytes(CHARSET_NAME);
        int length = passwordBytes.length < keyBytes.length ? passwordBytes.length : keyBytes.length;
        System.arraycopy(passwordBytes, 0, keyBytes, 0, length);
        SecretKeySpec key = new SecretKeySpec(keyBytes, AES_TYPE);
        return key;
    }

    /**
     * AES 加密
     *
     * @param content
     * @param password
     * @return
     */
    public static String encrypt(String content, String password) throws Exception {
        byte[] clearTextBytes = content.getBytes(CHARSET_NAME);
        //偏移向量
        IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);
        Cipher cipher = Cipher.getInstance(DEFAULT_CIPHER_ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, getSecretKey(password), ivParameterSpec);
        return new String(Base64.encode(cipher.doFinal(clearTextBytes)));
    }

    /**
     * AES 解密
     *
     * @param content
     * @param password
     * @return
     */
    public static String decrypt(String content, String password) throws Exception {
        IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);
        Cipher cipher = Cipher.getInstance(DEFAULT_CIPHER_ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, getSecretKey(password), ivParameterSpec);
        return new String(cipher.doFinal(Base64.decode(content.getBytes())));
    }
}