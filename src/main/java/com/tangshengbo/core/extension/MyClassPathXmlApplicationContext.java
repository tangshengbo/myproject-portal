package com.tangshengbo.core.extension;

import com.tangshengbo.listener.MyReaderEventListener;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.QualifierAnnotationAutowireCandidateResolver;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by Tangshengbo on 2018/10/12
 */
public class MyClassPathXmlApplicationContext extends ClassPathXmlApplicationContext {

    public MyClassPathXmlApplicationContext(String configLocation) throws BeansException {
        super(configLocation);
    }

    @Override
    protected void initBeanDefinitionReader(XmlBeanDefinitionReader reader) {
        super.initBeanDefinitionReader(reader);
        reader.setDocumentReaderClass(MyBeanDefinitionDocumentReader.class);
        reader.setEventListener(new MyReaderEventListener());
    }

    @Override
    protected void initPropertySources() {
        getEnvironment().setRequiredProperties("JAVA_HOME");
    }

    @Override
    protected void customizeBeanFactory(DefaultListableBeanFactory beanFactory) {
//        beanFactory.setAllowBeanDefinitionOverriding(false);
//        beanFactory.setAllowCircularReferences(false);
        beanFactory.setAutowireCandidateResolver(new QualifierAnnotationAutowireCandidateResolver());
        super.customizeBeanFactory(beanFactory);
    }
}
