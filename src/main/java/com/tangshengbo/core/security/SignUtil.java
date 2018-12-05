package com.tangshengbo.core.security;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tk.mybatis.mapper.util.StringUtil;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.util.Arrays;
import java.util.Set;

/**
 * Created by Tangshengbo on 2018/12/4
 */
public class SignUtil {

    private static Logger logger = LoggerFactory.getLogger(SignUtil.class);

    public static String sign(String content) throws Exception {
        PrivateKey privateKey = RSAConfig.getDefaultInstance().getKeyPair().getPrivate();
        byte[] contentBytes = content.getBytes("UTF-8");
        return Base64.encodeBase64String(RSAUtil.genSignature(contentBytes, privateKey, "SHA256withRSA"));
    }

    public static String signMesssage(String msg) throws Exception {
        JSONObject result = JSONObject.parseObject(msg);
        Set<String> respKeySet = result.keySet();
        respKeySet.remove("sign");
        respKeySet.remove("signed");
        String[] respKeysArr = respKeySet.toArray(new String[0]);
        Arrays.sort(respKeysArr);
        StringBuilder signedContent = new StringBuilder();

        for (String aRespKeysArr : respKeysArr) {
            Object object = result.get(aRespKeysArr);
            if (object != null && !StringUtil.isEmpty(object.toString())) {
                signedContent.append(aRespKeysArr).append("=").append(object.toString()).append("&");
            }
        }

        String signedContentStr = signedContent.toString();
        if (signedContentStr.endsWith("&")) {
            signedContentStr = signedContentStr.substring(0, signedContentStr.length() - 1);
        }

        String sign = sign(signedContentStr);
        result.put("sign", sign);
        result.put("signed", true);
        return result.toJSONString();
    }

    public static boolean verifySign(String content, String sign) throws Exception {
        logger.info("待验签字串:{}", content);
        PublicKey publicKey = RSAConfig.getDefaultInstance().getKeyPair().getPublic();
        Signature sig = Signature.getInstance("SHA256withRSA");
        sig.initVerify(publicKey);
        sig.update(content.getBytes("UTF-8"));
        return sig.verify(Base64.decodeBase64(sign));
    }

    public static boolean verifySign(String msg) throws Exception {
        JSONObject respObj = JSONObject.parseObject(msg);
        String respSign = respObj.getString("sign");
        Set<String> respKeySet = respObj.keySet();
        respKeySet.remove("sign");
        respKeySet.remove("signed");
        String[] respKeysArr = respKeySet.toArray(new String[0]);
        Arrays.sort(respKeysArr);
        StringBuilder respSignedContent = new StringBuilder();

        for (String aRespKeysArr : respKeysArr) {
            Object object = respObj.get(aRespKeysArr);
            if (object != null && !StringUtil.isEmpty(object.toString())) {
                respSignedContent.append(aRespKeysArr).append("=").append(object.toString()).append("&");
            }
        }

        String respSignedContentStr = respSignedContent.toString();
        if (respSignedContentStr.endsWith("&")) {
            respSignedContentStr = respSignedContentStr.substring(0, respSignedContentStr.length() - 1);
        }

        return verifySign(respSignedContentStr, respSign);
    }

    public static void main(String[] args) throws Exception {
        verifySign("");
    }
}
