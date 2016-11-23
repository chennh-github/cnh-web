package com.cnh.mvc.global.interceptor.system;

import com.cnh.frame.crud.base.constant.CONSTANT;
import com.cnh.frame.freemarker.variables.EnvironmentContext;
import com.cnh.frame.holders.ApplicationYmlHolder;
import com.cnh.frame.interceptor.MappingInterceptor;
import com.cnh.frame.wraps.RequestWrap;
import com.cnh.mvc.system.frame.AdminInfoHolder;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * ${Description}
 *
 * @author Administrator
 * @version v1.0.0
 * @since 2016/8/29
 */
public class SystemRequestParamsInjectInterceptor implements MappingInterceptor {
    @Override
    public String[] addPathPatterns() {
        return new String[]{CONSTANT.ROUTE_SYSTEM_PATH + "/**"};
    }

    @Override
    public String[] excludePathPatterns() {
        return new String[0];
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        request.setAttribute("imgShowRoot", ApplicationYmlHolder.PROPERTIES.get("custom.imgShowRoot"));
        request.setAttribute("staticVersion", EnvironmentContext.VERSION);
        request.setAttribute("contextPath", RequestWrap.getContextPath());
        request.setAttribute("adminInfo", AdminInfoHolder.get());
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
