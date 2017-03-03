package com.develop.model.thread.若面没有则添加;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@SuppressWarnings("unchecked")
public class ListHelper {

	// public static List<Map> list = new ArrayList<Map>();
	// public static List<Map> list = Collections.synchronizedList(new
	// ArrayList<Map>());

	 public static Map map = new HashMap();
//	public static ConcurrentHashMap map = new ConcurrentHashMap();

	// public static synchronized boolean putIfAbsent(Map x) {
	// System.out.println(list);
	// boolean absent = !list.contains(x);
	// if (absent) {
	// try {
	// Thread.sleep(1000);
	// } catch (InterruptedException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	// list.add(x);
	// System.out.println("aaaaaaaaaaaaaaaa");
	// }
	// return absent;
	// }

	// public static boolean putIfAbsent(Map x) {
	// System.out.println(list);
	// synchronized (list) {
	// boolean absent = !list.contains(x);
	// if (absent) {
	// try {
	// Thread.sleep(1000);
	// } catch (InterruptedException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	// list.add(x);
	// System.out.println("aaaaaaaaaaaaaaaa");
	// }
	// return absent;
	// }
	// }

	public static boolean putIfAbsent(Map x) throws InterruptedException {
		System.out.println(map);
		synchronized (map) {
			boolean absent = map.isEmpty();
			if (absent) {
				Thread.sleep(100);
				map = x;
				System.out.println("map 为空，执行幅值操作");
			}
			return absent;
		}

	}
	// public static void putIfAbsent(Map x) throws InterruptedException {
	// System.out.println(map);
	// map.putIfAbsent("1", "1v");
	// if(map.equals(x)){
	// Thread.sleep(100);
	//				
	// System.out.println("应该执行10次");
	// }
	//
	// }

}
