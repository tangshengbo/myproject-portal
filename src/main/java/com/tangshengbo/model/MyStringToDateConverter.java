package com.tangshengbo.model;


import cn.hutool.core.date.DatePattern;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.converter.Converter;

import java.text.ParseException;
import java.util.Date;

/**
 * Created by TangShengBo on 2018/11/13
 */
public class MyStringToDateConverter implements Converter<String, Date> {

    private static Logger logger = LoggerFactory.getLogger(MyStringToDateConverter.class);

    @Override
    public Date convert(String source) {
        try {
            logger.info("source:{}", source);
            return DatePattern.NORM_DATE_FORMAT.parse(source);
        } catch (ParseException e) {
            return null;
        }
    }
}
