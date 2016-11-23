package com.cnh.mvc.api;

import com.cnh.mvc.system.demo.entity.Demo;
import com.github.pagehelper.Page;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * ${Description}
 *
 * @author Administrator
 * @version v1.0.0
 * @since 2016/11/23
 */
@FeignClient(url = "${custom.api}")
public interface DemoApi {

    @RequestMapping(value = "/api/system/demo", method = RequestMethod.GET)
    public Page<Demo> query();
}
