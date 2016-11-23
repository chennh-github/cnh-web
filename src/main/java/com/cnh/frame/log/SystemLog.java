package com.cnh.frame.log;

import lombok.Data;

import java.lang.reflect.Method;

/**
 * ${Description}
 *
 * @author Administrator
 * @version v1.0.0
 * @since 2016/8/9
 */
@Data
public class SystemLog {

    private String className;

    private String methodName;

    private Object[] args;

    private Class targetClass;

    private Method[] targetMethods;

    private String title;

    private String description;

    private com.cnh.frame.log.annotation.SystemLog systemLogAnnotation;

}
