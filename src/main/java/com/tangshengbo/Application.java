package com.tangshengbo;

import com.tangshengbo.model.MyClassPathXmlApplicationContext;
import com.tangshengbo.model.User;
import com.tangshengbo.model.cycle.TestA;
import com.tangshengbo.service.LogService;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Created by TangShengBo on 2018/10/11
 */
public class Application {

    private static Logger logger = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {
        MyClassPathXmlApplicationContext beanFactory =
//                new ClassPathXmlApplicationContext("");
//                new XmlBeanFactory(new ClassPathResource("spring-context.xml"));
                new MyClassPathXmlApplicationContext("spring-context.xml");
        LogService logService = (LogService) beanFactory.getBean("logService");
        logService.listHttpLog();
        FactoryBean userFactoryBean = (FactoryBean) beanFactory.getBean("&userFactoryBean");
//        FactoryBean userFactoryBean = (FactoryBean) beanFactory.getBean("&accountMapper");
        TestA testA = (TestA) beanFactory.getBean("testA");
        logger.info("{}", testA);
        User user = (User) beanFactory.getBean("com.tangshengbo.model.User#0");
        logger.info("Simple Bean{}", user);
        try {
            logger.info("Factory Bean{}", userFactoryBean.getObject());
            Properties allProperties = PropertiesLoaderUtils.loadAllProperties("META-INF/spring.handlers");
            Map<String, String> mapping = new HashMap<>(allProperties.size());
            CollectionUtils.mergePropertiesIntoMap(allProperties, mapping);
            logger.info("{}", mapping.get("http://www.tangshengbo.com/schema/user"));
            SecurityManager s = System.getSecurityManager();
            if (s == null) {
                logger.info("{}", "SecurityManager not been established..");
            }
            Resource resource = beanFactory.getResource("https://camo.githubusercontent.com/41e7f575a8b976921c9af281e51274d195ec8571/68747470733a2f2f692e696d6775722e636f6d2f4d7034304359662e6a7067");
            String string = IOUtils.toString(resource.getInputStream(), "UTF-8");
            logger.info("{}", string);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
