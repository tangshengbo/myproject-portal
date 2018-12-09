package com.tangshengbo.core.extension;

import org.springframework.beans.factory.support.MethodReplacer;

import java.lang.reflect.Method;

/**
 * Created by Tangshengbo on 2018/10/15
 */
public class PersonReplacer implements MethodReplacer {

    @Override
    public Object reimplement(Object obj, Method method, Object[] args) throws Throwable {
        System.out.println("方法替换.........");
        return null;
    }
}
