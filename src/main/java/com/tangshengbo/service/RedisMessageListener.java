package com.tangshengbo.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;

/**
 * Created by Tangshengbo on 2018/4/24.
 */
public class RedisMessageListener implements MessageListener {

    private static final Logger logger = LoggerFactory.getLogger(RedisMessageListener.class);

    @Override
    public void onMessage(Message message, byte[] pattern) {
        logger.info("接收到消息{}", message);
    }
}
