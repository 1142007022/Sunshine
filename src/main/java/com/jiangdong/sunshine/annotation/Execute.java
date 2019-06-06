package com.jiangdong.sunshine.annotation;

import com.jiangdong.sunshine.enums.OperationTypes;

import java.lang.annotation.*;

/**
 * 暂未使用 以后可能会统一使用次注解 根据OperationTypes进行操作区分
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Inherited
@Documented
public @interface Execute {
    String sql();

    OperationTypes OperationTypes();
}
