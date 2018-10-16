package com.tangshengbo.model;

import com.tangshengbo.listener.MyReaderEventListener;
import org.springframework.beans.BeansException;
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
}
