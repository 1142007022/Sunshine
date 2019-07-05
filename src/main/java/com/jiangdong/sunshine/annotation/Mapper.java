package com.jiangdong.sunshine.annotation;

import java.lang.annotation.*;

/**
 * 自定义mapper接口标识注解
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Mapper {

    String name();

}
