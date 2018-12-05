package com.tangshengbo.core.security;

import com.alibaba.druid.util.Base64;
import com.google.common.collect.Maps;
import tk.mybatis.mapper.util.StringUtil;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.*;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Arrays;
import java.util.Map;

/**
 * Created by Tangshengbo on 2018/12/4
 */
public final class RSAUtil {

    public static final String DEFAULT_SIGN_ALGORITHM = "SHA256withRSA";
    public static final String KEY_ALGORITHM = "RSA";

    public static final String PUBLIC_KEY = "RSAPublicKey";//公钥
    public static final String PRIVATE_KEY = "RSAPrivateKey";//私钥

    public static final String DEFAULT_ENCODING = "UTF-8";


    private RSAUtil() {
    }

    public static byte[] genSignature(byte[] input, PrivateKey key, String signAlgorithm) throws NoSuchAlgorithmException, InvalidKeyException, SignatureException, IOException, NoSuchProviderException {
        signAlgorithm = StringUtil.isEmpty(signAlgorithm) ? "SHA256withRSA" : signAlgorithm;
        Signature sig = Signature.getInstance(signAlgorithm);
        sig.initSign(key);
        sig.update(input);
        return sig.sign();
    }

    public static byte[] genSignatureWithMD5(byte[] input, PrivateKey key) throws NoSuchAlgorithmException, InvalidKeyException, SignatureException, IOException, NoSuchProviderException {
        Signature sig = Signature.getInstance("MD5withRSA");
        sig.initSign(key);
        sig.update(input);
        return sig.sign();
    }

    public static byte[] genSignature(InputStream input, PrivateKey key, String signAlgorithm) throws NoSuchAlgorithmException, InvalidKeyException, SignatureException, IOException {
        signAlgorithm = StringUtil.isEmpty(signAlgorithm) ? "SHA256withRSA" : signAlgorithm;
        Signature sig = Signature.getInstance(signAlgorithm);
        sig.initSign(key);
        byte[] buffer = new byte[1024];

        int n;
        while((n = input.read(buffer)) >= 0) {
            sig.update(buffer, 0, n);
        }

        return sig.sign();
    }

    public static boolean verifySignature(byte[] plainInput, byte[] signedInput, PublicKey key, String signAlgorithm) throws NoSuchAlgorithmException, InvalidKeyException, SignatureException {
        signAlgorithm = StringUtil.isEmpty(signAlgorithm) ? "SHA256withRSA" : signAlgorithm;
        Signature sig = Signature.getInstance(signAlgorithm);
        sig.initVerify(key);
        sig.update(plainInput);
        return sig.verify(signedInput);
    }

    public static boolean verifySignatureSha256WithRSA(byte[] plainInput, byte[] signedInput, PublicKey key) throws NoSuchAlgorithmException, InvalidKeyException, SignatureException {
        Signature sig = Signature.getInstance("SHA256withRSA");
        sig.initVerify(key);
        sig.update(plainInput);
        return sig.verify(signedInput);
    }

    public static boolean verifySignature(InputStream plainInput, byte[] signedInput, PublicKey key, String signAlgorithm) throws NoSuchAlgorithmException, InvalidKeyException, IOException, SignatureException {
        signAlgorithm = StringUtil.isEmpty(signAlgorithm) ? "SHA256withRSA" : signAlgorithm;
        Signature sig = Signature.getInstance(signAlgorithm);
        sig.initVerify(key);
        byte[] buffer = new byte[1024];

        int n;
        while((n = plainInput.read(buffer)) >= 0) {
            sig.update(buffer, 0, n);
        }

        return sig.verify(signedInput);
    }

    public static byte[] encryptData(byte[] dataToEncrypt, PublicKey publicKey, String encryptAlgorithm) throws Exception {
        encryptAlgorithm = StringUtil.isEmpty(encryptAlgorithm) ? "RSA" : encryptAlgorithm;
        Cipher cipher = Cipher.getInstance(encryptAlgorithm);
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        return cipher.doFinal(dataToEncrypt);
    }

    public static void encryptData(InputStream dataToEncrypt, OutputStream encryptedOut, PublicKey publicKey, String encryptAlgorithm) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IOException, IllegalBlockSizeException, BadPaddingException {
        encryptAlgorithm = StringUtil.isEmpty(encryptAlgorithm) ? "RSA" : encryptAlgorithm;
        Cipher cipher = Cipher.getInstance(encryptAlgorithm);
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        byte[] buffer = new byte[117];

        for(int read = dataToEncrypt.read(buffer); read > 0; read = dataToEncrypt.read(buffer)) {
            if (read < 117) {
                encryptedOut.write(cipher.doFinal(Arrays.copyOfRange(buffer, 0, read)));
            } else {
                encryptedOut.write(cipher.doFinal(buffer));
            }
        }

        dataToEncrypt.close();
        encryptedOut.close();
    }

    public static void decryptData(InputStream dataToDecrypt, OutputStream decryptOut, PrivateKey privateKey, String encryptAlgorithm) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IOException, IllegalBlockSizeException, BadPaddingException {
        encryptAlgorithm = StringUtil.isEmpty(encryptAlgorithm) ? "RSA" : encryptAlgorithm;
        Cipher cipher = Cipher.getInstance(encryptAlgorithm);
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        byte[] buffer = new byte[128];

        for(int read = dataToDecrypt.read(buffer); read > 0; read = dataToDecrypt.read(buffer)) {
            if (read < 128) {
                decryptOut.write(cipher.doFinal(Arrays.copyOfRange(buffer, 0, read)));
            } else {
                decryptOut.write(cipher.doFinal(buffer));
            }
        }

        dataToDecrypt.close();
        decryptOut.close();
    }

    static byte[] decryptData(byte[] data, PrivateKey privateKey, String encryptAlgorithm) throws Exception {
        encryptAlgorithm = StringUtil.isEmpty(encryptAlgorithm) ? "RSA" : encryptAlgorithm;
        Cipher cipher = Cipher.getInstance(encryptAlgorithm);
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        return cipher.doFinal(data);
    }

    private static InputStream fileToInputStream(String filename) throws NoSuchAlgorithmException, IOException, InvalidKeySpecException {
        if (StringUtil.isEmpty(filename)) {
            return null;
        } else {
            InputStream fis = null;
            if (filename.contains("classpath:")) {
                filename = filename.replace("classpath:", "");
                if (filename.startsWith("/")) {
                    filename = filename.substring(1);
                }

                fis = RSAUtil.class.getClassLoader().getResourceAsStream(filename);
            } else if (filename.contains("file:")) {
                filename = filename.replace("file:", "");
                fis = new FileInputStream(filename);
            } else {
                fis = new FileInputStream(filename);
            }

            return fis;
        }
    }

    public static PublicKey readRSAPublicKeyFromFile(String filename) throws Exception {
        return StringUtil.isEmpty(filename) ? null : readRSAPublicKey(readRSAPublicKeyBytesFromFile(filename));
    }

    public static PublicKey readRSAPublicKey(String keyValue) {
        ByteArrayInputStream bip = new ByteArrayInputStream(keyValue.getBytes());
        return readRSAPublicKey(readRSAPublicKeyBytesFromStream(bip));
    }

    private static PublicKey readRSAPublicKey(byte[] keyBytes) {
        X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);

        try {
            KeyFactory kf = KeyFactory.getInstance("RSA");
            return kf.generatePublic(spec);
        } catch (Exception var4) {
            throw new RuntimeException("公钥加载失败", var4);
        }
    }

    private static byte[] readRSAPublicKeyBytesFromStream(InputStream isp) {
        try {
            InputStreamReader read = new InputStreamReader(isp);
            BufferedReader br = new BufferedReader(read);
            StringBuilder builder = new StringBuilder();
            boolean inKey = false;

            for(String line = br.readLine(); line != null; line = br.readLine()) {
                line = line.trim();
                if (!inKey) {
                    if (line.startsWith("-----BEGIN ") && line.endsWith(" PUBLIC KEY-----")) {
                        inKey = true;
                    }
                } else {
                    if (line.startsWith("-----END ") && line.endsWith(" PUBLIC KEY-----")) {
                        inKey = false;
                        break;
                    }

                    builder.append(line);
                }
            }

            isp.close();
            return Base64.base64ToByteArray(builder.toString());
        } catch (Exception var6) {
            throw new RuntimeException(var6);
        }
    }

    public static byte[] readRSAPublicKeyBytesFromFile(String filename) throws Exception {
        InputStream fis = fileToInputStream(filename);
        return readRSAPublicKeyBytesFromStream(fis);
    }

    public static PrivateKey readRSAPrivateKeyFromFile(String filename, boolean decode) {
        if (StringUtil.isEmpty(filename)) {
            return null;
        } else {
            try {
                return readRSAPrivateKey(readRSAPrivateKeyBytesFromFile(filename, decode));
            } catch (Exception var3) {
                throw new RuntimeException("加载私钥失败", var3);
            }
        }
    }

    public static PrivateKey readRSAPrivateKey(byte[] keyBytes) {
        try {
            PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);
            KeyFactory kf = KeyFactory.getInstance("RSA");
            return kf.generatePrivate(spec);
        } catch (Exception var4) {
            throw new RuntimeException("加载私钥失败", var4);
        }
    }

    public static byte[] readRSAPrivateKeyBytesFromFile(String fileName, boolean decode) throws Exception {
        InputStream inputStream = fileToInputStream(fileName);
        return readRSAPrivateKeyBytesFromStream(inputStream, decode);
    }

    public static byte[] readRSAPrivateKeyBytesFromStream(InputStream inputStream, boolean decode) throws Exception {
        InputStreamReader read = new InputStreamReader(inputStream);
        BufferedReader br = new BufferedReader(read);
        StringBuilder builder = new StringBuilder();
        boolean inKey = false;

        String line;
        for(line = br.readLine(); line != null; line = br.readLine()) {
            line = line.trim();
            if (!inKey) {
                if (line.startsWith("-----BEGIN ") && line.endsWith(" PRIVATE KEY-----")) {
                    inKey = true;
                }
            } else {
                if (line.startsWith("-----END ") && line.endsWith(" PRIVATE KEY-----")) {
                    inKey = false;
                    break;
                }

                builder.append(line);
            }
        }

        inputStream.close();
        line = null;
        byte[] keyBytes;
        if (!decode) {
            keyBytes = Base64.base64ToByteArray(builder.toString());
        } else {
            keyBytes = decodeBase64(builder.toString());
        }

        return keyBytes;
    }

    public static byte[] decodeBase64(String str) {
        org.apache.commons.codec.binary.Base64 base64 = new org.apache.commons.codec.binary.Base64();
        return base64.decode(str.getBytes());
    }

    /**
     * 用私钥加密
     *
     * @param data 加密数据
     * @param key  密钥
     * @return
     * @throws Exception
     */
    public static byte[] encryptByPrivateKey(byte[] data, String key) throws Exception {
        //解密密钥
        byte[] keyBytes = Coder.decryptBASE64(key);
        //取私钥
        PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        Key privateKey = keyFactory.generatePrivate(pkcs8EncodedKeySpec);

        //对数据加密
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.ENCRYPT_MODE, privateKey);

        return cipher.doFinal(data);
    }

    /**
     * 用私钥解密 * @param data 	加密数据
     *
     * @param key 密钥
     * @return
     * @throws Exception
     */
    public static byte[] decryptByPrivateKey(byte[] data, String key) throws Exception {
        //对私钥解密
        byte[] keyBytes = Coder.decryptBASE64(key);

        PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        Key privateKey = keyFactory.generatePrivate(pkcs8EncodedKeySpec);
        //对数据解密
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.DECRYPT_MODE, privateKey);

        return cipher.doFinal(data);
    }

    /**
     * 用公钥加密
     *
     * @param data 加密数据
     * @param key  密钥
     * @return
     * @throws Exception
     */
    public static byte[] encryptByPublicKey(byte[] data, String key) throws Exception {
        //对公钥解密
        byte[] keyBytes = Coder.decryptBASE64(key);
        //取公钥
        X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        Key publicKey = keyFactory.generatePublic(x509EncodedKeySpec);

        //对数据解密
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);

        return cipher.doFinal(data);
    }

    /**
     * 用公钥解密
     *
     * @param data 加密数据
     * @param key  密钥
     * @return
     * @throws Exception
     */
    public static byte[] decryptByPublicKey(byte[] data, String key) throws Exception {
        //对私钥解密
        byte[] keyBytes = Coder.decryptBASE64(key);
        X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        Key publicKey = keyFactory.generatePublic(x509EncodedKeySpec);

        //对数据解密
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.DECRYPT_MODE, publicKey);

        return cipher.doFinal(data);
    }

    /**
     *	用私钥对信息生成数字签名
     * @param data	//加密数据
     * @param privateKey	//私钥
     * @return
     * @throws Exception
     */
    public static String sign(byte[] data,String privateKey)throws Exception{
        //解密私钥
        byte[] keyBytes = Coder.decryptBASE64(privateKey);
        //构造PKCS8EncodedKeySpec对象
        PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(keyBytes);
        //指定加密算法
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        //取私钥匙对象
        PrivateKey privateKey2 = keyFactory.generatePrivate(pkcs8EncodedKeySpec);
        //用私钥对信息生成数字签名
        Signature signature = Signature.getInstance(DEFAULT_SIGN_ALGORITHM);
        signature.initSign(privateKey2);
        signature.update(data);

        return Coder.encryptBASE64(signature.sign());
    }

    /**
     * 校验数字签名
     * @param data	加密数据
     * @param publicKey	公钥
     * @param sign	数字签名
     * @return
     * @throws Exception
     */
    public static boolean verify(byte[] data,String publicKey,String sign)throws Exception{
        //解密公钥
        byte[] keyBytes = Coder.decryptBASE64(publicKey);
        //构造X509EncodedKeySpec对象
        X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(keyBytes);
        //指定加密算法
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        //取公钥匙对象
        PublicKey publicKey2 = keyFactory.generatePublic(x509EncodedKeySpec);

        Signature signature = Signature.getInstance(DEFAULT_SIGN_ALGORITHM);
        signature.initVerify(publicKey2);
        signature.update(data);
        //验证签名是否正常
        return signature.verify(Coder.decryptBASE64(sign));

    }

    /**
     * 取得公钥，并转化为String类型
     *
     * @param keyMap
     * @return
     * @throws Exception
     */
    public static String getPublicKey(Map<String, Object> keyMap) throws Exception {
        Key key = (Key) keyMap.get(PUBLIC_KEY);
        return Coder.encryptBASE64(key.getEncoded());
    }

    /**
     * 取得私钥，并转化为String类型
     *
     * @param keyMap
     * @return
     * @throws Exception
     */
    public static String getPrivateKey(Map<String, Object> keyMap) throws Exception {
        Key key = (Key) keyMap.get(PRIVATE_KEY);
        return Coder.encryptBASE64(key.getEncoded());
    }

    public static void main(String[] args) {
        try {
//            Map<String, Object> map = RSAUtil.initKey();
            KeyPair keyPair = RSAConfig.getDefaultInstance().getKeyPair();
            Map<String, Object> map = Maps.newHashMap();
            map.put(PRIVATE_KEY, keyPair.getPrivate());
            map.put(PUBLIC_KEY, keyPair.getPublic());
            String privateKey = getPrivateKey(map);
            String publicKey = getPublicKey(map);
            String source = "小唐";

            //私钥加密 -> 公钥解密
            byte[] encrypt = encryptByPrivateKey(source.getBytes(DEFAULT_ENCODING), privateKey);
            String encrpytStr = Coder.encryptBASE64(encrypt);
            System.out.println(encrpytStr);
            byte[] decrypt = decryptByPublicKey(Coder.decryptBASE64(encrpytStr), publicKey);
            String decryptStr = new String(decrypt, DEFAULT_ENCODING);
            System.out.println(decryptStr);
            System.out.println("----------------------------------------------------");
            //公钥加密 -> 私钥解密
            encrypt = encryptByPublicKey(source.getBytes(DEFAULT_ENCODING), publicKey);
            encrpytStr = Coder.encryptBASE64(encrypt);
            System.out.println(encrpytStr);
            decrypt = decryptByPrivateKey(Coder.decryptBASE64(encrpytStr), privateKey);
            decryptStr = new String(decrypt, DEFAULT_ENCODING);
            System.out.println(decryptStr);
            //私钥签名 -> 公钥验签
            String sign = sign(source.getBytes(DEFAULT_ENCODING), privateKey);
            System.out.println(sign);
            boolean verify = verify(source.getBytes(DEFAULT_ENCODING), publicKey, sign);
            System.out.println(verify);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
