package com.tangshengbo.core.crypto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
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
            config.setPrivateKey(RSAUtil.readRSAPrivateKeyFromFile(privateKeyPath, true));

            String coopPublicKeyPath = prop.getProperty("config.publicKeyPath");
            config.setCoopPublicKey(RSAUtil.readRSAPublicKeyFromFile(coopPublicKeyPath));
            return config;
        } catch (Exception var10) {
            throw new RuntimeException("加载config配置文件失败", var10);
        }
    }
}
