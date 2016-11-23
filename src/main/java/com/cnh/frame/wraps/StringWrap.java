package com.cnh.frame.wraps;


import org.apache.commons.lang3.StringUtils;

import java.util.regex.Pattern;

/**
 * ${Description}
 *
 * @author Administrator
 * @version v1.0.0
 * @since 2016/8/11
 */
public class StringWrap {


    /**
     * 确保字符串以endStr结尾
     *
     * @param target
     * @param endStr
     * @return
     */
    public static String endBy(String target, String endStr) {
        if (!target.endsWith(endStr)) {
            target = target + endStr;
        }
        return target;
    }

    /**
     * 计算text的长度（一个中文算两个字符）
     *
     * @param text
     * @return
     */
    public static int getLength(String text) {
        int length = 0;
        for (int i = 0; i < text.length(); i++) {
            if ((text.charAt(i) + "").getBytes().length > 1) {
                length += 2;
            } else {
                length += 1;
            }
        }
        return length / 2;
    }

    public static class Validate {

        // 匹配非负整数（正整数 + 0）
        public static final String REG_DIGITS = "^\\d+$";

        // 匹配整数
        public static final String REG_DECIMAL = "^-?\\d+$";

        // 匹配浮点数
        public static final String REG_FLOAT = "^(-?\\d+)(\\.\\d+)?$";

        // 账号
        public static final String REG_ACCOUNT = "^[a-zA-z]\\w{5,15}$";

        /**
         * 是否非负整数
         *
         * @param value
         * @return
         */
        public static boolean isDigits(String value) {
            Pattern p = Pattern.compile(REG_DIGITS);
            return StringUtils.isNotBlank(value) && p.matcher(value).find();
        }

        /**
         * 是否整数
         * @param value
         * @return
         */
        public static boolean isDecimal(String value) {
            Pattern p = Pattern.compile(REG_DECIMAL);
            return StringUtils.isNotBlank(value) && p.matcher(value).find();
        }

        /**
         * 是否浮点数
         * @param value
         * @return
         */
        public static boolean isFloat(String value) {
            Pattern p = Pattern.compile(REG_FLOAT);
            return StringUtils.isNotBlank(value) && p.matcher(value).find();
        }

        /**
         * 是否账号
         * @param value
         * @return
         */
        public static boolean isAccount(String value) {
            Pattern p = Pattern.compile(REG_ACCOUNT);
            return StringUtils.isNotBlank(value) && p.matcher(value).find();
        }

        /**
         * 是否电话号码
         * @paramue
         * @return
         */
        public static boolean isPhone(String value) {
           return true;
        }

        /**
         * 是否邮箱
         * @param value
         * @return
         */
        public static boolean isEmail(String value) {
            return true;
        }

    }

}
