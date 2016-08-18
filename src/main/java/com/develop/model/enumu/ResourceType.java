package com.develop.model.enumu;

import java.util.ArrayList;
import java.util.List;

import com.develop.model.util.StringUtil;

public enum ResourceType {

	tipword, city, plaza, store, brand, coupon, activity, film, flashbuy, goods;

	public static final ResourceType[] suggest = new ResourceType[] { tipword, plaza, store, brand, coupon, activity, film, flashbuy, goods };
	public static final ResourceType[] keyword = new ResourceType[] { plaza, store, brand, coupon, activity, film, flashbuy, goods };

	// Add by Gray 20160405 添加ResourceType为数组的类型
	public static ResourceType[] parses(String value) {
		if (StringUtil.isEmpty(value)) {
			return null;
		} else {
			String[] valueStrArray = value.split(",");
			List<ResourceType> resourceTypes = new ArrayList<ResourceType>();
			for (String valueStr : valueStrArray) {
				if (valueStr.equals("brands")) {
					valueStr = "brand";
				}
				try {
					resourceTypes.add(valueOf(valueStr));
				} catch (Exception e) {
				}
			}
			if (resourceTypes.size() > 0) {
				return resourceTypes.toArray(new ResourceType[0]);
			} else {
				return null;
			}

		}
	}

	public static String getIndexType(ResourceType type) {
		if (type == null) {
			return null;
		}
		if (type == ResourceType.brand) {
			return "brands";
		}
		return type.toString();
	}

	public static ResourceType parse(String value) {
		if (StringUtil.isEmpty(value)) {
			return null;
		}
		if (value.equals("brands")) {
			value = "brand";
		}
		try {
			return valueOf(value);
		} catch (Exception e) {
		}
		return null;
	}

	public static void main(String[] args) {
		ResourceType[] types = ResourceType.values();
		for (ResourceType type : types) {
			System.out.println(type);
		}
	}

}
