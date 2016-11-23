package com.cnh.frame.interceptor;

import com.cnh.frame.wraps.ClassWrap;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;

import java.util.List;

/**
 * 拦截器注册分发
 *
 * @author Administrator
 * @version v1.0.0
 * @since 2016/8/23
 */
public class InterceptorDispatcher {

    private static final String INTERCEPTOR_PACKAGE = "com.cnh.mvc.global.interceptor";


    public static void dispatch(InterceptorRegistry registry) {
        List<MappingInterceptor> interceptorList = getInterceptors();

        for (MappingInterceptor interceptor: interceptorList) {
            registry.addInterceptor(interceptor)
                    .addPathPatterns(interceptor.addPathPatterns())
                    .excludePathPatterns(interceptor.excludePathPatterns());
        }
    }


    private static List<MappingInterceptor> getInterceptors() {
        return ClassWrap.getImplementationsOfBeanType(MappingInterceptor.class, INTERCEPTOR_PACKAGE);
    }

}
