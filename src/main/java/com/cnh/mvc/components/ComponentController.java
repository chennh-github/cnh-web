package com.cnh.mvc.components;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 * ${Description}
 *
 * @author Administrator
 * @version v1.0.0
 * @since 2016/9/1
 */
@Controller
@RequestMapping(ComponentController.COMPONENT_ROUTE_PATH)
public class ComponentController {

    public static final String COMPONENT_ROUTE_PATH = "/component";


    @RequestMapping(value = "/template")
    public ModelAndView template(@RequestParam("templatePath") String templatePath) {
        return new ModelAndView("/components/" + templatePath);
    }

}
