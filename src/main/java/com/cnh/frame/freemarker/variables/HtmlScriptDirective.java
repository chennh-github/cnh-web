package com.cnh.frame.freemarker.variables;

import com.cnh.frame.freemarker.support.DirectiveUtils;
import com.cnh.frame.freemarker.support.Minify;
import com.cnh.frame.holders.ApplicationYmlHolder;
import com.cnh.frame.wraps.RequestWrap;
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
public class HtmlScriptDirective implements TemplateDirectiveModel {

    /**
     * 分隔符
     */
    private final static String SPLIT_FLAG = ",";

    @Override
    public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body) throws TemplateException, IOException {
        env.getOut().write(this.generalTemplates(DirectiveUtils.getString("src", params), DirectiveUtils.getString("srcValue", params)));
    }


    private String generalTemplates(String srcString, String srcValue) throws IOException {
        // 是否需要合并静态脚本
        boolean staticMinify = Boolean.valueOf(String.valueOf(ApplicationYmlHolder.PROPERTIES.get("custom.static.minify")));

        StringBuilder sb = new StringBuilder();
        if (StringUtils.isNotBlank(srcString)) {
            List<String> srcs = Arrays.asList(srcString.split(SPLIT_FLAG));
            if (staticMinify) {
                try {
                    sb.append(generalScript(Minify.minifyJS(srcs, srcValue)));
                } catch (Exception e) {
                    sb.append(normal(srcs));
                }
            } else {
                sb.append(normal(srcs));
            }
        }
        return sb.toString();
    }


    private String normal(List<String> srcs) {
        StringBuilder sb = new StringBuilder();
        for (String src : srcs) {
            if (StringUtils.isNotBlank(src)) {
                sb.append(generalScript(RequestWrap.getFullPath(StringUtils.trim(src))));
            }
        }
        return sb.toString();
    }


    private String generalScript(String src) {
        if (src.contains("?")) {
            src += "&v=" + EnvironmentContext.VERSION;
        } else {
            src += "?v=" + EnvironmentContext.VERSION;
        }
        return "<script type='text/javascript' src='" + src + "' ></script>\n";
    }

}
