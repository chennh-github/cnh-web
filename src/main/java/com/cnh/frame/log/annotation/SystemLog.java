package com.cnh.frame.log.annotation;

import java.lang.annotation.*;

/**
 * 系统日志输出注解
 *
 * @author Administrator
 * @version v1.0.0
 * @since 2016/8/9
 */
@Target({ElementType.PARAMETER, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SystemLog {

    /**
     * 标题
     * @return
     */
    String title() default "";

    /**
     * 类型
     * @return
     */
    String type() default "system";

    /**
     * 描述
     * @return
     */
    String description() default "";
}
