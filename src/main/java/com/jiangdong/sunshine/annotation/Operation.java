package com.jiangdong.sunshine.annotation;

import com.jiangdong.sunshine.enums.OperationTypes;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Inherited
@Documented
public @interface Operation {

    OperationTypes value();

}
