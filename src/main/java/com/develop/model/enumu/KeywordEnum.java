package com.develop.model.enumu;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public enum KeywordEnum {
	CITY("city","城市"),PLAZA("plaza", "广场"),BRAND("brand", "品牌"),STORE("store", "门店"),FILM("film", "电影"),
	ACTIVITY("activity", "活动"),COMMODITY("commodity", "商品"),FLASHBUY("flashbuy", "闪购"),COUPON("coupon", "优惠券"); 
	
	public static final KeywordEnum[] search = new KeywordEnum[] { CITY,FILM,PLAZA,STORE,COUPON,ACTIVITY,COMMODITY,FLASHBUY,BRAND };
	public static final KeywordEnum[] product = new KeywordEnum[] { STORE,FILM,ACTIVITY,COMMODITY,FLASHBUY,COUPON };
	public static final String[] terms=new String[]{STORE.getDesc(),FILM.getDesc(),ACTIVITY.getDesc(),COMMODITY.getDesc(),FLASHBUY.getDesc(),COUPON.getDesc()};
	public static final Map<String, String> map = new HashMap<String, String>();  
	
	static{
		for(KeywordEnum e:EnumSet.allOf(KeywordEnum.class)){
			map.put(e.desc, e.value);
		}
	}
	
	private final String value;
    private final String desc;

    private KeywordEnum(String value, String desc) {
        this.value = value;
        this.desc = desc;
    }

	public String getValue() {
		return value;
	}

	public String getDesc() {
		return desc;
	}
	
	public static void main(String[] args) {
		System.out.println(map);
	}
    



}
