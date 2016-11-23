package com.cnh.mvc.system.base;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.AbstractJsonpResponseBodyAdvice;

/**
 * ${DESCRIPTION}
 * <p/>
 * Author: chennaihua
 * History:
 * 1.created at 2016/10/29.
 */
@ControllerAdvice(basePackages = "com.cnh.mvc")
public class JsonpAdvice extends AbstractJsonpResponseBodyAdvice {
    public JsonpAdvice() {
        super("callback", "jsonp");
    }
}
