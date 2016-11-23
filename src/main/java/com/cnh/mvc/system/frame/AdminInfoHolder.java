package com.cnh.mvc.system.frame;

import com.cnh.frame.wraps.CookieWrap;
import com.cnh.frame.wraps.RequestWrap;
import com.cnh.mvc.system.adminInfo.entity.AdminInfo;

import javax.servlet.http.Cookie;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * ${DESCRIPTION}
 * <p/>
 * Author: chennaihua
 * History:
 * 1.created at 16/9/15.
 */
public class AdminInfoHolder {

    private final static Map<String, AdminInfo> adminInfoMap = new HashMap<String, AdminInfo>();

    public final static String KEY = "__KEY_SYSTEM__";

    public static void add(final AdminInfo adminInfo) {
        String key = KEY + UUID.randomUUID().toString();
        adminInfoMap.put(key, adminInfo);

        Cookie cookie = new Cookie(KEY, key);
        cookie.setPath("/");
        RequestWrap.getResponse().addCookie(cookie);
    }

    public static AdminInfo get() {
        Cookie cookie = CookieWrap.getCookieByName(RequestWrap.getRequest(), KEY);
        return cookie == null ? null : adminInfoMap.get(cookie.getValue());
    }


    public static void remove() {
        Cookie cookie = CookieWrap.getCookieByName(RequestWrap.getRequest(), KEY);
        if (cookie != null) {
            adminInfoMap.remove(cookie.getValue());
            CookieWrap.removeCookies(RequestWrap.getRequest(), RequestWrap.getResponse(), KEY);
        }

    }
}
