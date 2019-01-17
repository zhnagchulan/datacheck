package com.chinalife.datacheck.web.annotation;

import java.lang.annotation.*;

/**
 * 对Spring MVC 常规请求调用的返回值注解.
 *
 * @author maeagle
 * @date 2016-2-5 16 :23:35
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ResponseMessage {
    /**
     * {@link java.util.Date}类型字段的转换格式
     *
     * @return 转换日期格式
     * @author maeagle
     * @date 2016-4-29 19 :20:56
     */
    String dateFormat() default "";
}
