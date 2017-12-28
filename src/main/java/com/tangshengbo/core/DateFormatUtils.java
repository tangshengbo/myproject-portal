package com.tangshengbo.core;

import org.apache.commons.lang3.time.FastDateFormat;

/**
 * Created by TangShengBo on 2017/12/28.
 */
public class DateFormatUtils {

    public static final String DEFAULT_DATETIME_PATTERN = "yyyy-MM-dd HH:mm:ss";

    /**
     * 默认时间格式
     */
    public static final FastDateFormat DEFAULT_DATETIME_FORMAT = FastDateFormat.getInstance(DEFAULT_DATETIME_PATTERN);
}
