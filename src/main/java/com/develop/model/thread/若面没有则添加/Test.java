package com.develop.model.thread.若面没有则添加;

import java.util.HashMap;
import java.util.Map;


public class Test {
	public static void main(String[] args) {
		for (int i = 1; i <= 10; i++) {
			Thread t = new Thread("thread-" + i){
				@Override
				public void run() {
					Map map=new HashMap();
					map.put("1", "1v");
//					System.out.println(Thread.currentThread().getName()+"启动...");
					try {
						ListHelper.putIfAbsent(map);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			};
			t.start();
		}
	}
}
