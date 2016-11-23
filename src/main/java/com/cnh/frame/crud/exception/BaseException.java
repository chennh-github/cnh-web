package com.cnh.frame.crud.exception;

import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * ${Description}
 *
 * @author Administrator
 * @version v1.0.0
 * @since 2016/8/5
 */
@Data
public class BaseException extends Exception implements IBaseException {

    private String message;

    private String code = "BASE_EXCEPTION";

    private String stack;

    public BaseException(){}

    public BaseException(String message, String stack) {
        super();
        this.message = message;
        this.stack = stack;
    }

    public BaseException(String message, Throwable throwable) {
        this(message, throwable.getStackTrace().toString());
    }

    public BaseException(String message) {
        this.message = message;
    }


    public String getMessage() {
        return message;
    };

    public String getCode() {
        return code;
    };

    public String getStack() {
        return stack;
    };


    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.DEFAULT_STYLE);
    }

}
