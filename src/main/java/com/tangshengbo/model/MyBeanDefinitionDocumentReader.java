package com.tangshengbo.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.xml.DefaultBeanDefinitionDocumentReader;
import org.w3c.dom.Element;

/**
 * Created by Tangshengbo on 2018/10/12
 */
public class MyBeanDefinitionDocumentReader extends DefaultBeanDefinitionDocumentReader {

    private static Logger logger = LoggerFactory.getLogger(MyBeanDefinitionDocumentReader.class);

    @Override
    protected void preProcessXml(Element root) {
        logger.info("解析xml前置{}", root.getTagName());
    }

    @Override
    protected void postProcessXml(Element root) {
        logger.info("解析xml后置{}", root.getTagName());
    }
}
