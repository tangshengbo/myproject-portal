package com.tangshengbo.core.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Properties;

/**
 * Created by Tangshengbo on 2018/12/4
 */
public class RSAConfigLoader {

    private static Logger logger = LoggerFactory.getLogger(RSAConfigLoader.class);

    RSAConfigLoader() {
    }

    RSAConfig loadAppConfig() {
        return this.loadFile();
    }

    private RSAConfig loadFile() {
        RSAConfig config = new RSAConfig();
        InputStream configFile = null;

        try {
            configFile = RSAConfigLoader.class.getClassLoader().getResourceAsStream("config.properties");
            if (configFile == null) {
                configFile = RSAConfigLoader.class.getClassLoader().getResourceAsStream("properties/config.properties");
            }

            Properties prop = new Properties();
            prop.load(configFile);
            configFile.close();

            String privateKeyPath = prop.getProperty("config.privateKeyPath");
            PrivateKey privateKey = RSAUtil.readRSAPrivateKeyFromFile(privateKeyPath, true);

            String publicKeyPath = prop.getProperty("config.publicKeyPath");
            PublicKey publicKey = RSAUtil.readRSAPublicKeyFromFile(publicKeyPath);
            config.setKeyPair(new KeyPair(publicKey, privateKey));
            return config;
        } catch (Exception var10) {
            throw new RuntimeException("加载config配置文件失败", var10);
        }
    }
}
