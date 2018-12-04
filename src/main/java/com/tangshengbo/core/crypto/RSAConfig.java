package com.tangshengbo.core.crypto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.PrivateKey;
import java.security.PublicKey;

/**
 * Created by Tangshengbo on 2018/12/4
 */
public final class RSAConfig {

    private static Logger logger = LoggerFactory.getLogger(RSAConfig.class);
    private PrivateKey privateKey;
    private PublicKey coopPublicKey;
    private static RSAConfig instance = null;

    RSAConfig() {

    }

    public static RSAConfig getDefaultInstance() {
        if (instance == null) {
            RSAConfigLoader builder = new RSAConfigLoader();
            instance = builder.loadAppConfig();
        }
        return instance;
    }

    public byte[] getAESKey(int keyLength) {
        return AESUtil.generateKey(keyLength);
    }

    public PrivateKey getPrivateKey() {
        return this.privateKey;
    }

    public void setPrivateKey(PrivateKey privateKey) {
        this.privateKey = privateKey;
    }

    public PublicKey getCoopPublicKey() {
        return this.coopPublicKey;
    }

    public void setCoopPublicKey(PublicKey publicKey) {
        this.coopPublicKey = publicKey;
    }
}
