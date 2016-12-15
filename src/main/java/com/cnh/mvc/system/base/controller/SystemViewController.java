package com.cnh.mvc.system.base.controller;

import com.cnh.frame.crud.base.controller.BaseViewController;
import com.cnh.frame.crud.base.controller.ViewController;
import com.cnh.frame.crud.base.entity.BaseEntity;
import com.cnh.frame.crud.base.service.BaseService;
import com.cnh.frame.crud.utils.Assist;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * ${Description}
 *
 * @author Administrator
 * @version v1.0.0
 * @since 2016/8/18
 */
public abstract class SystemViewController<T extends BaseEntity> extends BaseViewController<T> implements InitializingBean {

    private List<ViewController> viewControllerList = new ArrayList<ViewController>();

    @Override
    public void afterPropertiesSet() throws Exception {
        /**
         * 注册Super View路由解析器
         *
         * .../super/index
         * .../super/form
         * .../super/form/{id}
         * .../super/detail/{id}
         */
        viewControllerList.add(new ViewController() {
            @Override
            public boolean mappingRouter(String[] urls) {
                return urls.length > 3 && StringUtils.equalsIgnoreCase(urls[3], "super");
            }

            @Override
            public String getView() {
                return "super";
            }

            @Override
            protected String getModularName() {
                return SystemViewController.this.getModularName();
            }

            @Override
            public BaseService getService() {
                return SystemViewController.this.getService();
            }
        });
    }

    /**
     * 默认的路由分发器，分发路由到已绑定的viewController，如果未指定处理器则抛出异常
     *
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/**", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView dispatcher(HttpServletRequest request) throws Exception {
        ViewController viewController = getViewController(request.getRequestURI());
        assert viewController != null;
        return viewController.dispatcher(request);
    }

    private ViewController getViewController(String url) throws Exception {
        Assist.notNull(url, "URL 不能为null");
        String[] urls = url.split("/");
        Assist.threw(urls.length < 4, "URL解析错误");
        for (ViewController viewController : viewControllerList) {
            if (viewController.mappingRouter(urls)) {
                return viewController;
            }
        }
        Assist.threw("未指定路由解析器\r\nURL:" + url);
        return null;
    }

}
