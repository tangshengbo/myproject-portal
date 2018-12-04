package com.tangshengbo.core.crypto;

import com.alibaba.druid.util.Base64;
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

/**
 * Created by Tangshengbo on 2018/12/4
 */
public final class RSAUtil {

    public static final String DEFAULT_SIGN_ALGORITHM = "SHA256withRSA";
    public static final String DEFAULT_ENCRYPT_ALGORITHM = "RSA";

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

    public static byte[] encryptData(byte[] dataToEncrypt, PublicKey publicKey, String encryptAlgorithm) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
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

    static byte[] decryptData(byte[] data, PrivateKey privateKey, String encryptAlgorithm) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
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

    public static PublicKey readRSAPublicKeyFromFile(String filename) throws NoSuchAlgorithmException, IOException, InvalidKeySpecException {
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

    public static byte[] readRSAPublicKeyBytesFromFile(String filename) throws NoSuchAlgorithmException, IOException, InvalidKeySpecException {
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

    public static byte[] readRSAPrivateKeyBytesFromFile(String fileName, boolean decode) throws NoSuchAlgorithmException, IOException, InvalidKeySpecException {
        InputStream inputStream = fileToInputStream(fileName);
        return readRSAPrivateKeyBytesFromStream(inputStream, decode);
    }

    public static byte[] readRSAPrivateKeyBytesFromStream(InputStream inputStream, boolean decode) throws NoSuchAlgorithmException, IOException, InvalidKeySpecException {
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

}
