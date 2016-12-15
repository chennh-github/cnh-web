package com.cnh.frame.crud.base.controller;

import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * ${Description}
 *
 * @author Administrator
 * @version v1.0.0
 * @since 2016/12/14
 */
public interface ViewRouter {


    /**
     * 路由的匹配规则
     * @param request
     * @param urls
     * @return
     */
    public boolean mapping(HttpServletRequest request, String[] urls);


    /**
     * 路由的处理函数
     * @param request
     * @param urls
     * @return
     * @throws Exception
     */
    public ModelAndView resolve(HttpServletRequest request, String[] urls) throws Exception;

}
