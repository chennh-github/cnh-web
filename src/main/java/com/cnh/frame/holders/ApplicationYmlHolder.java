package com.cnh.frame.holders;

import com.cnh.spring.ApplicationYmlProperties;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
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
public class ApplicationYmlHolder implements ApplicationContextAware {

    public static final Map<String, Object> PROPERTIES = new HashMap<String, Object>();

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        ApplicationYmlProperties applicationYmlProperties = applicationContext.getBean(ApplicationYmlProperties.class);
        normalizeYmlMap(applicationYmlProperties.getCustom(), "custom", PROPERTIES);
    }

    /**
     * 将spring的yml配置文件转为只有一级的map
     * 多层的map转为.分隔符
     *
     * @param map
     * @param prefix
     * @param resultMap
     */
    public static void normalizeYmlMap(Map<String, Object> map, String prefix, Map<String, Object> resultMap) {
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            String key = StringUtils.isNotBlank(prefix) ? (prefix + "." + entry.getKey()) : entry.getKey();
            if (entry.getValue() instanceof Map) {
                normalizeYmlMap((Map<String, Object>) entry.getValue(), key, resultMap);
            } else {
                resultMap.put(key, entry.getValue());
            }
        }
    }

}
