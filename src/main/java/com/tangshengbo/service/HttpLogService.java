package com.tangshengbo.service;

import com.mongodb.WriteResult;
import com.tangshengbo.model.HttpLog;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Tangshengbo on 2018/4/20.
 */
@Service
public class HttpLogService {

    private static Logger logger = LoggerFactory.getLogger(HttpLogService.class);

    @Autowired
    private MongoTemplate mongoTemplate;

    /**
     * 创建对象
     *
     * @param httpLog
     */
    public void saveUser(HttpLog httpLog) {
        mongoTemplate.save(httpLog);
    }


    /**
     * 根据用户名查询对象
     *
     * @param clientIp
     * @return
     */
    public List<HttpLog> findHttpLogs(String clientIp) {
        Query query = new Query(Criteria.where("clientIp").is(clientIp));
        return mongoTemplate.find(query, HttpLog.class);
    }

    /**
     * 更新对象
     *
     * @param httpLog
     */
    public void updateHttpLog(HttpLog httpLog) {
        WriteResult result = mongoTemplate.upsert(new Query(Criteria.where("logId").is(httpLog.getLogId())),
                new Update().set("clientIp", httpLog.getClientIp()), HttpLog.class);
        logger.info("updateHttpLog:{}", result);
    }

    public void remvoeHttpById(int logId) {
        WriteResult result = mongoTemplate.remove(new Query(Criteria.where("logId").is(logId)), HttpLog.class);
        logger.info("remvoeHttpById:{}", result);

    }
}
