package com.cnh.spring.config;

import com.cnh.frame.freemarker.variables.HtmlLinkDirective;
import com.cnh.frame.freemarker.variables.HtmlScriptDirective;
import com.cnh.frame.freemarker.variables.I18nDirective;
import com.cnh.frame.freemarker.variables.PathDirective;
import freemarker.template.TemplateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

/**
 * ${Description}
 *
 * @author Administrator
 * @version v1.0.0
 * @since 2016/8/18
 */
@Configuration
public class FreemarkerConfig {


    @Autowired
    protected freemarker.template.Configuration configuration;
    @Autowired
    protected org.springframework.web.servlet.view.freemarker.FreeMarkerViewResolver resolver;
    @Autowired
    protected org.springframework.web.servlet.view.InternalResourceViewResolver springResolver;


    @PostConstruct
    public void setSharedVariable () {

        configuration.setDefaultEncoding("UTF-8");
        configuration.setDateFormat("yyyy-MM-dd");
        configuration.setTimeFormat("HH:mm:ss");
        configuration.setDateTimeFormat("yyyy-MM-dd HH:mm:ss");
        configuration.setNumberFormat("0.##");
        configuration.setBooleanFormat("true,false");

        // 自定义标签
        configuration.setSharedVariable("path", new PathDirective());
        configuration.setSharedVariable("link", new HtmlLinkDirective());
        configuration.setSharedVariable("script", new HtmlScriptDirective());
        configuration.setSharedVariable("i18n", new I18nDirective());

        // 配置setting
        try {
            configuration.setSetting(freemarker.template.Configuration.TEMPLATE_UPDATE_DELAY_KEY, "5");
            configuration.setSetting(freemarker.template.Configuration.WHITESPACE_STRIPPING_KEY, "true");
        } catch (TemplateException e) {
            e.printStackTrace();
        }

        // 配置Spring的视图解析器
        springResolver.setPrefix("/webapp/views/");  //解析前缀后XXX路径下的jsp文件
        springResolver.setSuffix(".ftl");
        springResolver.setOrder(1);

        // 配置freemarker的视图解析器
        resolver.setSuffix(".ftl");
        resolver.setCache(false);
        resolver.setRequestContextAttribute("request");
        resolver.setOrder(0);

    }

}
