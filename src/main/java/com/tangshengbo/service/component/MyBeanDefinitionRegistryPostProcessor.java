package com.tangshengbo.service.component;

import cn.hutool.core.date.DatePattern;
import com.google.common.collect.Maps;
import com.tangshengbo.model.LoveImage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Date;
import java.util.Map;

/**
 * Created by Tangshengbo on 2018/11/2
 */
@Component
public class MyBeanDefinitionRegistryPostProcessor implements BeanDefinitionRegistryPostProcessor, Ordered {

    @Override
    public int getOrder() {
        return -1;
    }

    private static Logger logger = LoggerFactory.getLogger(MyBeanDefinitionRegistryPostProcessor.class);

    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
        RootBeanDefinition def = new RootBeanDefinition(LoveImage.class);
//        def.setRole(BeanDefinition.ROLE_SUPPORT);
        registry.registerBeanDefinition("loveImage", def);
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        String[] beanDefinitionNames = beanFactory.getBeanDefinitionNames();
        logger.info("{}", Arrays.toString(beanDefinitionNames));
        for (String beanName : beanDefinitionNames) {
            if ("loveImage".equals(beanName)) {
                logger.info("设置loveImage开始..............");
                BeanDefinition beanDefinition = beanFactory.getBeanDefinition(beanName);
                Map<String, Object> propertyValue = Maps.newHashMap();
                Date current = new Date();
                propertyValue.put("createDate", current);
                propertyValue.put("remark", DatePattern.NORM_DATETIME_FORMAT.format(current));
                propertyValue.put("imgUrl", "https://aecpm.alicdn.com/simba/img/TB1W4nPJFXXXXbSXpXXSutbFXXX.jpg");
                propertyValue.put("beanFactoryProcessorDate", DatePattern.NORM_DATETIME_FORMAT.format(current));
                beanDefinition.getPropertyValues().addPropertyValues(propertyValue);
                logger.info("设置loveImage结束..............");
            }
        }
    }
}
