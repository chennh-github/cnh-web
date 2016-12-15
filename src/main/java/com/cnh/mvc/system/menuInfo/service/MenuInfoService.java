package com.cnh.mvc.system.menuInfo.service;

import com.cnh.frame.crud.base.dao.IBaseDao;
import com.cnh.frame.crud.base.service.BaseService;
import com.cnh.mvc.system.menuInfo.entity.MenuInfo;
import com.cnh.mvc.system.menuInfo.dao.IMenuInfoDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class MenuInfoService extends BaseService<MenuInfo>{

    @Autowired
    private IMenuInfoDao menuInfoDao;

    @Override
    protected IBaseDao<MenuInfo>getDao(){
        return menuInfoDao;
    }


    @Override
    protected boolean beforeSaveOrUpdate(MenuInfo entity) throws Exception {

        // 填充ORDER_NO
        if (entity.getId() == null) {
            entity.setOrderNo(menuInfoDao.getMaxOrderNo(entity.getMenuType()) + 1);
        }

        return super.beforeSaveOrUpdate(entity);
    }
}
