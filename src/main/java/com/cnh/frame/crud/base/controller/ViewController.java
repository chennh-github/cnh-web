package com.cnh.frame.crud.base.controller;

import com.cnh.frame.crud.base.constant.CONSTANT;
import com.cnh.frame.crud.base.entity.BaseEntity;
import com.cnh.frame.crud.base.service.BaseService;
import com.cnh.frame.crud.utils.Assist;
import com.cnh.frame.wraps.RequestWrap;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * View路由解析器
 *
 * @author Administrator
 * @version v1.0.0
 * @since 2016/12/13
 */
public abstract class ViewController<T extends BaseEntity> {


    public ViewController() {
        this.register();
    }

    /**
     * 路由匹配
     * @return
     */
    public abstract boolean mappingRouter(String[] urls);

    /**
     * 视图模块名
     * @return
     */
    public abstract String getView();

    protected abstract String getModularName();

    public abstract BaseService<T> getService();

    protected List<ViewRouter> viewRouterList = new ArrayList<ViewRouter>();

    public ModelAndView dispatcher(HttpServletRequest request) throws Exception {
        String url = request.getRequestURI();
        String[] urls = url.split("/");
        int length = urls.length;
        Assist.threw(length < 4, "URL解析错误\r\nURL:" + url);

        for (ViewRouter viewRouter : viewRouterList) {
            if (viewRouter.mapping(request, urls)) {
                return viewRouter.resolve(request, urls);
            }
        }
        Assist.threw("URL解析错误，未指定路由解析器\r\nURL:" + url);
        return null;
    }

    protected void register() {
        viewRouterList.clear();

        // 编辑页
        viewRouterList.add(new ViewRouter() {
            @Override
            public boolean mapping(HttpServletRequest request, String[] urls) {
                return StringUtils.equals(urls[urls.length - 2], "form");
            }

            @Override
            public ModelAndView resolve(HttpServletRequest request, String[] urls) throws Exception {
                Long id = Long.parseLong(urls[urls.length - 1]);
                ModelAndView modelAndView = new ModelAndView(CONSTANT.VIEW_SYSTEM_PATH + "/" + getView() + "/" + getModularName() + "/form", RequestWrap.getMap(request));
                T entity = getService().get(id);
                modelAndView.getModelMap().put("entity", entity);
                modelAndView.getModelMap().put("id", id);
                return modelAndView;
            }
        });

        // 详情页
        viewRouterList.add(new ViewRouter() {
            @Override
            public boolean mapping(HttpServletRequest request, String[] urls) {
                return StringUtils.equals(urls[urls.length - 2], "detail");
            }

            @Override
            public ModelAndView resolve(HttpServletRequest request, String[] urls) throws Exception {
                Long id = Long.parseLong(urls[urls.length - 1]);
                ModelAndView modelAndView = new ModelAndView(CONSTANT.VIEW_SYSTEM_PATH + "/" + getView() + "/" + getModularName() + "/detail", RequestWrap.getMap(RequestWrap.getRequest()));
                T entity = getService().get(id);
                modelAndView.getModelMap().put("entity", entity);
                modelAndView.getModelMap().put("id", id);
                return modelAndView;
            }
        });

        // 添加页
        viewRouterList.add(new ViewRouter() {
            @Override
            public boolean mapping(HttpServletRequest request, String[] urls) {
                return StringUtils.equals(urls[urls.length - 1], "form");
            }

            @Override
            public ModelAndView resolve(HttpServletRequest request, String[] urls) throws Exception {
                return new ModelAndView(CONSTANT.VIEW_SYSTEM_PATH + "/" + getView() + "/" + getModularName() + "/form", RequestWrap.getMap(RequestWrap.getRequest()));
            }
        });

        // 首页
        viewRouterList.add(new ViewRouter() {
            @Override
            public boolean mapping(HttpServletRequest request, String[] urls) {
                return StringUtils.equals(urls[urls.length - 1], "index");
            }

            @Override
            public ModelAndView resolve(HttpServletRequest request, String[] urls) throws Exception {
                return new ModelAndView(CONSTANT.VIEW_SYSTEM_PATH + "/" + getView() + "/" + getModularName() + "/index", RequestWrap.getMap(RequestWrap.getRequest()));
            }
        });
    }

}
