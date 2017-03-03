package com.develop.model.thread.runner;

import java.util.concurrent.CountDownLatch;

/**
 * 这是一套计数器的代码
 * countDown方法，当前线程调用此方法，则计数减一
 *awaint方法，调用此方法会一直阻塞当前线程，直到计时器的值为0
 * @author 胡慧超
 *
 */
public class ThreadTest {
	  public static void main(String[] args) throws Exception{
	        final CountDownLatch start = new CountDownLatch(1);

	        Thread thread1 = new Thread(new Runnable() {
	            public void run() {
	                System.out.println("1号运动员准备完毕");
	                try {
	                    start.await();
	                }catch (Exception e){}
	                System.out.println("1号运动员开始跑");
	            }
	        });
	        Thread thread2 = new Thread(new Runnable() {
	            public void run() {
	                System.out.println("2号运动员准备完毕");
	                try {
	                    start.await();
	                }catch (Exception e){}
	                System.out.println("2号运动员开始跑");
	            }
	        });
	        Thread thread3 = new Thread(new Runnable() {
	            public void run() {
	                System.out.println("3号运动员准备完毕");
	                try {
	                    start.await();
	                }catch (Exception e){}
	                System.out.println("3号运动员开始跑");
	            }
	        });

	        thread1.start();
	        thread2.start();
	        thread3.start();
	        System.out.println("预备。。。跑！");
	        start.countDown();
	    }
}