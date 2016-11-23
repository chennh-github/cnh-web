package com.cnh.mvc.system.base.controller;

import com.cnh.frame.crud.base.constant.CONSTANT;
import com.cnh.frame.crud.base.controller.BaseViewController;
import com.cnh.frame.crud.base.entity.BaseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * ${Description}
 *
 * @author Administrator
 * @version v1.0.0
 * @since 2016/8/18
 */
public abstract class SystemViewController<T extends BaseEntity> extends BaseViewController<T> {

    /**
     * 列表页
     * @return
     */
    @RequestMapping("super/index")
    public ModelAndView superIndex () {
        return new ModelAndView(CONSTANT.VIEW_SYSTEM_PATH + "/super/" + getModularName() + "/index");
    }

    /**
     * 新增页
     * @param id
     * @return
     * @throws Exception
     */
    @RequestMapping("super/form")
    public ModelAndView superForm () throws Exception {
        return new ModelAndView(CONSTANT.VIEW_SYSTEM_PATH + "/super/"  + getModularName() + "/form");
    }

    /**
     * 编辑页
     * @param id
     * @return
     * @throws Exception
     */
    @RequestMapping("super/form/{id}")
    public ModelAndView superForm (@PathVariable("id") Long id) throws Exception {
        ModelAndView modelAndView = new ModelAndView(CONSTANT.VIEW_SYSTEM_PATH + "/super/"  + getModularName() + "/form");
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
    @RequestMapping("super/detail/{id}")
    public ModelAndView superDetail (@PathVariable("id") Long id) throws Exception {
        ModelAndView modelAndView = new ModelAndView(CONSTANT.VIEW_SYSTEM_PATH + "/super/"  + getModularName() + "/detail");
        if (id != null && id != 0) {
            T entity = getService().get(id);
            modelAndView.getModelMap().put("entity", entity);
        }
        modelAndView.getModelMap().put("id", id);
        return modelAndView;
    }

}
