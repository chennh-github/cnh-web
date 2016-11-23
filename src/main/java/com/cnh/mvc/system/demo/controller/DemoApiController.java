package com.cnh.mvc.system.demo.controller;


import com.cnh.frame.crud.base.constant.CONSTANT;
import com.cnh.frame.crud.base.service.BaseService;
import com.cnh.mvc.api.DemoApi;
import com.cnh.mvc.system.base.controller.SystemApiController;
import com.cnh.mvc.system.demo.entity.Demo;
import com.cnh.mvc.system.demo.service.DemoService;
import com.github.pagehelper.Page;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Api("Demo操作类")
@RestController
@RequestMapping(CONSTANT.API_SYSTEM_PATH + "/demo")
public class DemoApiController extends SystemApiController<Demo> {

    @Autowired
    private DemoService demoService;

    @Autowired
    private DemoApi demoApi;

    @Override
    protected BaseService<Demo> getService() {
        return demoService;
    }


    @RequestMapping(value = "query", method = RequestMethod.GET)
    public Page<Demo> query() {
        return demoApi.query();
    }


}
