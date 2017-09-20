package com.tangshengbo.core;

import java.lang.annotation.*;

/**
 * Created by Tangshengbo on 2017/9/19.
 */
@Target({ElementType.PARAMETER, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Log {
    /**
     * 业务名称
     *
     * @return
     */
    String businessName() default "";

    /**
     * 操作类型(1 文件下载  2 对账)
     *
     * @return
     */
    String type() default "";
}
