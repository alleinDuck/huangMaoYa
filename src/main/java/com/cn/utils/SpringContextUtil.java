package com.cn.utils;

import org.jetbrains.annotations.NotNull;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class SpringContextUtil implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    /**
     * 实现ApplicationContextAware接口的回调方法，设置上下文环境
     */
    @Override
    public void setApplicationContext(@NotNull ApplicationContext applicationContext){
        SpringContextUtil.applicationContext = applicationContext;
    }

    /**
     * 获得spring上下文
     * @return ApplicationContext spring上下文
     */
    public static ApplicationContext getApplicationContext(){
        return applicationContext;
    }

    /**
     * 获取bean
     * @param name service注解方式name为小驼峰格式
     * @return  Object bean的实例对象
     */
    public static Object getBean(String name) throws BeansException {
        return getApplicationContext().getBean(name);
    }


    public static <T> T getBean(Class<T> className) {
        return getApplicationContext().getBean(className);
    }

    /**
     * 通过接口类型，返回所有实现了这个接口的实现类
     *
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> Map<String, T> getBeansOfType(Class<T> clazz) {
        return getApplicationContext().getBeansOfType(clazz);
    }

}
