package com.cnh.frame.crud.utils;

import com.cnh.frame.crud.exception.BaseException;
import com.cnh.frame.crud.exception.type.InvalidRouteException;
import com.cnh.frame.crud.exception.type.ResultNotFoundException;

/**
 * ${DESCRIPTION}
 * <p/>
 * Author: chennaihua
 * History:
 * 1.created at 16/9/15.
 */
public class Assist {

    public static void threw(String message) throws BaseException {
        throw new BaseException(message);
    }

    public static void threw(boolean shouldThrew, String message) throws BaseException {
        if (shouldThrew) {
            throw new BaseException(message);
        }
    }

    public static void notNull(Object o, String message) throws BaseException {
        if (o == null) {
            throw new BaseException(message);
        }
    }

    public static void invalidRoute(String message) throws BaseException {
        throw new InvalidRouteException(message);
    }

    public static void resultNotFound(String message) throws BaseException {
        throw new ResultNotFoundException(message);
    }
}
