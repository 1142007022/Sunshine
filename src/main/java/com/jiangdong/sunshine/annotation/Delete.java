package com.jiangdong.sunshine.annotation;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Inherited
@Documented
public @interface Delete {

    String id();

    String sql();

}
