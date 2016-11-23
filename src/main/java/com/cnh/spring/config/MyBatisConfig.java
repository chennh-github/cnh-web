package com.cnh.spring.config;

import com.cnh.spring.dataSource.DynamicDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.bind.RelaxedPropertyResolver;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@ConditionalOnClass({EnableTransactionManagement.class})
@AutoConfigureAfter({DataSourceConfig.class})
@MapperScan(basePackages = {"com.cnh.mvc.**.*Dao"}, sqlSessionTemplateRef = "mybatisSqlSessionTemplate")
public class MyBatisConfig implements EnvironmentAware {

    private static final Logger LOGGER = LoggerFactory.getLogger(MyBatisConfig.class);

    private RelaxedPropertyResolver propertyResolver;

    @Autowired
    private DynamicDataSource dynamicDataSource;

    @Bean(name = "mybatisSqlSessionFactory")
    @ConditionalOnMissingBean
    public SqlSessionFactory sqlSessionFactory() {
        try {
            SqlSessionFactoryBean sessionFactoryBean = new SqlSessionFactoryBean();
            sessionFactoryBean.setDataSource(dynamicDataSource);
            sessionFactoryBean.setMapperLocations(new PathMatchingResourcePatternResolver()
                    .getResources(propertyResolver.getProperty("mapperLocations")));
            sessionFactoryBean.setConfigLocation(new DefaultResourceLoader()
                    .getResource(propertyResolver.getProperty("configLocation")));
            return sessionFactoryBean.getObject();
        } catch (Exception e) {
            LOGGER.error("Could not confiure mybatis session factory");
            return null;
        }
    }

    @Bean(name = "mybatisDataSourceTransactionManager")
    @ConditionalOnMissingBean
    public DataSourceTransactionManager dataSourceTransactionManager (DynamicDataSource dynamicDataSource) {
        return new DataSourceTransactionManager(dynamicDataSource);
    }

    @Bean(name = "mybatisSqlSessionTemplate", destroyMethod = "close")
    @ConditionalOnMissingBean
    public SqlSessionTemplate sqlSessionTemplate (@Qualifier("mybatisSqlSessionFactory") SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }


    @Override
    public void setEnvironment(Environment environment) {
        this.propertyResolver = new RelaxedPropertyResolver(environment, "spring.mybatis.");
    }
}
