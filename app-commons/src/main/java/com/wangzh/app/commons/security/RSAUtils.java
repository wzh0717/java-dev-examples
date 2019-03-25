package com.wangzh.app.commons.security;

import org.apache.commons.codec.binary.Base64;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import javax.crypto.Cipher;
import java.io.ByteArrayOutputStream;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 * @Description: RSA加密解密 签名 验签
 * @CreatedDate:2019-02-18 11:16
 * @Author:wangzh
 */
public abstract class RSAUtils {
    /**
     * 最大加密明文大小
     */
    private static final int MAX_ENCRYPT_BLOCK = 117;
    private static final String RSA_TYPE = "RSA";
    private static final String MD5_RSA_TYPE = "MD5withRSA";
    /**
     * 最大解密密文大小
     */
    private static final int MAX_DECRYPT_BLOCK = 128;
    /**
     * 密钥长度
     */
    private static final int KEY_SIZE = 1024;

    /**
     * UTF-8
     */
    private static final String CHARSET_UTF8 = "UTF-8";

    /**
     * 获取密钥对
     *
     * @return
     * @throws Exception
     */
    public static KeyPairInfo getKeyPairInfo() throws Exception {
        KeyPair keyPair = getKeyPair();
        KeyPairInfo pairInfo = new KeyPairInfo();
        pairInfo.setPublicKey(new String(Base64.encodeBase64(keyPair.getPublic().getEncoded())));
        pairInfo.setPrivateKey(new String(Base64.encodeBase64(keyPair.getPrivate().getEncoded())));
        return pairInfo;
    }

    /**
     * 获取密钥对
     *
     * @return
     * @throws Exception
     */
    private static KeyPair getKeyPair() throws NoSuchAlgorithmException {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(RSA_TYPE);
        keyPairGenerator.initialize(KEY_SIZE);
        return keyPairGenerator.generateKeyPair();
    }

    /**
     * 获取公钥
     *
     * @param publicKey
     * @return
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     */
    public static PublicKey getPublicKey(String publicKey) throws NoSuchAlgorithmException, InvalidKeySpecException {
        KeyFactory keyFactory = KeyFactory.getInstance(RSA_TYPE);
        byte[] decodedKey = Base64.decodeBase64(publicKey.getBytes());
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(decodedKey);
        return keyFactory.generatePublic(keySpec);
    }

    /**
     * 获取私钥
     *
     * @param privateKey
     * @return
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     */
    public static PrivateKey getPrivateKey(String privateKey) throws NoSuchAlgorithmException, InvalidKeySpecException {
        KeyFactory keyFactory = KeyFactory.getInstance(RSA_TYPE);
        byte[] decodedKey = Base64.decodeBase64(privateKey.getBytes());
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(decodedKey);
        return keyFactory.generatePrivate(keySpec);
    }

    /**
     * RSA 加密
     *
     * @param data
     * @param publicKey
     * @return
     * @throws Exception
     */
    public static String encrypt(String data, PublicKey publicKey) throws Exception {
        Cipher cipher = Cipher.getInstance(RSA_TYPE, BouncyCastleProvider.PROVIDER_NAME);
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        int inputLen = data.getBytes().length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offset = 0;
        byte[] cache;
        int i = 0;
        // 对数据分段加密
        while (inputLen - offset > 0) {
            if (inputLen - offset > MAX_ENCRYPT_BLOCK) {
                cache = cipher.doFinal(data.getBytes(), offset, MAX_ENCRYPT_BLOCK);
            } else {
                cache = cipher.doFinal(data.getBytes(), offset, inputLen - offset);
            }
            out.write(cache, 0, cache.length);
            i++;
            offset = i * MAX_ENCRYPT_BLOCK;
        }
        byte[] encryptedData = out.toByteArray();
        out.close();
        // 获取加密内容使用base64进行编码,并以UTF-8为标准转化成字符串
        return new String(Base64.encodeBase64String(encryptedData));
    }

    /**
     * RSA 解密
     *
     * @param data
     * @param privateKey
     * @return
     * @throws Exception
     */
    public static String decrypt(String data, PrivateKey privateKey) throws Exception {
        Cipher cipher = Cipher.getInstance(RSA_TYPE, BouncyCastleProvider.PROVIDER_NAME);
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        byte[] dataBytes = Base64.decodeBase64(data);
        int inputLen = dataBytes.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offset = 0;
        byte[] cache;
        int i = 0;
        // 对数据分段解密
        while (inputLen - offset > 0) {
            if (inputLen - offset > MAX_DECRYPT_BLOCK) {
                cache = cipher.doFinal(dataBytes, offset, MAX_DECRYPT_BLOCK);
            } else {
                cache = cipher.doFinal(dataBytes, offset, inputLen - offset);
            }
            out.write(cache, 0, cache.length);
            i++;
            offset = i * MAX_DECRYPT_BLOCK;
        }
        byte[] decryptedData = out.toByteArray();
        out.close();
        // 解密后的内容
        return new String(decryptedData, CHARSET_UTF8);
    }

    /**
     * 签名
     *
     * @param data       待签名数据
     * @param privateKey 私钥
     * @return 签名
     * @throws Exception
     */
    public static String sign(String data, PrivateKey privateKey) throws Exception {
        byte[] keyBytes = privateKey.getEncoded();
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(RSA_TYPE);
        PrivateKey key = keyFactory.generatePrivate(keySpec);
        Signature signature = Signature.getInstance(MD5_RSA_TYPE);
        signature.initSign(key);
        signature.update(data.getBytes());
        return new String(Base64.encodeBase64(signature.sign()));
    }

    /**
     * 验签
     *
     * @param srcData   原始字符串
     * @param publicKey 公钥
     * @param sign      签名
     * @return 是否验证通过
     * @throws Exception
     */
    public static boolean verify(String srcData, PublicKey publicKey, String sign) throws Exception {
        byte[] keyBytes = publicKey.getEncoded();
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(RSA_TYPE);
        PublicKey key = keyFactory.generatePublic(keySpec);
        Signature signature = Signature.getInstance(MD5_RSA_TYPE);
        signature.initVerify(key);
        signature.update(srcData.getBytes());
        return signature.verify(Base64.decodeBase64(sign.getBytes()));
    }

    /**
     * main
     *
     * @param args
     */
    public static void main(String[] args) {
        try {
            KeyPairInfo pairInfo = getKeyPairInfo();
            String publicKey = pairInfo.getPublicKey();
            String privateKey = pairInfo.getPrivateKey();
            System.out.println("私钥:" + privateKey);
            System.out.println("公钥:" + publicKey);

            String data = "测试加密的文字内容";
            //RSA 加密
            String encryptData = encrypt(data, getPublicKey(publicKey));
            System.out.println("加密后内容:" + encryptData);

            // RSA解密
            String decryptData = decrypt(encryptData, getPrivateKey(privateKey));
            System.out.println("解密后内容:" + decryptData);

            // RSA签名
            String signStr = sign(data, getPrivateKey(privateKey));
            System.out.println("签名：" + signStr);
            // RSA验签
            boolean result = verify(data, getPublicKey(publicKey), signStr);
            System.out.print("验签结果:" + result);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * 密钥对
     */
    public static class KeyPairInfo {
        private String publicKey;
        private String privateKey;

        public String getPublicKey() {
            return publicKey;
        }

        public void setPublicKey(String publicKey) {
            this.publicKey = publicKey;
        }

        public String getPrivateKey() {
            return privateKey;
        }

        public void setPrivateKey(String privateKey) {
            this.privateKey = privateKey;
        }
    }
}