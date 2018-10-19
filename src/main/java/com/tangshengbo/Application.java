package com.tangshengbo;

import com.tangshengbo.model.HttpLog;
import com.tangshengbo.model.MyClassPathXmlApplicationContext;
import com.tangshengbo.service.LogService;
import net.sf.cglib.beans.BeanMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.util.StopWatch;

import java.util.List;

/**
 * Created by TangShengBo on 2018/10/11
 */
public class Application {

    private static Logger logger = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {
        BeanFactory beanFactory =
//                new ClassPathXmlApplicationContext("");
//                new XmlBeanFactory(new ClassPathResource("spring-context.xml"));
                new MyClassPathXmlApplicationContext("spring-context.xml");
        LogService logService = (LogService) beanFactory.getBean("logService");
//        logger.info("{}", logService.listHttpLog());
//        PersonTest personTest = (PersonTest) beanFactory.getBean("personTest");
//        personTest.show();
        List<HttpLog> httpLogList = logService.listHttpLog();
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        for (int i = 0; i < 1000; i++) {
            httpLogList.forEach(httpLog -> {
                BeanMap beanMap =  BeanMap.create(httpLog);
                logger.info("BeanMap:{}", beanMap);
            });
        }
        stopWatch.stop();
        logger.info("耗时：{}s", stopWatch.getTotalTimeSeconds());


    }
}
