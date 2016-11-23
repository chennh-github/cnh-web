package com.cnh.mvc.system.frame;

import com.cnh.frame.wraps.RSAWrap;
import com.cnh.frame.wraps.RequestWrap;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * ${Description}
 *
 * @author Administrator
 * @version v1.0.0
 * @since 2016/9/9
 */
public class RSAHolder {

    private static Map<String, RSAWrap.RSA> RSAMap = new HashMap<String, RSAWrap.RSA>();

    public static String RSA_KEY = "__KEY__SECURITY__";

    public static RSAWrap.RSA get (String key) {
        return RSAMap.get(key);
    }

    /**
     * 从cookie中取得key，并根据key取得RSA
     * @return
     */
    public static RSAWrap.RSA get () {
        String key = null;
        HttpServletRequest request = RequestWrap.getRequest();
        for (Cookie cookie: request.getCookies()) {
            if (RSA_KEY.equals(cookie.getName())) {
                key = cookie.getValue();
                break;
            }
        }
        return get(key);
    }

    public static void set (String key, RSAWrap.RSA rsa) {
        RSAMap.put(key, rsa);
    }

    public static void remove (String key) {
        RSAMap.remove(key);
    }

    public static void remove () {
        String key = null;
        HttpServletRequest request = RequestWrap.getRequest();
        for (Cookie cookie: request.getCookies()) {
            if (RSA_KEY.equals(cookie.getName())) {
                key = cookie.getValue();
                break;
            }
        }
        remove(key);
    }

    public static void clear () {
        RSAMap.clear();
    }

    /**
     * 生成一个新的RSA，并放置到cookie中
     * @return
     * @throws Exception
     */
    public static RSAWrap.RSA general () throws Exception {
        RSAWrap.RSA rsa = RSAWrap.getRSA();
        String key = RSA_KEY + System.currentTimeMillis();

        Cookie cookie = new Cookie(RSA_KEY, key);
        cookie.setPath("/");

        HttpServletResponse response = RequestWrap.getResponse();
        response.addCookie(cookie);
        RSAMap.put(key, rsa);

        return rsa;
    }
}
