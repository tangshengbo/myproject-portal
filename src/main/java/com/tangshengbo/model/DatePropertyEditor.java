package com.tangshengbo.model;

import cn.hutool.core.date.DateUtil;

import java.beans.PropertyEditorSupport;

/**
 * Created by TangShengBo on 2018/11/6
 */
public class DatePropertyEditor extends PropertyEditorSupport {

    private String format = "yyyy-MM-dd";

    public void setFormat(String format) {
        this.format = format;
    }

    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        this.setValue(DateUtil.parse(text, format));
    }
}
