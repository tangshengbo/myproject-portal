package com.tangshengbo.model;

import org.springframework.beans.factory.xml.NamespaceHandlerSupport;

/**
 * Created by Tangshengbo on 2018/10/19
 */
public class MyNamespaceHandler extends NamespaceHandlerSupport {

    @Override
    public void init() {
        System.out.println("2342342342342");
    }
}
