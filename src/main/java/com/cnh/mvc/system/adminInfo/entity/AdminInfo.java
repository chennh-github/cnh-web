package com.cnh.mvc.system.adminInfo.entity;

import com.cnh.frame.crud.base.entity.BaseEntity;
import lombok.Data;

/**
 * ${Description}
 *
 * @author Administrator
 * @version v1.0.0
 * @since 2016/9/8
 */
@Data
public class AdminInfo extends BaseEntity {

    private String account;

    private String password;

}
