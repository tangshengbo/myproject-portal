package com.tangshengbo.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.parsing.*;

/**
 * Created by Tangshengbo on 2018/10/16
 */
public class MyReaderEventListener implements ReaderEventListener {

    private static Logger logger = LoggerFactory.getLogger(MyReaderEventListener.class);

    @Override
    public void defaultsRegistered(DefaultsDefinition defaultsDefinition) {

    }

    @Override
    public void componentRegistered(ComponentDefinition componentDefinition) {
        if (componentDefinition instanceof BeanComponentDefinition) {
            BeanComponentDefinition beanComponentDefinition = (BeanComponentDefinition) componentDefinition;
            BeanDefinition beanDefinition = beanComponentDefinition.getBeanDefinitions()[0];
            MutablePropertyValues propertyValues = beanDefinition.getPropertyValues();
            logger.info("BeanClassName:{},propertyValues:{}", beanDefinition.getBeanClassName(),
                    propertyValues);
        } else {
            logger.info("BeanClassName:{}", componentDefinition.getName());
        }
    }

    @Override
    public void aliasRegistered(AliasDefinition aliasDefinition) {

    }

    @Override
    public void importProcessed(ImportDefinition importDefinition) {

    }
}
