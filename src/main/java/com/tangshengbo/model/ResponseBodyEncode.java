package com.tangshengbo.model;

import java.lang.annotation.*;

/**
 * Created by TangShengBo on 2018/12/8
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ResponseBodyEncode {
}
