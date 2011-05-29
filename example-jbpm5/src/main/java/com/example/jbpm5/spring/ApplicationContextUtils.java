package com.example.jbpm5.spring;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class ApplicationContextUtils implements ApplicationContextAware {

	private static ApplicationContext applicationContext;
	
	public void setApplicationContext(ApplicationContext value)
			throws BeansException {
		applicationContext = value;
	}
	
	public static ApplicationContext getApplicationContext() {
		return applicationContext;
	}

}
