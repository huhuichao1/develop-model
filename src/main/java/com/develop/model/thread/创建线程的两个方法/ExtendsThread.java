package com.develop.model.thread.创建线程的两个方法;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 假设一个影院有三个售票口，分别用于向儿童、成人和老人售票。影院为每个窗口放有10张电影票，分别是儿童票、成人票和老人票。
 * 三个窗口需要同时卖票，而现在只有一个售票员，这个售票员就相当于一个CPU，三个窗口就相当于三个线程。通过程序来看一看是如何创建这三个线程的。
 * @author 胡慧超
 *
 */
public class ExtendsThread {
	
	/**
	 * 继承Thread类创建的多个线程，虽然执行的是相同的代码，但彼此相互独立，且各自拥有自己的资源，互不干扰。
	 * @param args
	 */
	public static void main(String [] args){
	        MutliThread m1=new MutliThread("Window 1");
	        MutliThread m2=new MutliThread("Window 2");
	        MutliThread m3=new MutliThread("Window 3");
	        m1.start();
	        m2.start();
	        m3.start();
	    }
	}
	class MutliThread extends Thread{
		private static final AtomicInteger TOTAL_SUCCESS = new AtomicInteger(10);//一共拥有10张票
//	    private int ticket=10;//一共拥有10张票
	    MutliThread(String name){
	        super(name);//调用父类带参数的构造方法
	    }
	    public void run(){
	        while(TOTAL_SUCCESS.get()>0){
	        	int t=TOTAL_SUCCESS.decrementAndGet();//减后再获取一
	            System.out.println(t+1+" is saled by "+Thread.currentThread().getName());
	        }
	    }
	
}
