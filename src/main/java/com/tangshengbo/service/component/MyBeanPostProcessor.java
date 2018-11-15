package com.tangshengbo.service.component;

import cn.hutool.core.date.DateUtil;
import com.tangshengbo.core.DateFormatUtils;
import com.tangshengbo.model.LoveImage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * Created by TangShengBo on 2018/11/2
 */
@Component
public class MyBeanPostProcessor implements BeanPostProcessor, BeanNameAware {

    private static Logger logger = LoggerFactory.getLogger(MyBeanPostProcessor.class);

    @Override
    public void setBeanName(String name) {
        logger.info("{}", name);
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if (bean instanceof LoveImage) {
            LoveImage loveImage = (LoveImage) bean;
            loveImage.setId(System.currentTimeMillis());
            loveImage.setBeanProcessorDate(DateUtil.format(new Date(), DateFormatUtils.DEFAULT_DATETIME_PATTERN));
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }
}
