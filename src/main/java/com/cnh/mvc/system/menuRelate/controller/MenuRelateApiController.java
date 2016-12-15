package com.cnh.mvc.system.menuRelate.controller;

import com.cnh.frame.crud.base.constant.CONSTANT;
import com.cnh.frame.crud.base.service.BaseService;
import com.cnh.mvc.system.base.controller.SystemApiController;
import com.cnh.mvc.system.menuRelate.entity.MenuRelate;
import com.cnh.mvc.system.menuRelate.service.MenuRelateService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

@Api("MenuRelate Api")
@RestController
@RequestMapping(CONSTANT.API_SYSTEM_PATH + "/menuRelate")
public class MenuRelateApiController extends SystemApiController<MenuRelate> {

	@Autowired
	private MenuRelateService menuRelateService;

	@Override
	protected BaseService<MenuRelate> getService(){
		return menuRelateService;
	}

}
