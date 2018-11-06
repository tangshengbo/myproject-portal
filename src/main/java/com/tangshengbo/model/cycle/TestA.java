package com.tangshengbo.model.cycle;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Date;

/**
 * Created by TangShengBo on 2018/10/28
 */
public class TestA {

    private TestB testB;

    private Date date;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public TestA(TestB testB) {
        this.testB = testB;
    }

    public TestA() {
    }

    public TestB getTestB() {
        return testB;
    }

    public void setTestB(TestB testB) {
        this.testB = testB;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
