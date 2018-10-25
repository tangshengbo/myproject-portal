package com.tangshengbo;

import com.tangshengbo.dao.AccountMapper;
import com.tangshengbo.model.MyClassPathXmlApplicationContext;
import com.tangshengbo.model.User;
import com.tangshengbo.service.LogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.FactoryBean;
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
//        User userFactoryBean = (User) beanFactory.getBean("userFactoryBean");
        FactoryBean userFactoryBean = (FactoryBean) beanFactory.getBean("&accountMapper");
        User user = (User) beanFactory.getBean("com.tangshengbo.model.User#0");
        logger.info("Simple Bean{}", user);
        try {
            logger.info("Factory Bean{}", ((AccountMapper) userFactoryBean.getObject()).selectAll());
            Properties allProperties = PropertiesLoaderUtils.loadAllProperties("META-INF/spring.handlers");
            Map<String, String> mapping = new HashMap<>(allProperties.size());
            CollectionUtils.mergePropertiesIntoMap(allProperties, mapping);
            logger.info("{}", mapping.get("http://www.tangshengbo.com/schema/user"));
        } catch (Exception e) {
            e.printStackTrace();
        }

//        logger.info("{}", logService.listHttpLog());
//        PersonTest personTest = (PersonTest) beanFactory.getBean("personTest");
//        personTest.show();

    }
}
