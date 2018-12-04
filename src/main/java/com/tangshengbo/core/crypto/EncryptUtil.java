package com.tangshengbo.core.crypto;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.google.common.collect.Maps;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Date;
import java.util.Map;

/**
 * Created by Tangshengbo on 2018/12/4
 */
public final class EncryptUtil {

    private static Logger logger = LoggerFactory.getLogger(EncryptUtil.class);

    public static String encrypt(String msg) throws Exception {
        Map<String, Object> params = JSONObject.parseObject(msg,
                new TypeReference<Map<String, Object>>() {
                });
        if (params != null) {
            encrypt(params);
        }
        return JSONObject.toJSONString(params);
    }

    public static void encrypt(Map<String, Object> params) throws Exception {
        String encoding = "UTF-8";
        String content = (String) params.get("content");
        ByteArrayInputStream bti = new ByteArrayInputStream(content.getBytes(encoding));
        PublicKey publicKey = RSAConfig.getDefaultInstance().getCoopPublicKey();
        ByteArrayOutputStream bout = new ByteArrayOutputStream();
        byte[] key = AESUtil.generateKey(128);
        byte[] iv = AESUtil.generateRandomIV();

        try {
            AESUtil.encrypt(key, iv, bti, bout);
            bti = new ByteArrayInputStream(bout.toByteArray());
            String encryptKey = Base64.encodeBase64String(RSAUtil.encryptData(key, publicKey, "RSA"));
            String encryptKeyIv = Base64.encodeBase64String(RSAUtil.encryptData(iv, publicKey, "RSA"));
            params.put("encryptKey", encryptKey);
            params.put("encryptIv", encryptKeyIv);
        } catch (Exception var13) {
            throw new RuntimeException("值AES加密失败", var13);
        }

        ByteArrayOutputStream bout2 = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        try {
            int read = bti.read(buffer);

            while (true) {
                if (read <= 0) {
                    bti.close();
                    bout2.close();
                    break;
                }
                bout2.write(buffer, 0, read);
                read = bti.read(buffer);
            }
        } catch (IOException var14) {
            throw new RuntimeException("值Base64加密失败", var14);
        }

        byte[] returnByte = bout2.toByteArray();
        String contentStr = Base64.encodeBase64String(returnByte);
        params.put("content", contentStr);
    }

    public static void decrypt(Map<String, Object> params) throws Exception {
        String ivStr = (String) params.get("encryptIv");
        String keyStr = (String) params.get("encryptKey");
        String content = (String) params.get("content");
        if (!StringUtils.isEmpty(ivStr) && !StringUtils.isEmpty(keyStr) && !StringUtils.isEmpty(content)) {
            byte[] base64Content = Base64.decodeBase64(content);
            PrivateKey privateKey = RSAConfig.getDefaultInstance().getPrivateKey();
            byte[] key;
            byte[] iv;
            try {
                key = RSAUtil.decryptData(Base64.decodeBase64(keyStr), privateKey, "RSA");
                iv = RSAUtil.decryptData(Base64.decodeBase64(ivStr), privateKey, "RSA");
            } catch (Exception var12) {
                throw new RuntimeException("值解密失败，AES密钥解密失败", var12);
            }

            ByteArrayOutputStream bout = new ByteArrayOutputStream();
            ByteArrayInputStream bin = new ByteArrayInputStream(base64Content);

            try {
                AESUtil.decrypt(key, iv, bin, bout);
            } catch (Exception var11) {
                throw new RuntimeException("值RSA解密失败", var11);
            }

            base64Content = bout.toByteArray();
            String contentStr = new String(base64Content, "UTF-8");
            params.put("content", contentStr);
        }
    }

    public static String decrypt(String msg) throws Exception {
        Map<String, Object> params = JSONObject.parseObject(msg, new TypeReference<Map<String, Object>>() {
        });
        if (params != null) {
            decrypt(params);
        }
        return JSONObject.toJSONString(params);
    }

    public static void main(String[] args) throws Exception {
        JSONObject jsonContent = new JSONObject();
        jsonContent.put("name", "唐");
        jsonContent.put("age", "18");
        jsonContent.put("birthday", new Date());
        String original = jsonContent.toJSONString();
        logger.info("原始数据:{}", original);

        Map<String, Object> params = Maps.newLinkedHashMap();
        params.put("content", original);
        String encryptStr = encrypt(JSON.toJSONString(params));
        logger.info("加密后:{}", encryptStr);

        Map<String, Object> ecParams = JSON.parseObject(encryptStr);
        String sign = SignUtil.sign(original);
        logger.info("原始数据签名:{}", sign);
        ecParams.put("sign", sign);
        logger.info("最终加密数据:{}", ((JSONObject) ecParams).toJSONString());


        boolean isSuccess = SignUtil.verifySign(original, ecParams.get("sign").toString());
        logger.info("验签结果:{}", isSuccess);

        String decryptMsg = decrypt(JSON.toJSONString(ecParams));
        logger.info("解密结果:{}", decryptMsg);

    }
}
