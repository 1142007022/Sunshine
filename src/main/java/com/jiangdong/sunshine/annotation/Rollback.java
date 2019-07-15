package com.jiangdong.sunshine.annotation;

import java.lang.annotation.*;

/**
 * 2019.7.15添加rollBackFor属性 可自定义异常的class进行事务回滚
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Inherited
@Documented
public @interface Rollback {

    Class rollBackFor() default Exception.class;

}
