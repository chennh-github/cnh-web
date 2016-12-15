package com.cnh.mvc.system.menuRelate.service;

import com.cnh.frame.crud.base.dao.IBaseDao;
import com.cnh.frame.crud.base.service.BaseService;
import com.cnh.mvc.system.menuRelate.entity.MenuRelate;
import com.cnh.mvc.system.menuRelate.dao.IMenuRelateDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class MenuRelateService extends BaseService<MenuRelate>{

    @Autowired
    private IMenuRelateDao menuRelateDao;

    @Override
    protected IBaseDao<MenuRelate>getDao(){
        return menuRelateDao;
    }

}
