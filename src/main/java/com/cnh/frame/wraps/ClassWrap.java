package com.cnh.frame.wraps;

import com.cnh.frame.interceptor.MappingInterceptor;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.support.SimpleBeanDefinitionRegistry;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.core.type.filter.AssignableTypeFilter;
import org.springframework.core.type.filter.TypeFilter;

import java.util.ArrayList;
import java.util.List;

/**
 * ${Description}
 *
 * @author Administrator
 * @version v1.0.0
 * @since 2016/8/30
 */
public class ClassWrap {


    public static void main(String[] args) throws IllegalAccessException, InstantiationException, ClassNotFoundException {
        List<MappingInterceptor> list = getImplementationsOfBeanType(MappingInterceptor.class, "com.cnh.mvc.global.interceptor");
        for (MappingInterceptor interceptor: list) {
            System.out.println(interceptor);
        }
    }



    /**
     * 取clazzPackage包下所有clazz的实现类
     * @param clazz
     * @param clazzPackage
     * @return
     */
    public static <T> List<T> getImplementationsOfBeanType(Class<T> clazz, String clazzPackage) {
        BeanDefinitionRegistry registry = new SimpleBeanDefinitionRegistry();
        ClassPathBeanDefinitionScanner scanner = new ClassPathBeanDefinitionScanner(registry);

        TypeFilter filter = new AssignableTypeFilter(clazz);
        scanner.setIncludeAnnotationConfig(false);
        scanner.resetFilters(true);
        scanner.addIncludeFilter(filter);
        scanner.scan(clazzPackage);

        String[] beanNames = registry.getBeanDefinitionNames();
        List<T> list = new ArrayList<T>(beanNames.length);

        DefaultListableBeanFactory factory = new DefaultListableBeanFactory();
        for (String beanName: beanNames) {
            factory.registerBeanDefinition(beanName, registry.getBeanDefinition(beanName));
            list.add(factory.getBean(beanName, clazz));
        }
        return list;
    }




}
