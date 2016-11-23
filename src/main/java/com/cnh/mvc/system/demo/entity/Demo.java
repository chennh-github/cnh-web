package com.cnh.mvc.system.demo.entity;

import com.cnh.frame.crud.base.entity.BaseEntity;
import lombok.Data;

@Data
public class Demo extends BaseEntity {

    private String account;

    private String password;

    private String name;

    private Integer status;


}
