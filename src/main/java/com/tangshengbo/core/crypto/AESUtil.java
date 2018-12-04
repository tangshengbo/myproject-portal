package com.tangshengbo.core.crypto;

import com.alibaba.druid.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
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
        try {
            SecureRandom sr = new SecureRandom();
            SecretKey securekey = new SecretKeySpec(key, "AES");
            IvParameterSpec iv = new IvParameterSpec(ivByte);
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(1, securekey, iv, sr);
            return cipher.doFinal(value);
        } catch (Exception var7) {
            throw var7;
        }
    }

    public static byte[] encrypt(byte[] key, byte[] value) throws Exception {
        try {
            SecretKey securekey = new SecretKeySpec(key, "AES");
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(1, securekey);
            return cipher.doFinal(value);
        } catch (Exception var4) {
            throw var4;
        }
    }

    public static void encrypt(byte[] key, byte[] ivByte, InputStream dataToEncrypt, OutputStream encryptedOut) throws Exception {
        SecureRandom sr = new SecureRandom();
        SecretKey securekey = new SecretKeySpec(key, "AES");
        IvParameterSpec iv = new IvParameterSpec(ivByte);
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(1, securekey, iv, sr);
        try {
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
        } catch (Exception var10) {
            throw var10;
        }
    }

    public static byte[] decrypt(byte[] key, byte[] ivByte, byte[] value) throws Exception {
        try {
            SecureRandom sr = new SecureRandom();
            SecretKey securekey = new SecretKeySpec(key, "AES");
            IvParameterSpec iv = new IvParameterSpec(ivByte);
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(2, securekey, iv, sr);
            return cipher.doFinal(value);
        } catch (Exception var7) {
            throw var7;
        }
    }

    public static byte[] decrypt(byte[] key, byte[] value) throws Exception {
        try {
            SecretKey securekey = new SecretKeySpec(key, "AES");
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(2, securekey);
            return cipher.doFinal(value);
        } catch (Exception var4) {
            throw var4;
        }
    }

    public static void decrypt(byte[] key, byte[] ivByte, InputStream dataToDecrypt, OutputStream decryptOut) throws Exception {
        try {
            SecureRandom sr = new SecureRandom();
            SecretKey securekey = new SecretKeySpec(key, "AES");
            IvParameterSpec iv = new IvParameterSpec(ivByte);
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(2, securekey, iv, sr);
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
        } catch (Exception var10) {
            throw var10;
        }
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
        byte[] default_Key = generateKey(128);
        String x = new String(default_Key, "UTF-8");
        byte[] Key = x.getBytes("UTF-8");
        new BufferedReader(new InputStreamReader(System.in));
        System.out.println("***********************************************" + Base64.byteArrayToBase64(default_Key));
        System.out.println("**********密码加密小工具   ***************");
        System.out.print("*** 请输入密码原文：");
    }
}
