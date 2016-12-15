package com.cnh.frame.crud.base.entity;

import net.sf.json.JSONObject;

public class BaseEntity {

    private Long id;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOrderNo() {
        return null;
    }

    public void setOrderNo(Long orderNo) {
    }

    public String orderBy() {
        // desc 序号越大，越靠前
        // asc 序号越小，越靠前
        // 配合排序规则
        return "desc";
    }

    @Override
    public String toString() {
        return JSONObject.fromObject(this).toString();
    }
}
