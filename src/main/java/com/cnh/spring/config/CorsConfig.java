package com.cnh.spring.config;

import com.cnh.frame.crud.base.constant.CONSTANT;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**
 * ${Description}
 *
 * @author Administrator
 * @version v1.0.0
 * @since 2016/10/31
 */
@Configuration
@ConfigurationProperties(prefix = "custom.cors", locations = "classpath:config/application.yml")
@Data
public class CorsConfig {

    private String allowedOrigin;

    private String allowedHeader;

    private String allowedMethod;

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration(CONSTANT.ROUTE_CORS_PATH + "/**", buildCorsConfig()); // 4
        return new CorsFilter(source);
    }


    private CorsConfiguration buildCorsConfig () {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.addAllowedOrigin(allowedOrigin); // 1
        corsConfiguration.addAllowedHeader(allowedHeader); // 2
        corsConfiguration.addAllowedMethod(allowedMethod); // 3
        return corsConfiguration;
    }

}
