package com.cnh.frame.crud.base.enums;

import lombok.Data;

/**
 * ${Description}
 *
 * @author Administrator
 * @version v1.0.0
 * @since 2016/10/26
 */
@Data
public class EnumEntity {

    private Integer value;

    private String name;

    public EnumEntity(int value, String name) {
        this.value = value;
        this.name = name;
    }

}
