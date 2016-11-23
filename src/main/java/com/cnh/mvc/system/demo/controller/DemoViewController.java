package com.cnh.mvc.system.demo.controller;

import com.cnh.frame.crud.base.constant.CONSTANT;
import com.cnh.frame.crud.base.service.BaseService;
import com.cnh.mvc.system.base.controller.SystemViewController;
import com.cnh.mvc.system.demo.entity.Demo;
import com.cnh.mvc.system.demo.service.DemoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * ${Description}
 *
 * @author Administrator
 * @version v1.0.0
 * @since 2016/8/9
 */
@Controller
@RequestMapping(CONSTANT.ROUTE_SYSTEM_PATH + "/demo")
public class DemoViewController extends SystemViewController<Demo> {

    @Autowired
    private DemoService demoService;

    @Override
    protected BaseService<Demo> getService() {
        return demoService;
    }

    @Override
    protected String getModularName() {
        return "demo";
    }


    @RequestMapping("i18n")
    public ModelAndView test() {
        return new ModelAndView("i18n");
    }


    /**
     * 新增页
     * @return
     * @throws Exception
     */
    @RequestMapping("form2")
    public ModelAndView form2 () throws Exception {
        return new ModelAndView(CONSTANT.VIEW_SYSTEM_PATH + "/"  + getModularName() + "/form2");
    }

    /**
     *编辑页
     * @param id
     * @return
     * @throws Exception
     */
    @RequestMapping("form2/{id}")
    public ModelAndView form2 (@PathVariable("id") Long id) throws Exception {
        ModelAndView modelAndView = new ModelAndView(CONSTANT.VIEW_SYSTEM_PATH + "/"  + getModularName() + "/form2");
        if (id != null && id != 0) {
            modelAndView.getModelMap().put("entity", demoService.get(id));
        }
        modelAndView.getModelMap().put("id", id);
        return modelAndView;
    }

}
