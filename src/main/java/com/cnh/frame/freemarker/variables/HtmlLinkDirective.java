package com.cnh.frame.freemarker.variables;

import com.cnh.frame.wraps.RequestWrap;
import com.cnh.frame.freemarker.support.DirectiveUtils;
import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.util.Map;

/**
 * ${Description}
 *
 * @author Administrator
 * @version v1.0.0
 * @since 2016/8/11
 */
public class HtmlLinkDirective implements TemplateDirectiveModel {


    /**
     * 分隔符
     */
    private final static String SPLIT_FLAG = ",";

    @Override
    public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body) throws TemplateException, IOException {
        env.getOut().write(this.generalTemplates(DirectiveUtils.getString("href", params)));
    }


    private String generalTemplates(String hrefString) {
        StringBuffer sb = new StringBuffer();
        if (StringUtils.isNotBlank(hrefString)) {
            String[] hrefs = hrefString.split(SPLIT_FLAG);
            for (String href: hrefs) {
                if (StringUtils.isNotBlank(href)) {
                    sb.append("<link type='text/css' rel='stylesheet' href='")
                            .append(RequestWrap.getFullPath(StringUtils.trim(href)))
                            .append("?v=")
                            .append(EnvironmentContext.VERSION)
                            .append("' />\n");
                }
            }
        }
        return sb.toString();
    }


}
