package com.cnh.spring.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.cnh.spring.dataSource.DBContextHolder;
import com.cnh.spring.dataSource.DynamicDataSource;
import com.cnh.spring.utils.PropertiesLoaderUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.boot.bind.RelaxedPropertyResolver;
import org.springframework.boot.context.embedded.ServletRegistrationBean;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotationMetadata;

import javax.sql.DataSource;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class DataSourceConfig implements EnvironmentAware, ImportBeanDefinitionRegistrar {

    private static final Logger LOGGER = LoggerFactory.getLogger(DataSourceConfig.class);

    private Properties properties;

    private RelaxedPropertyResolver propertyResolver;

    /**
     * DRUID 扩展配置文件的地址
     */
    private static final String DRUID_EXTEND_PROPERTIES_PATH = "/config/datasource/datasource.druid.properties";

    @Bean
    public ServletRegistrationBean druidServlet() {
        return new ServletRegistrationBean(new StatViewServlet(), "/druid/*");
    }

    /**
     * 设置DRUID数据源
     * @return DataSource
     */
    private DataSource getDruidDataSource() {
        DruidDataSource dataSource = new DruidDataSource();

        dataSource.setUrl(this.propertyResolver.getProperty("url"));
        dataSource.setUsername(this.propertyResolver.getProperty("username"));
        dataSource.setPassword(this.propertyResolver.getProperty("password"));
        dataSource.configFromPropety(properties);

        return dataSource;
    }

    /**
     * 读数据源
     * @return
     */
    public DataSource readDataSource () {
        return this.getDruidDataSource();
    }

    /**
     * 写数据源
     * @return
     */
    public DataSource writeDataSource () {
        return this.getDruidDataSource();
    }

    /**
     * 读写数据源
     * @return
     */
    public DataSource readAndWriteDataSource () {
        return this.getDruidDataSource();
    }

    @Override
    public void setEnvironment(Environment environment) {
        try {
            this.propertyResolver = new RelaxedPropertyResolver(environment, "spring.datasource.");
            this.properties = PropertiesLoaderUtils.loadProperties(DRUID_EXTEND_PROPERTIES_PATH);
        } catch (FileNotFoundException e) {
            LOGGER.error("DRUID property(" + DRUID_EXTEND_PROPERTIES_PATH + ") does not exits.");
            e.printStackTrace();
        } catch (Exception e) {
            LOGGER.error("loading DRUID property (" + DRUID_EXTEND_PROPERTIES_PATH + ") error.", e);
            e.printStackTrace();
        }
    }

    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        Map<Object, Object> targetDataSources = new HashMap<Object, Object>();

        DataSource defaultDataSource = readAndWriteDataSource();
        // 将主数据源添加到更多数据源中
        targetDataSources.put(DBContextHolder.DB_TYPE_RW, defaultDataSource);
        DBContextHolder.dataSourceList.add(DBContextHolder.DB_TYPE_RW);

        // 添加更多数据源
        targetDataSources.put(DBContextHolder.DB_TYPE_R, readDataSource());
        DBContextHolder.dataSourceList.add(DBContextHolder.DB_TYPE_R);
        targetDataSources.put(DBContextHolder.DB_TYPE_W, writeDataSource());
        DBContextHolder.dataSourceList.add(DBContextHolder.DB_TYPE_W);


        // 创建DynamicDataSource
        GenericBeanDefinition beanDefinition = new GenericBeanDefinition();
        beanDefinition.setBeanClass(DynamicDataSource.class);
        beanDefinition.setSynthetic(true);
        MutablePropertyValues mpv = beanDefinition.getPropertyValues();
        mpv.addPropertyValue("defaultTargetDataSource", defaultDataSource);
        mpv.addPropertyValue("targetDataSources", targetDataSources);
        registry.registerBeanDefinition("dataSource", beanDefinition);

        LOGGER.info("Dynamic DataSource Registry");
    }
}
