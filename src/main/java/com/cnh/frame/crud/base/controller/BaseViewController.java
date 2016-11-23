package com.cnh.frame.crud.base.controller;

import com.cnh.frame.crud.base.constant.CONSTANT;
import com.cnh.frame.crud.base.entity.BaseEntity;
import com.cnh.frame.crud.base.service.BaseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
public abstract class BaseViewController<T extends BaseEntity> {

    private static final Logger LOGGER = LoggerFactory.getLogger(BaseApiController.class);

    protected abstract BaseService<T> getService();

    /**
     * 模块名称
     * @return
     */
    protected abstract String getModularName();

    /**
     * 列表页
     * @return
     */
    @RequestMapping("index")
    public ModelAndView index () {
        return new ModelAndView(CONSTANT.VIEW_SYSTEM_PATH + "/" + getModularName() + "/index");
    }

    /**
     * 新增页
     * @return
     * @throws Exception
     */
    @RequestMapping("form")
    public ModelAndView form () throws Exception {
        return new ModelAndView(CONSTANT.VIEW_SYSTEM_PATH + "/"  + getModularName() + "/form");
    }

    /**
     *编辑页
     * @param id
     * @return
     * @throws Exception
     */
    @RequestMapping("form/{id}")
    public ModelAndView form (@PathVariable("id") Long id) throws Exception {
        ModelAndView modelAndView = new ModelAndView(CONSTANT.VIEW_SYSTEM_PATH + "/"  + getModularName() + "/form");
        if (id != null && id != 0) {
            T entity = getService().get(id);
            modelAndView.getModelMap().put("entity", entity);
        }
        modelAndView.getModelMap().put("id", id);
        return modelAndView;
    }

    /**
     * 详情页
     * @param id
     * @return
     * @throws Exception
     */
    @RequestMapping("detail/{id}")
    public ModelAndView detail (@PathVariable("id") Long id) throws Exception {
        ModelAndView modelAndView = new ModelAndView(CONSTANT.VIEW_SYSTEM_PATH + "/"  + getModularName() + "/detail");
        if (id != null && id != 0) {
            T entity = getService().get(id);
            modelAndView.getModelMap().put("entity", entity);
        }
        modelAndView.getModelMap().put("id", id);
        return modelAndView;
    }

}
