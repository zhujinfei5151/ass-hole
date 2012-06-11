package com.tmall.asshole.event.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class SpringBeanUtils implements ApplicationContextAware {

	private static ApplicationContext applicationContext;

	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		SpringBeanUtils.applicationContext = applicationContext;

	}

	public static Object getBean(String beanName) {
		return applicationContext.getBean(beanName);
	}
}
