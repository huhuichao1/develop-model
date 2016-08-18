package com.develop.model.util;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.develop.model.util.StringUtil;

public class BeanUtil {

	@SuppressWarnings("rawtypes")
	public static Map<String, String> getField4Map(Class clazz) {
		if (clazz == null) {
			return null;
		}
		BeanInfo info = null;
		try {
			info = Introspector.getBeanInfo(clazz);
		} catch (IntrospectionException e) {
			e.printStackTrace();
		}
		if (info == null) {
			return null;
		}
		PropertyDescriptor[] pds = info.getPropertyDescriptors();
		if (pds == null || pds.length == 0) {
			return null;
		}
		Map<String, String> fields = null;
		for (PropertyDescriptor pd : pds) {
			if (pd.getReadMethod() == null || "class".equals(pd.getName())) {
				continue;
			}
			if (fields == null) {
				fields = new HashMap<String, String>();
			}
			fields.put(pd.getName(), "");
		}
		return fields;
	}

	@SuppressWarnings("rawtypes")
	public static List<String> getField(Class clazz) {
		if (clazz == null) {
			return null;
		}
		BeanInfo info = null;
		try {
			info = Introspector.getBeanInfo(clazz);
		} catch (IntrospectionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (info == null) {
			return null;
		}
		PropertyDescriptor[] pds = info.getPropertyDescriptors();
		if (pds == null || pds.length == 0) {
			return null;
		}
		List<String> fields = null;
		for (PropertyDescriptor pd : pds) {
			if (pd.getReadMethod() == null || "class".equals(pd.getName())) {
				continue;
			}
			if (fields == null) {
				fields = new ArrayList<String>();
			}
			fields.add(pd.getName());
		}
		return fields;
	}

	/**
	 * 获取 bean指定字段的值，如果指定属性为空则不过滤
	 * 
	 * @param bean
	 * @param properties
	 *            指定bean属性
	 * @return
	 */
	public static Map<String, Object> getValues(Object bean, Collection<String> properties) {
		if (bean == null) {
			return null;
		}
		BeanInfo info = null;
		try {
			info = Introspector.getBeanInfo(bean.getClass());
		} catch (IntrospectionException e) {
			e.printStackTrace();
		}
		if (info == null) {
			return null;
		}
		PropertyDescriptor[] pds = info.getPropertyDescriptors();
		if (pds == null || pds.length == 0) {
			return null;
		}
		Map<String, Object> values = null;
		for (PropertyDescriptor pd : pds) {
			Method method = pd.getReadMethod();
			String name = pd.getName();
			if (method == null || "class".equals(name) || StringUtil.isEmpty(name)) {
				continue;
			}
			if (properties != null && !properties.isEmpty()) {// 指定了字段
				if (!properties.contains(name)) {// 过滤未指定字段
					continue;
				}
			}
			Object value = null;
			try {
				value = method.invoke(bean);
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
			if (values == null) {
				values = new HashMap<String, Object>();
			}
			values.put(name, value);
		}
		return values;
	}

	/**
	 * 
	 * @param bean
	 * @return
	 */
	public static Map<String, Object> getValues(Object bean) {
		return getValues(bean, null);
	}
}
