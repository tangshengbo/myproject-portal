package com.tangshengbo.core;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.Properties;

public class EmailUtils implements Runnable {
    /**
     * @EMAIL_PORT 邮箱端口
     * @EMAIL_CONFIG_LOCATION 配置文件地址
     */
    private static final int EMAIL_PORT = 25;
    private static final String EMAIL_CONFIG_LOCATION = "Email.properties";
    private static Properties PROPERTIES = null;
    /**
     * 用户邮箱
     * 验证码
     */
    private String userEmail;
    private String identifyCode;

    static {
        /**
         * 加载配置文件
         */
        try {
            InputStream inputStream = EmailUtils.class.getClassLoader().getResourceAsStream(EMAIL_CONFIG_LOCATION);
            PROPERTIES = new Properties();
            PROPERTIES.load(inputStream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        sendEmail();
    }

    private void sendEmail() {
        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
        javaMailSender.setHost("smtp.126.com");
        javaMailSender.setUsername(PROPERTIES.getProperty("JWCEmail"));
        javaMailSender.setPassword(PROPERTIES.getProperty("JWCPassword"));
        javaMailSender.setPort(EMAIL_PORT);
        javaMailSender.setDefaultEncoding("UTF-8");
        /**
         * @simpleMailMessage 新建邮件
         * @setTo 要发送的邮箱
         * @setFrom JWC邮箱
         * 分别是邮件标题和正文
         */
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setTo(userEmail);
        simpleMailMessage.setFrom(PROPERTIES.getProperty("JWCEmail"));
        simpleMailMessage.setSubject("");
        simpleMailMessage.setText("");
        simpleMailMessage.setSentDate(new Date());
        /**
         * 发送邮件
         */
        javaMailSender.setJavaMailProperties(PROPERTIES);
        javaMailSender.send(simpleMailMessage);
    }


    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getIdentifyCode() {
        return identifyCode;
    }

    public void setIdentifyCode(String identifyCode) {
        this.identifyCode = identifyCode;
    }
}