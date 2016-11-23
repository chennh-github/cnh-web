package com.cnh.frame.crud.base.controller;

import com.cnh.frame.crud.base.entity.BaseEntity;
import com.cnh.frame.crud.base.service.BaseService;
import com.cnh.frame.wraps.RequestWrap;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

public abstract class BaseApiController<T extends BaseEntity> {


    private static final Logger LOGGER = LoggerFactory.getLogger(BaseApiController.class);


    protected abstract BaseService<T> getService();

    /**
     * 读取数据列表
     * 如果传递了分页则执行分页读取，否则返回全部数据
     * @param pageNo
     * @param pageSize
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public Object list (@RequestParam(value = "pageNo", required = false) Integer pageNo,
                        @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize,
                        HttpServletRequest request) throws Exception {
        if (pageNo != null) {
            PageHelper.startPage(pageNo, pageSize);
            return new PageInfo<T>(getService().select(RequestWrap.getMap(request)));
        } else {
            return getService().select(RequestWrap.getMap(request));
        }
    }

    /**
     * 根据ID读取对象
     * @param id
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public T get (@PathVariable(value = "id") Long id) throws Exception{
        return getService().get(id);
    }

    /**
     * 更新对象
     * @param entity
     * @throws Exception
     */
    @RequestMapping(method = RequestMethod.PUT)
    @ResponseBody
    public void update (@RequestBody T entity) throws Exception {
        getService().update(entity);
    }

    /**
     * "新增对象
     * @param entity
     * @throws Exception
     */
    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public void save (@RequestBody T entity) throws Exception {
        getService().save(entity);
    }

    /**
     * 根据IDS删除对象
     * @param ids
     * @throws Exception
     */
    @RequestMapping(value = "/{ids}", method = RequestMethod.DELETE)
    @ResponseBody
    public void delete (@PathVariable(value = "ids") Long[] ids) throws Exception {
        getService().delete(ids);
    }

}
