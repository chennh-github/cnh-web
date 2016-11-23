package com.cnh.frame.crud.exception.type;

import com.cnh.frame.crud.exception.BaseException;

/**
 * ${Description}
 *
 * @author Administrator
 * @version v1.0.0
 * @since 2016/8/5
 */
public class ResultNotFoundException extends BaseException {

    private String code = "Not found";

    public ResultNotFoundException(String message, String stack) {
        super(message, stack);
    }

    public ResultNotFoundException(String message, Throwable throwable) {
        super(message, throwable);
    }

    public ResultNotFoundException(String message) {
        super(message);
    }

}
