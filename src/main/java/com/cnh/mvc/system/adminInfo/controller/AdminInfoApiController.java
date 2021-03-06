package com.cnh.mvc.system.adminInfo.controller;

import com.cnh.frame.crud.base.constant.CONSTANT;
import com.cnh.frame.crud.base.service.BaseService;
import com.cnh.mvc.system.base.controller.SystemApiController;
import com.cnh.mvc.system.adminInfo.entity.AdminInfo;
import com.cnh.mvc.system.adminInfo.service.AdminInfoService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

@Api("AdminInfo Api")
@RestController
@RequestMapping(CONSTANT.API_SYSTEM_PATH + "/adminInfo")
public class AdminInfoApiController extends SystemApiController<AdminInfo> {

	@Autowired
	private AdminInfoService adminInfoService;

	@Override
	protected BaseService<AdminInfo> getService(){
		return adminInfoService;
	}

}
