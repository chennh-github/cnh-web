package com.cnh.frame.wraps;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.Date;

/**
 * ${Description}
 *
 * @author Administrator
 * @version v1.0.0
 * @since 2016/12/16
 */
public class ObjectWrap {

    public static boolean equals(Object value1, Object value2) {
        if (value1 instanceof String && value2 instanceof String) {
            return StringUtils.equals((String) value1, (String) value2);
        } else if (value1 instanceof Integer && value2 instanceof Integer) {
            return ((Integer) value1).intValue() == ((Integer) value2).intValue();
        } else if (value1 instanceof Long && value2 instanceof Long) {
            return ((Long) value1).longValue() == ((Long) value2).longValue();
        } else if (value1 instanceof Double && value2 instanceof Double) {
            return ((Double) value1).doubleValue() == ((Double) value2).doubleValue();
        } else if (value1 instanceof Date && value2 instanceof Date) {
            return ((Date) value1).getTime() == ((Date) value2).getTime();
        } else if (value1 instanceof Boolean && value2 instanceof Boolean) {
            return ((Boolean) value1).booleanValue() == ((Boolean) value2).booleanValue();
        }
        return ObjectUtils.equals(value1, value2);
    }
}
