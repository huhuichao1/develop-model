package com.develop.model.web;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * The Class DefaultServiceLocator.
 */
public class DefaultServiceLocator {

	/**
	 * 全局缓存locator对应的ApplicationContext
	 */
	private static Map<String, ClassPathXmlApplicationContext> contexts = new ConcurrentHashMap<String, ClassPathXmlApplicationContext>();

	/**
	 * Gets the spring config file name.
	 * 对于DefaultServiceLocator，返回default_service_locator.xml
	 * 
	 * @param clz
	 *            the clz
	 * @return the spring config file name
	 */
	protected static String getSpringConfigFileName(Class<?> clz) {
		String currentLocatorName = clz.getSimpleName();
		StringBuffer configFileName = new StringBuffer("");
		for (int i = 0, j = currentLocatorName.length(); i < j; i++) {
			char tempChar = currentLocatorName.charAt(i);
			if (Character.isLowerCase(tempChar)) {
				configFileName.append(tempChar);
			} else if (Character.isUpperCase(currentLocatorName.charAt(i))) {
				configFileName.append("_");
				configFileName.append(Character.toLowerCase(tempChar));
			}
		}
		configFileName.append(".xml");
		return configFileName.substring(1, configFileName.length()).toString();
	}

	/**
	 * 根据locator class获取ApplicationContext
	 * 
	 * @param clz
	 * @return
	 */
	public static ApplicationContext getApplicationContext(Class<?> clz) {
		String xmlName = getSpringConfigFileName(clz);
		return getApplicationContext(xmlName);
	}

	/**
	 * 根据配置文件名称获取ApplicationContext
	 * 
	 * @param xmlName
	 * @return
	 */
	public static ApplicationContext getApplicationContext(String xmlName) {
		ClassPathXmlApplicationContext context = contexts.get(xmlName);
		if (context == null) {
			if (StringUtils.isNotBlank(xmlName)) {
				List<Object> beanList = new ArrayList<Object>();
				context = new ClassPathXmlApplicationContext();
				context.setConfigLocation(xmlName);

				/**
				 * 添加BeanFactoryPostProcessor的目的是获得原始的bean对象，
				 * 直接getBean拿到的可能是代理后的对象
				 */
				context.addBeanFactoryPostProcessor(createProcessor(beanList));

				// context.
				/**
				 * 在初始化之前先把context放到缓存中 防止在初始化过程中存在循环调用，反复初始化 造成程序卡死
				 */
				contexts.put(xmlName, context);
				context.refresh();

				for (Object bean : beanList) {
					// 解决跨locator的依赖
					resolveInjects(bean);
				}
			}
		}
		return context;
	}

	private static BeanFactoryPostProcessor createProcessor(
			final List<Object> beanList) {
		return new BeanFactoryPostProcessor() {
			@Override
			public void postProcessBeanFactory(
					ConfigurableListableBeanFactory beanFactory)
					throws BeansException {

				beanFactory.addBeanPostProcessor(new BeanPostProcessor() {
					@Override
					public Object postProcessBeforeInitialization(Object bean,
							String beanName) throws BeansException {
						beanList.add(bean);
						return bean;
					}

					@Override
					public Object postProcessAfterInitialization(Object bean,
							String beanName) throws BeansException {
						return bean;
					}
				});

				loadPropertyPlaceholderConfigurerBean(beanFactory);
			}

			/**
			 * 加载PropertyPlaceholderConfigurer bean到spring中，便于spring配置文件中可以通过${XXX}的形式使用系统变量
			 * @param beanFactory
			 */
			private void loadPropertyPlaceholderConfigurerBean(
					ConfigurableListableBeanFactory beanFactory) {
				try {
					beanFactory
							.registerSingleton(
									"propertyPlaceholderConfigurer-common",
									beanFactory
											.createBean(PropertyPlaceholderConfigurer.class));
				} catch (Exception e) {
					e.printStackTrace();
				}

			}
		};
	}

	private static void resolveInjects(Object bean) throws BeansException {
		Class<?> clazz = bean.getClass();
		Field[] fileds = clazz.getDeclaredFields();
		for (Field field : fileds) {
			Load injectAnno = field.getAnnotation(Load.class);
			if (injectAnno == null) {
				continue;
			}
			Class<?> locator = injectAnno.locator();

			ApplicationContext context = getApplicationContext(locator);
			if (context == null) {
				continue;
			}
			Object target = null;
			String name = injectAnno.name();
			if ("".equals(name.trim())) {
				target = context.getBean(field.getType());
			} else {
				target = context.getBean(name, field.getType());
			}
			if (target != null) {
				try {
					boolean acc = field.isAccessible();
					if (!acc) {
						field.setAccessible(true);
					}
					field.set(bean, target);
					if (!acc) {
						field.setAccessible(false);
					}
				} catch (IllegalAccessException e) {
					throw new RuntimeException("resolve inject error", e);
				}
			}
		}
	}
}

