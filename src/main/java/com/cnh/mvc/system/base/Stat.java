package com.cnh.mvc.system.base;

import com.cnh.frame.crud.base.constant.StatMap;

/**
 * ${Description}
 *
 * @author Administrator
 * @version v1.0.0
 * @since 2016/12/9
 */
public class Stat {

    public static final StatMap<Integer, String> SEX = new StatMap<Integer, String>(
            new StatMap.Entry<Integer, String>(1, "男"),
            new StatMap.Entry<Integer, String>(2, "女")
    );

}
