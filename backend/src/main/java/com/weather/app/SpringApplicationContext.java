package com.weather.app;

import lombok.NonNull;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.lang.NonNullApi;

public class SpringApplicationContext implements ApplicationContextAware {
    private static ApplicationContext CONTEXT;

    @Override
    public void setApplicationContext(@NonNull ApplicationContext applicationContext) throws BeansException {
        CONTEXT = applicationContext;
    }

    public static Object getBean(String beanName) {
        return CONTEXT.getBean(beanName);
    }
}