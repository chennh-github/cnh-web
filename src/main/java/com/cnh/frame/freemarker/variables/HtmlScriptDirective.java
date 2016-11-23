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
public class HtmlScriptDirective implements TemplateDirectiveModel {


    /**
     * 分隔符
     */
    private final static String SPLIT_FLAG = ",";

    @Override
    public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body) throws TemplateException, IOException {
        env.getOut().write(this.generalTemplates(DirectiveUtils.getString("src", params)));
    }


    private String generalTemplates(String srcString) {
        StringBuffer sb = new StringBuffer();
        if (StringUtils.isNotBlank(srcString)) {
            String[] srcs = srcString.split(SPLIT_FLAG);
            for (String src: srcs) {
                if (StringUtils.isNotBlank(src)) {
                    sb.append("<script type='text/javascript' src='")
                            .append(RequestWrap.getFullPath(StringUtils.trim(src)))
                            .append("?v=")
                            .append(EnvironmentContext.VERSION)
                            .append("' ></script>\n");
                }
            }
        }
        return sb.toString();
    }


}
