package com.cnh.frame.crud.exception.type;

import com.cnh.frame.crud.exception.BaseException;

/**
 * ${Description}
 *
 * @author Administrator
 * @version v1.0.0
 * @since 2016/8/5
 */
public class InvalidRouteException extends BaseException {

    private String code = "Invalid route";

    public InvalidRouteException(String message, String stack) {
        super(message, stack);
    }

    public InvalidRouteException(String message, Throwable throwable) {
        super(message, throwable);
    }

    public InvalidRouteException(String message) {
        super(message);
    }

}
