package com.cnh.frame.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;

/**
 * ${Description}
 *
 * @author Administrator
 * @version v1.0.0
 * @since 2016/8/23
 */
public interface MappingInterceptor extends HandlerInterceptor {


    /**
     * 添加拦截路径
     * @return
     */
    public String[] addPathPatterns();

    /**
     * 排除路径
     * @return
     */
    public String[] excludePathPatterns();

}
