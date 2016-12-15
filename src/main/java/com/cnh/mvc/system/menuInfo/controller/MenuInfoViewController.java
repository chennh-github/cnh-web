package com.cnh.mvc.system.menuInfo.controller;

import com.cnh.frame.crud.base.constant.CONSTANT;
import com.cnh.frame.crud.base.service.BaseService;
import com.cnh.mvc.system.base.controller.SystemViewController;
import com.cnh.mvc.system.menuInfo.dao.IMenuInfoDao;
import com.cnh.mvc.system.menuInfo.entity.MenuInfo;
import com.cnh.mvc.system.menuInfo.service.MenuInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping(CONSTANT.ROUTE_SYSTEM_PATH + "/menuInfo")
public class MenuInfoViewController extends SystemViewController<MenuInfo>{

    @Autowired
    private MenuInfoService menuInfoService;

    @Autowired
    private IMenuInfoDao menuInfoDao;

    @Override
    protected BaseService<MenuInfo>getService(){
        return menuInfoService;
    }

    @Override
    protected String getModularName(){
        return "menuInfo";
    }

}
