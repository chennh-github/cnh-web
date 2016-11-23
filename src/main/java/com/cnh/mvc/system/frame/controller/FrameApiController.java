package com.cnh.mvc.system.frame.controller;

import com.cnh.frame.crud.base.constant.CONSTANT;
import com.cnh.frame.crud.utils.Assist;
import com.cnh.frame.crud.exception.BaseException;
import com.cnh.frame.wraps.RSAWrap;
import com.cnh.mvc.system.adminInfo.entity.AdminInfo;
import com.cnh.mvc.system.frame.AdminInfoHolder;
import com.cnh.mvc.system.frame.RSAHolder;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * ${Description}
 *
 * @author Administrator
 * @version v1.0.0
 * @since 2016/9/8
 */
@Api(value = "框架接口")
@RestController
@RequestMapping(CONSTANT.API_SYSTEM_PATH + "/")
public class FrameApiController {


    @ApiOperation(value = "登录")
    @RequestMapping(value = "login", method = RequestMethod.POST)
    public Object login(@RequestParam("account") String account,
                        @RequestParam("password") String password) throws Exception {
        // RSA解析密码
        RSAWrap.RSA rsa = RSAHolder.get();
        Assist.notNull(rsa, "票据验证异常，请刷新页面重试。");
        rsa.decrypt(password);
        password = rsa.getDecrypt();

        if ("admin".equals(account) && "admin".equals(password)) {
            AdminInfo adminInfo = new AdminInfo();
            adminInfo.setAccount("admin");

            return adminInfo;
        }
        throw new BaseException("帐号或密码错误");
    }


    @ApiOperation(value = "登出")
    @RequestMapping(value = "logout")
    public boolean logout() {
        AdminInfoHolder.remove();
        return true;
    }

}
