package com.develop.model.util;

import java.util.List;

import com.alibaba.fastjson.JSONObject;

public class JSONUtil {

	/**
	 * bean->JSONObject
	 * 
	 * @param bean
	 * @return
	 */
	public static JSONObject bean2JSONObject(Object bean) {
		if (bean == null) {
			return null;
		}
		try {
			return (JSONObject) JSONObject.toJSON(bean);
		} catch (Exception e) {
			LogUtil.ROOT.error("", e);
		}
		return null;
	}

	/**
	 * string->JSONObject
	 * 
	 * @param json
	 * @return
	 */
	public static JSONObject string2JSONObject(String json) {
		if (StringUtil.isEmpty(json)) {
			return null;
		}
		try {
			return JSONObject.parseObject(json);
		} catch (Exception e) {
			LogUtil.ROOT.error("", e);
		}
		return null;
	}

	/**
	 * bean->string JSONObject->string
	 * 
	 * @param bean
	 * @return
	 */
	public static String bean2String(Object bean) {
		if (bean == null) {
			return null;
		}
		try {
			return JSONObject.toJSONString(bean);
		} catch (Exception e) {
			LogUtil.ROOT.error("", e);
		}
		return null;
	}

	/**
	 * JSONObject->bean
	 * 
	 * @param jSONObject
	 * @param clazz
	 * @return
	 */
	public static <T> T jSONObject2Bean(JSONObject jSONObject, Class<T> clazz) {
		if (jSONObject == null || clazz == null) {
			return null;
		}
		try {
			return JSONObject.toJavaObject(jSONObject, clazz);
		} catch (Exception e) {
			LogUtil.ROOT.error("", e);
		}
		return null;
	}

	/**
	 * string->bean
	 * 
	 * @param json
	 * @param clazz
	 * @return
	 */
	public static <T> T string2Bean(String json, Class<T> clazz) {
		if (StringUtil.isEmpty(json) || clazz == null) {
			return null;
		}
		try {
			return JSONObject.parseObject(json, clazz);
		} catch (Exception e) {
			LogUtil.ROOT.error("", e);
		}
		return null;
	}

	/**
	 * string->list
	 * 
	 * @param json
	 * @param clazz
	 * @return
	 */
	public static <T> List<T> string2List(String json, Class<T> clazz) {
		if (StringUtil.isEmpty(json) || clazz == null) {
			return null;
		}
		try {
			return JSONObject.parseArray(json, clazz);
		} catch (Exception e) {
			LogUtil.ROOT.error("", e);
		}
		return null;
	}

}
