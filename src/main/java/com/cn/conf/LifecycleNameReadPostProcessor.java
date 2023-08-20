package com.cn.conf;

import com.cn.model.Person;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

public class LifecycleNameReadPostProcessor implements BeanPostProcessor {

    @Override
    public Object postProcessAfterInitialization(@NotNull Object bean,
                                                 @NotNull String beanName) throws BeansException {
        if (bean instanceof Person) {
            Person person = (Person) bean;
            System.out.println("LifecycleNameReadPostProcessor ------> " + person.getName());
        }
        return bean;
    }
}

