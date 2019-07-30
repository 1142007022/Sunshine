package com.jiangdong.sunshine.annotation;

import java.lang.annotation.*;

/**
 * 主键注解
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Inherited
@Documented
public @interface Id {
}
