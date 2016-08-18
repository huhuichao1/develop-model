package com.develop.model.util;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.develop.model.enumu.ResourceType;

public class SubjectFieldUtil {

	private static Map<ResourceType, Map<String, String>> fields = new ConcurrentHashMap<ResourceType, Map<String, String>>();

	static {
		Map<String, String> store = new ConcurrentHashMap<String, String>();
		Map<String, String> flashbuy = new ConcurrentHashMap<String, String>();
		Map<String, String> coupon = new ConcurrentHashMap<String, String>();
		Map<String, String> film = new ConcurrentHashMap<String, String>();
		Map<String, String> activity = new ConcurrentHashMap<String, String>();
		Map<String, String> goods = new ConcurrentHashMap<String, String>();
		fields.put(ResourceType.store, store);
		fields.put(ResourceType.flashbuy, flashbuy);
		fields.put(ResourceType.coupon, coupon);
		fields.put(ResourceType.film, film);
		fields.put(ResourceType.activity, activity);
		fields.put(ResourceType.goods, goods);
		// store.put("title", "title");
		// store.put("pinyin", "pinyin");
		// store.put("initials", "initials");
		// store.put("engName", "engName");
		// store.put("engInitials", "engInitials");
		// store.put("relatedName", "relatedName");
		// store.put("label", "label");
		// store.put("isEntity", "isEntity");
		// store.put("description", "description");
		// store.put("merchantId", "merchantId");
		// store.put("brandIds", "brandIds");
		// store.put("typeId", "typeId");

		goods.put("categoryId1", "categorys.id");
		goods.put("categoryId2", "categorys.id");
		goods.put("categoryId3", "categorys.id");
		goods.put("categoryName1", "categorys.name");
		goods.put("categoryName2", "categorys.name");
		goods.put("categoryName3", "categorys.name");
		goods.put("brandIds", "brandId");

		// flashbuy.put("title", "title");
		// flashbuy.put("subtitle", "subtitle");
		flashbuy.put("categoryId1", "categorys.id");
		flashbuy.put("categoryId2", "categorys.id");
		flashbuy.put("categoryId3", "categorys.id");
		flashbuy.put("categoryName1", "categorys.name");
		flashbuy.put("categoryName2", "categorys.name");
		flashbuy.put("categoryName3", "categorys.name");
		flashbuy.put("brandIds", "brandId");
		// flashbuy.put("webDetails", "webDetails");
		// flashbuy.put("appDetails", "appDetails");

		// coupon.put("title", "title");
		// coupon.put("subtitle", "subtitle");
		// coupon.put("webDetails", "webDetails");
		// coupon.put("appDetails", "appDetails");
		coupon.put("businessId", "couponBusinessId");
		coupon.put("typeId", "couponTypeId");
		coupon.put("pubTypeId", "couponPubTypeId");
		coupon.put("categoryId1", "categorys.id");
		coupon.put("categoryId2", "categorys.id");
		coupon.put("categoryId3", "categorys.id");
		coupon.put("categoryName1", "categorys.name");
		coupon.put("categoryName2", "categorys.name");
		coupon.put("categoryName3", "categorys.name");

		// film.put("title", "title");
		// film.put("subtitle", "subtitle");
		// film.put("description", "description");
		film.put("directors", "filmDirectors");
		film.put("actors", "filmActors");
		film.put("types", "filmTypes");

		// activity.put("title", "title");
		// activity.put("subtitle", "subtitle");
		activity.put("details", "activityDetails");
		// activity.put("description", "description");
		activity.put("categoryId1", "categorys.id");
		activity.put("categoryId2", "categorys.id");
		activity.put("categoryId3", "categorys.id");
		activity.put("categoryName1", "categorys.name");
		activity.put("categoryName2", "categorys.name");
		activity.put("categoryName3", "categorys.name");
	}

	public static String getField(ResourceType type, String oldField) {
		if (type == null || oldField == null) {
			return null;
		}
		Map<String, String> map = fields.get(type);
		if (map == null || map.isEmpty()) {
			return null;
		}
		return map.get(oldField);
	}
}
