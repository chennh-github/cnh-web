package com.cnh.mvc.system.demo.service;

import com.cnh.frame.crud.base.dao.IBaseDao;
import com.cnh.frame.crud.base.service.BaseService;
import com.cnh.mvc.system.demo.dao.IDemoDao;
import com.cnh.mvc.system.demo.entity.Demo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * ${Description}
 *
 * @author Administrator
 * @version v1.0.0
 * @since 2016/8/5
 */
@Service
public class DemoService extends BaseService<Demo> {

    @Autowired
    private IDemoDao demoDao;

    @Override
    protected IBaseDao<Demo> getDao() {
        return demoDao;
    }


}
