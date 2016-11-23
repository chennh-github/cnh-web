package com.cnh.spring.redis;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * ${Description}
 *
 * @author Administrator
 * @version v1.0.0
 * @since 2016/8/11
 */
@Data
@ConfigurationProperties(prefix = "spring.redis")
public class RedisProperty {

    private Integer database;
    private String host;
    private Integer port;
    private String password;
    private RedisPool pool;
    private Integer timeout;

}
