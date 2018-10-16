package com.tangshengbo;

import com.tangshengbo.model.MyClassPathXmlApplicationContext;
import com.tangshengbo.model.PersonTest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanFactory;

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
//        LogService logService = (LogService) beanFactory.getBean("logService");
//        logger.info("{}", logService.listHttpLog());
        PersonTest personTest = (PersonTest) beanFactory.getBean("personTest");
        personTest.show();
    }
}
