package com.develop.model.thread.temp;

public class CrawlerThread {

	public static void main(String[] args) {
		for (int i = 1; i <= 3; i++) {
			Thread t = new MutliThread("thread-" + i);
			t.start();
		}
	}
}
	class MutliThread extends Thread {
//		Lock lock = Lock.getInstance();      
		private  int ticket=10;//一共10张票
		MutliThread(String name) {
			super(name);// 调用父类带参数的构造方法
		}
		public  void run() {
			while (ticket > 0) {	
					System.out.println(ticket--+" is saled by "+Thread.currentThread().getName());
			}
		}
//		ThreadLocal t = new ThreadLocal();
	
	}
