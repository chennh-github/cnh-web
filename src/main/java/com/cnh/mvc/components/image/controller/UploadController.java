package com.cnh.mvc.components.image.controller;

import com.cnh.frame.freemarker.variables.EnvironmentContext;
import com.cnh.mvc.components.ComponentController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

/**
 * ${Description}
 *
 * @author Administrator
 * @version v1.0.0
 * @since 2016/9/6
 */
@Controller
@RequestMapping(ComponentController.COMPONENT_ROUTE_PATH + "/image/upload")
public class UploadController {

    @RequestMapping("/index")
    public Object index(@RequestParam(value = "urls", required = false) String urls,
                        @RequestParam(value = "callback", required = false) String callback,
                        @RequestParam(value = "businessType", required = true) String businessType,
                        @RequestParam(value = "maxCount", required = false, defaultValue = "1") Integer maxCount) {
        ModelAndView view = new ModelAndView("components/image/upload/index");
        Map<String, Object> modelMap = view.getModelMap();
        modelMap.put("urls", urls);
        modelMap.put("callback", callback);
        modelMap.put("businessType", businessType);
        modelMap.put("maxCount", maxCount);
        modelMap.put("staticVersion", EnvironmentContext.VERSION);
        return view;
    }

}
