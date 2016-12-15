package com.cnh.frame.freemarker.variables;

import com.cnh.frame.freemarker.support.Minify;
import com.cnh.frame.holders.ApplicationYmlHolder;
import com.cnh.frame.wraps.RequestWrap;
import com.cnh.frame.freemarker.support.DirectiveUtils;
import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
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
        env.getOut().write(this.generalTemplates(DirectiveUtils.getString("href", params), DirectiveUtils.getString("srcValue", params)));
    }


    private String generalTemplates(String hrefString, String srcValue) throws IOException {
        // 是否需要合并静态脚本
        boolean staticMinify = Boolean.valueOf(String.valueOf(ApplicationYmlHolder.PROPERTIES.get("custom.static.minify")));

        StringBuilder sb = new StringBuilder();
        if (StringUtils.isNotBlank(hrefString)) {
            List<String> hrefs = Arrays.asList(hrefString.split(SPLIT_FLAG));
            if (staticMinify) {
                try {
                    sb.append(generalLink(Minify.minifyCSS(hrefs, srcValue)));
                } catch (Exception e) {
                    sb.append(normal(hrefs));
                }
            } else {
                sb.append(normal(hrefs));
            }
        }
        return sb.toString();
    }

    private String normal(List<String> hrefs) {
        StringBuilder sb = new StringBuilder();
        for (String href : hrefs) {
            if (StringUtils.isNotBlank(href)) {
                sb.append(generalLink(RequestWrap.getFullPath(StringUtils.trim(href))));
            }
        }
        return sb.toString();
    }


    private String generalLink(String href) {
        return "<link type='text/css' rel='stylesheet' href='" + href + "?v=" + EnvironmentContext.VERSION + "' />\n";
    }

}
