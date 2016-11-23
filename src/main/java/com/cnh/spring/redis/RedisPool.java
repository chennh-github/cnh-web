package com.cnh.spring.redis;

import lombok.Data;

/**
 * ${Description}
 *
 * @author Administrator
 * @version v1.0.0
 * @since 2016/8/11
 */
@Data
public class RedisPool {

    private Integer max;
    private Integer min;
    private Integer maxActive;
    private Integer maxWait;

}
