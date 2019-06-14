package com.jiangdong.sunshine.annotation;

import java.lang.annotation.*;

/**
 * 对于批量插入 参数必须是Object[][] 并使用@Param("batchParam")
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Inherited
@Documented
public @interface InsertBatch {

    String sql();

}
