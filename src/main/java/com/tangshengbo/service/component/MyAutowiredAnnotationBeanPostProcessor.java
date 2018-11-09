package com.tangshengbo.service.component;

import com.tangshengbo.model.MyInject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.ClassUtils;

import java.lang.annotation.Annotation;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Created by Tangshengbo on 2018/10/31
 */
@Component
public class MyAutowiredAnnotationBeanPostProcessor extends AutowiredAnnotationBeanPostProcessor implements InitializingBean {

    private static Logger logger = LoggerFactory.getLogger(MyAutowiredAnnotationBeanPostProcessor.class);

    public MyAutowiredAnnotationBeanPostProcessor() {
        super.setAutowiredAnnotationTypes(custom());
//        super.setOrder(1000);
    }

    @SuppressWarnings("unchecked")
    private Set<Class<? extends Annotation>> custom() {
        Set<Class<? extends Annotation>> autowiredAnnotationTypes =
                new LinkedHashSet<>();
        autowiredAnnotationTypes.add(Autowired.class);
        autowiredAnnotationTypes.add(MyInject.class);
        autowiredAnnotationTypes.add(Value.class);
        try {
            autowiredAnnotationTypes.add((Class<? extends Annotation>)
                    ClassUtils.forName("javax.inject.Inject", AutowiredAnnotationBeanPostProcessor.class.getClassLoader()));
            logger.info("JSR-330 'javax.inject.Inject' annotation found and supported for autowiring");
        } catch (ClassNotFoundException ex) {
            // JSR-330 API not available - simply skip.
        }
        return autowiredAnnotationTypes;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        logger.info("MyAutowiredAnnotationBeanPostProcessor......afterPropertiesSet");
    }
}
