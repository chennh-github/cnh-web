package com.cnh.mvc.system.menuRelate.controller;

import com.cnh.frame.crud.base.constant.CONSTANT;
import com.cnh.frame.crud.base.service.BaseService;
import com.cnh.mvc.system.base.controller.SystemViewController;
import com.cnh.mvc.system.menuRelate.entity.MenuRelate;
import com.cnh.mvc.system.menuRelate.service.MenuRelateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping(CONSTANT.ROUTE_SYSTEM_PATH + "/menuRelate")
public class MenuRelateViewController extends SystemViewController<MenuRelate>{

    @Autowired
    private MenuRelateService menuRelateService;

    @Override
    protected BaseService<MenuRelate>getService(){
        return menuRelateService;
    }

    @Override
    protected String getModularName(){
        return "menuRelate";
    }

}
