package com.cnh.frame.freemarker.variables;

import com.cnh.frame.freemarker.support.DirectiveUtils;
import com.cnh.frame.holders.ApplicationContextHolder;
import com.cnh.frame.wraps.RequestWrap;
import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;
import lombok.Data;
import org.springframework.boot.env.YamlPropertySourceLoader;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.util.*;

/**
 * ${Description}
 *
 * @author Administrator
 * @version v1.0.0
 * @since 2016/11/16
 */
public class I18nDirective implements TemplateDirectiveModel {

    private static final String I18N = "i18n/";

    private static final Map<String, Priority> i18nSourceMap = new LinkedHashMap<String, Priority>();

    private static final int PAGE_LIMIT = 20;

    private static final int PAGE_CLEAR_COUNT = 10;

    private static final String ALLOWED_EXT = ".yml";

    @Override
    public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body) throws TemplateException, IOException {
        Object i18n = getI18nSource(I18N + DirectiveUtils.getString("path", params) + ALLOWED_EXT);
        RequestWrap.getRequest().setAttribute("i18n", i18n);
    }


    private Object getI18nSource(String path) throws IOException {
        Priority priority = null;
        if (i18nSourceMap.containsKey(path)) {
            priority = i18nSourceMap.get(path);
        } else {
            optimizeMap();
            priority = new Priority(path, readI18nYml(path), 0);
            i18nSourceMap.put(path, priority);
        }
        priority.setPriority(priority.getPriority() + 1);
        return priority.getValue();
    }

    private Object readI18nYml(String path) throws IOException {
        Resource resource = ApplicationContextHolder.getApplicationContext().getResource("classpath:" + path);
        YamlPropertySourceLoader loader = new YamlPropertySourceLoader();
        return loader.load("i18n", resource, null).getSource();
    }


    private void optimizeMap() {
        if (i18nSourceMap.size() > PAGE_LIMIT) {
            List<Priority> priorityCollection = (List<Priority>) i18nSourceMap.values();
            Collections.sort(priorityCollection, new Comparator<Priority>() {
                @Override
                public int compare(Priority prev, Priority next) {
                    return next.getPriority() - prev.getPriority();
                }
            });
            List<Priority> removeList = priorityCollection.subList(PAGE_LIMIT - PAGE_CLEAR_COUNT, priorityCollection.size() - 1);
            for (Priority priority : removeList) {
                i18nSourceMap.remove(priority.getName());
            }
        }
    }


    @Data
    private class Priority {

        private String name;

        private Object value;

        private int priority;

        public Priority(String name, Object value, int priority) {
            this.name = name;
            this.value = value;
            this.priority = priority;
        }
    }


}
