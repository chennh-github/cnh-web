package com.cnh.frame.freemarker.support;

import org.springframework.util.StringUtils;

import java.beans.PropertyEditorSupport;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * ${Description}
 *
 * @author Administrator
 * @version v1.0.0
 * @since 2016/8/18
 */
public class DateTypeEditor extends PropertyEditorSupport {

    public static final DateFormat DF_LONG = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public static final DateFormat DF_SHORT = new SimpleDateFormat("yyyy-MM-dd");
    /**
     * 短类型日期长度
     */
    public static final int SHORT_DATE = 10;

    public void setAsText(String text) throws IllegalArgumentException {
        text = text.trim();
        if (!StringUtils.hasText(text)) {
            setValue(null);
            return;
        }
        try {
            if (text.length() <= SHORT_DATE) {
                setValue(new java.sql.Date(DF_SHORT.parse(text).getTime()));
            } else {
                setValue(new java.sql.Timestamp(DF_LONG.parse(text).getTime()));
            }
        } catch (ParseException ex) {
            IllegalArgumentException iae = new IllegalArgumentException(
                    "Could not parse date: " + ex.getMessage());
            iae.initCause(ex);
            throw iae;
        }
    }

    /**
     * Format the Date as String, using the specified DateFormat.
     */
    public String getAsText() {
        Date value = (Date) getValue();
        return (value != null ? DF_LONG.format(value) : "");
    }
}
