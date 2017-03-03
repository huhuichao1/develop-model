package com.develop.model.web;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


public class ApplicationContextUtil {

	private static ApplicationContext applicationContext;

	public static synchronized ApplicationContext getApplicationContext() {

		if (applicationContext == null) {
			String[] configLocations = { "applicationContext.xml" };
			applicationContext = new ClassPathXmlApplicationContext(configLocations);
			/**
			 * 注册关闭挂钩，正确关闭ctrl+c或kill(kill -15)，禁止kill -9
			 */
			Runtime.getRuntime().addShutdownHook(new Thread() {
				@Override
				public void run() {
					((ClassPathXmlApplicationContext) applicationContext).close();
				}
			});
		}
		return applicationContext;
	}

	public static void main(String[] args) {
		ApplicationContextUtil.getApplicationContext();
	}
}
