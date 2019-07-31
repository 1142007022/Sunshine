package com.jiangdong.sunshine.annotation;

import java.lang.annotation.*;

/**
 * 对应表注解
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Inherited
@Documented
public @interface Table {

    String value();

}
