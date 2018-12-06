package com.tangshengbo.core.security;

import com.alibaba.druid.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.SecureRandom;
import java.util.Arrays;

/**
 * Created by Tangshengbo on 2018/12/4
 */
public final class AESUtil {

    private static final String AES = "AES";
    private static final String AES_CHARSET_NAME = "UTF-8";
    private static final String AES_CBC = "AES/CBC/PKCS5Padding";

    private AESUtil() {
    }

    public static byte[] encrypt(byte[] key, byte[] ivByte, byte[] value) throws Exception {
        SecureRandom sr = new SecureRandom();
        SecretKey secureKey = new SecretKeySpec(key, "AES");
        IvParameterSpec iv = new IvParameterSpec(ivByte);
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(1, secureKey, iv, sr);
        return cipher.doFinal(value);
    }

    public static byte[] encrypt(byte[] key, byte[] value) throws Exception {
        SecretKey secureKey = new SecretKeySpec(key, "AES");
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(1, secureKey);
        return cipher.doFinal(value);
    }

    public static void encrypt(byte[] key, byte[] ivByte, InputStream dataToEncrypt, OutputStream encryptedOut) throws Exception {
        SecureRandom sr = new SecureRandom();
        SecretKey secureKey = new SecretKeySpec(key, "AES");
        IvParameterSpec iv = new IvParameterSpec(ivByte);
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, secureKey, iv, sr);
        byte[] buffer = new byte[1024];

        for (int read = dataToEncrypt.read(buffer); read > 0; read = dataToEncrypt.read(buffer)) {
            if (read < 1024) {
                encryptedOut.write(cipher.doFinal(Arrays.copyOfRange(buffer, 0, read)));
            } else {
                encryptedOut.write(cipher.doFinal(buffer));
            }
        }

        dataToEncrypt.close();
        encryptedOut.close();
    }

    public static byte[] decrypt(byte[] key, byte[] ivByte, byte[] value) throws Exception {
        SecureRandom sr = new SecureRandom();
        SecretKey secureKey = new SecretKeySpec(key, "AES");
        IvParameterSpec iv = new IvParameterSpec(ivByte);
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, secureKey, iv, sr);
        return cipher.doFinal(value);
    }

    public static byte[] decrypt(byte[] key, byte[] value) throws Exception {
        SecretKey secureKey = new SecretKeySpec(key, "AES");
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(2, secureKey);
        return cipher.doFinal(value);
    }

    public static void decrypt(byte[] key, byte[] ivByte, InputStream dataToDecrypt, OutputStream decryptOut) throws Exception {
        SecureRandom sr = new SecureRandom();
        SecretKey secureKey = new SecretKeySpec(key, "AES");
        IvParameterSpec iv = new IvParameterSpec(ivByte);
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(2, secureKey, iv, sr);
        byte[] buffer = new byte[1040];

        for (int read = dataToDecrypt.read(buffer); read > 0; read = dataToDecrypt.read(buffer)) {
            if (read < 1040) {
                decryptOut.write(cipher.doFinal(Arrays.copyOfRange(buffer, 0, read)));
            } else {
                decryptOut.write(cipher.doFinal(buffer));
            }
        }

        dataToDecrypt.close();
        decryptOut.close();
    }

    public static String byte2Hex(byte[] b) {
        String hs = "";
        String stmp = "";

        for (int n = 0; n < b.length; ++n) {
            stmp = Integer.toHexString(b[n] & 255);
            if (stmp.length() == 1) {
                hs = hs + "0" + stmp;
            } else {
                hs = hs + stmp;
            }

            if (n < b.length - 1) {
                hs = hs + ":";
            }
        }

        return hs.toUpperCase();
    }

    public static byte[] generateKey(int length) {
        try {
            KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
            keyGenerator.init(length);
            SecretKey key = keyGenerator.generateKey();
            return key.getEncoded();
        } catch (Exception var3) {
            throw new RuntimeException(var3);
        }
    }

    public static byte[] generateRandomIV() {
        try {
            KeyGenerator keyGen = KeyGenerator.getInstance("AES");
            keyGen.init(new SecureRandom());
            SecretKey randomDESKey = keyGen.generateKey();
            return randomDESKey.getEncoded();
        } catch (Exception var2) {
            throw new RuntimeException(var2);
        }
    }

    public static void main(String[] args) throws Exception {
        System.out.println("**********密码加密小工具   ***************");
        //密钥长度16个字节->128bit
        byte[] default_Key = "qwertyuiopasd唐".getBytes();
        System.out.println(default_Key.length);
        System.out.println("密钥:" + Base64.byteArrayToBase64(default_Key));
        System.out.println("密钥:" + java.util.Base64.getEncoder().encodeToString(default_Key));
        String source = "小唐";
        System.out.println("原数据:" + source);
        byte[] encrypt = encrypt(default_Key,source.getBytes(AES_CHARSET_NAME));
        System.out.println("加密后:" + Base64.byteArrayToBase64(encrypt));

        byte[] decrypt = decrypt(default_Key, encrypt);
        String decryptStr = new String(decrypt, AES_CHARSET_NAME);
        System.out.println("解密后:" + decryptStr);
        System.out.println("***********************************************");
    }
}
