package com.cnh.frame.freemarker.variables;

/**
 * ${Description}
 *
 * @author Administrator
 * @version v1.0.0
 * @since 2016/8/11
 */
public class EnvironmentContext {

    private static String ENV = System.getProperty("spring.profiles.active");

    private static String DEV = "development";

    public static String VERSION = String.valueOf(System.currentTimeMillis());

    public static boolean isDev(){
        return DEV.equals(ENV);
    }

}
