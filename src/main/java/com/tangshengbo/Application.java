package com.tangshengbo;

import com.tangshengbo.service.LogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by TangShengBo on 2018/10/11
 */
public class Application {

    private static Logger logger = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {
        BeanFactory beanFactory = new ClassPathXmlApplicationContext("spring-context.xml");
//                new XmlBeanFactory(new ClassPathResource("spring-context.xml"));
        LogService logService = (LogService) beanFactory.getBean("logService");
        logger.info("{}", logService.listHttpLog());
    }
}
