package com.tangshengbo.model;

/**
 * Created by Tangshengbo on 2018/10/15
 */
public abstract class PersonTest {

    public void show() {
        getPerson().showName();
    }

    abstract Person getPerson();
}
