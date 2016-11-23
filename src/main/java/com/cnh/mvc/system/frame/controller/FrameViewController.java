package com.cnh.mvc.system.frame.controller;

import com.cnh.frame.crud.base.constant.CONSTANT;
import com.cnh.frame.wraps.RSAWrap;
import com.cnh.mvc.system.adminInfo.entity.AdminInfo;
import com.cnh.mvc.system.frame.AdminInfoHolder;
import com.cnh.mvc.system.frame.RSAHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

/**
 * ${Description}
 *
 * @author Administrator
 * @version v1.0.0
 * @since 2016/8/25
 */
@Controller
@RequestMapping(value = CONSTANT.ROUTE_SYSTEM_PATH + "/")
public class FrameViewController {


    @RequestMapping(value = "login")
    public ModelAndView login () throws Exception {
        RSAWrap.RSA rsa = RSAHolder.general();
        ModelAndView view = new ModelAndView("system/login/login");

        Map<String, Object> modelMap = view.getModelMap();
        // 判断用户是否是已登录状态
        AdminInfo adminInfo = AdminInfoHolder.get();
        if (adminInfo != null) {
            modelMap.put("adminInfo", adminInfo);
        }

        modelMap.put("exponent", rsa.getRsaPublicKey().getPublicExponent().toString(16));
        modelMap.put("modulus", rsa.getRsaPublicKey().getModulus().toString(16));
        return view;
    }

}
