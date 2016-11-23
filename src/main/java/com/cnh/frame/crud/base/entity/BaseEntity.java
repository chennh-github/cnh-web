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

    @Override
    public String toString() {
        return JSONObject.fromObject(this).toString();
    }
}
