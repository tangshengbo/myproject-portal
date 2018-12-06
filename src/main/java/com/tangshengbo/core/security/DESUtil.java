package com.tangshengbo.core.security;

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
import java.lang.reflect.Method;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;

/**
 * Created by Tangshengbo on 2018/12/6
 */
public class DESUtil {

    private static final String DESEDE = "DESede";
    private static final String DESEDE_CHARSET_NAME = "UTF-8";
    private static final String DES_CBC = "DESede/CBC/PKCS5Padding";

    public DESUtil() {
    }

    public static byte[] encrypt(byte[] key, byte[] ivByte, byte[] value) throws Exception {
        try {
            SecureRandom sr = new SecureRandom();
            SecretKey secureKey = new SecretKeySpec(key, "DESede");
            IvParameterSpec iv = new IvParameterSpec(ivByte);
            Cipher cipher = Cipher.getInstance("DESede/CBC/PKCS5Padding");
            cipher.init(1, secureKey, iv, sr);
            return cipher.doFinal(value);
        } catch (Exception e) {
            throw e;
        }
    }

    public static void encrypt(byte[] key, byte[] ivByte, InputStream dataToEncrypt, OutputStream encryptedOut) throws Exception {
        SecureRandom sr = new SecureRandom();
        SecretKey secureKey = new SecretKeySpec(key, "DESede");
        IvParameterSpec iv = new IvParameterSpec(ivByte);
        Cipher cipher = Cipher.getInstance("DESede/CBC/PKCS5Padding");
        cipher.init(1, secureKey, iv, sr);

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
            SecretKey secureKey = new SecretKeySpec(key, "DESede");
            IvParameterSpec iv = new IvParameterSpec(ivByte);
            Cipher cipher = Cipher.getInstance("DESede/CBC/PKCS5Padding");
            cipher.init(2, secureKey, iv, sr);
            return cipher.doFinal(value);
        } catch (Exception e) {
            throw e;
        }
    }

    public static void decrypt(byte[] key, byte[] ivByte, InputStream dataToDecrypt, OutputStream decryptOut) throws Exception {
        try {
            SecureRandom sr = new SecureRandom();
            SecretKey secureKey = new SecretKeySpec(key, "DESede");
            IvParameterSpec iv = new IvParameterSpec(ivByte);
            Cipher cipher = Cipher.getInstance("DESede/CBC/PKCS5Padding");
            cipher.init(2, secureKey, iv, sr);
            byte[] buffer = new byte[1032];

            for (int read = dataToDecrypt.read(buffer); read > 0; read = dataToDecrypt.read(buffer)) {
                if (read < 1032) {
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


    public static byte[] generateKey() throws NoSuchAlgorithmException {
        KeyGenerator keyGen = KeyGenerator.getInstance("DESede");
        keyGen.init(new SecureRandom());
        SecretKey key = keyGen.generateKey();
        return key.getEncoded();
    }

    public static byte[] generateRandomIV() throws NoSuchAlgorithmException {
        KeyGenerator keyGen = KeyGenerator.getInstance("DES");
        keyGen.init(new SecureRandom());
        SecretKey randomDESKey = keyGen.generateKey();
        return randomDESKey.getEncoded();
    }

    public static void main(String[] args) throws Exception {
        byte[] defaultKey = generateKey();
        byte[] ivKey = generateRandomIV();
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("***********************************************");
        System.out.println("**********密码加密小工具   ***************");
        System.out.print("*** 请输入密码原文：");
        String sSrcPwd = "w5hQ4/1rtiTRKVRbE0iYDg==";

        while (true) {
            sSrcPwd = br.readLine();
            if (sSrcPwd != null && sSrcPwd.length() > 0) {
                byte[] encrypt = encrypt(defaultKey, ivKey, sSrcPwd.getBytes());
                String sPwd = Base64.byteArrayToBase64(encrypt);
                System.out.println("*** 加密后密文：{" + sPwd + "}");
                System.out.println("***********************************************");
                byte[] decodePwd = Base64.base64ToByteArray(sPwd);
//                decodePwd = decrypt(defaultKey, ivKey, decodePwd);
                Method decrypt = DESUtil.class.getDeclaredMethod("decrypt", byte[].class, byte[].class, byte[].class);
                decodePwd = (byte[]) decrypt.invoke(null, defaultKey, ivKey, decodePwd);
                System.out.println("*** 解密后原文：{" + new String(decodePwd, "UTF-8") + "}");
                System.out.println("***********************************************");
//                return;
            }
            System.out.print("*** 请输入密码原文：");
        }
    }
}
