package com.cnh.mvc.system.adminInfo.service;

import com.cnh.frame.crud.base.dao.IBaseDao;
import com.cnh.frame.crud.base.service.BaseService;
import com.cnh.mvc.system.adminInfo.entity.AdminInfo;
import com.cnh.mvc.system.adminInfo.dao.IAdminInfoDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class AdminInfoService extends BaseService<AdminInfo>{

    @Autowired
    private IAdminInfoDao adminInfoDao;

    @Override
    protected IBaseDao<AdminInfo>getDao(){
        return adminInfoDao;
    }

}
