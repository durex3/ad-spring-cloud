package com.durex.ad.index;

import com.google.common.collect.Maps;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.PriorityOrdered;
import org.springframework.stereotype.Component;
import java.util.Map;

/**
 * @author gelong
 * @date 2019/11/21 0:57
 */
@Component
public class DataTable implements ApplicationContextAware, PriorityOrdered {

    private static ApplicationContext applicationContext;
    private static final Map<Class, Object> DATA_TABLE_MAP = Maps.newConcurrentMap();

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        DataTable.applicationContext = applicationContext;
    }

    @Override
    public int getOrder() {
        return PriorityOrdered.HIGHEST_PRECEDENCE;
    }

    @SuppressWarnings(value = "all")
    public static <T> T of(Class<T> clazz) {
        return (T)DATA_TABLE_MAP.computeIfAbsent(clazz, bean(clazz));
    }

    @SuppressWarnings(value = "all")
    private static <T> T bean(String beanName) {
        return (T)applicationContext.getBean(beanName);
    }

    @SuppressWarnings(value = "all")
    private static <T> T bean(Class clazz) {
        return (T)applicationContext.getBean(clazz);
    }
}
