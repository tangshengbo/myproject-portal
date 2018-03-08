package com.tangshengbo.service.impl;

import com.tangshengbo.core.QueryString;
import com.tangshengbo.dao.HttpLogMapper;
import com.tangshengbo.model.HttpLog;
import com.tangshengbo.service.LogService;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.URL;
import java.util.List;

/**
 * Created by Tangshengbo on 2018/3/5.
 */
@Service("logService")
public class LogServiceImpl implements LogService {

    private static Logger logger = LoggerFactory.getLogger(LogServiceImpl.class);

    @Autowired
    private HttpLogMapper logMapper;

    @Override
    public List<HttpLog> listHttpLog() {
        List<HttpLog> logList = logMapper.listHttpLog();
        logList.forEach(System.out::println);
        return logList;
    }

    @Override
    public void saveHttpLog(HttpLog httpLog) {
        QueryString queryString = new QueryString();
        queryString.add("ip", httpLog.getClientIp());
        queryString.add("action", "1");
        String address = "";
        try {
            Document doc = Jsoup.parse(new URL("http://www.ip138.com/ips1388.asp?" + queryString), 5000);
            Elements elements = doc.select("ul.ul1 li");
            address = elements.get(0).text();
            address = address.substring(address.indexOf("：") + 1);
            logger.info("{}", address);
        } catch (Exception e) {
            logger.error("{}", ExceptionUtils.getStackTrace(e));
        }
        httpLog.setClientAddress(address);
        logMapper.insertSelective(httpLog);
    }
}
