package com.cnh.mvc.system.adminInfo.controller;

import com.cnh.frame.crud.base.constant.CONSTANT;
import com.cnh.frame.crud.base.service.BaseService;
import com.cnh.mvc.system.base.controller.SystemViewController;
import com.cnh.mvc.system.adminInfo.entity.AdminInfo;
import com.cnh.mvc.system.adminInfo.service.AdminInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping(CONSTANT.ROUTE_SYSTEM_PATH + "/adminInfo")
public class AdminInfoViewController extends SystemViewController<AdminInfo>{

    @Autowired
    private AdminInfoService adminInfoService;

    @Override
    protected BaseService<AdminInfo>getService(){
        return adminInfoService;
    }

    @Override
    protected String getModularName(){
        return "adminInfo";
    }

}
