package com.tangshengbo.model;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.core.io.Resource;

/**
 * Created by Tangshengbo on 2018/10/12
 */
public class MyXmlBeanFactory extends DefaultListableBeanFactory {

    private final XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(this);


    /**
     * Create a new XmlBeanFactory with the given resource,
     * which must be parsable using DOM.
     * @param resource XML resource to load bean definitions from
     * @throws BeansException in case of loading or parsing errors
     */
    public MyXmlBeanFactory(Resource resource) throws BeansException {
        this(resource, null);
    }

    /**
     * Create a new XmlBeanFactory with the given input stream,
     * which must be parsable using DOM.
     * @param resource XML resource to load bean definitions from
     * @param parentBeanFactory parent bean factory
     * @throws BeansException in case of loading or parsing errors
     */
    public MyXmlBeanFactory(Resource resource, BeanFactory parentBeanFactory) throws BeansException {
        super(parentBeanFactory);
        reader.setDocumentReaderClass(MyBeanDefinitionDocumentReader.class);
        this.reader.loadBeanDefinitions(resource);
    }

}
