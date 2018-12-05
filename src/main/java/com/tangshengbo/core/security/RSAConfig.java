package com.tangshengbo.core.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.KeyPair;

/**
 * Created by Tangshengbo on 2018/12/4
 */
public final class RSAConfig {

    private static Logger logger = LoggerFactory.getLogger(RSAConfig.class);

    private KeyPair keyPair;

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

    public KeyPair getKeyPair() {
        return keyPair;
    }

    public void setKeyPair(KeyPair keyPair) {
        this.keyPair = keyPair;
    }

}
