package com.cnh.spring;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * ${Description}
 *
 * @author Administrator
 * @version v1.0.0
 * @since 2016/8/23
 */
@Component
@ConfigurationProperties
@Data
public class ApplicationYmlProperties {

    /**
     * 自定义属性Map
     * 访问时使用全称 custom.customName
     */
    private Map<String, Object> custom = new HashMap<String, Object>();

}
