package com.develop.model.thread.创建线程的两个方法;

public class ExtendsRunable {

	/**
	 * 虽然现在程序中有三个线程，但是一共卖了10张票，也就是说使用Runnable实现多线程可以达到资源共享目的。
	 * @param args
	 */
	public static void main(String [] args){
		
        MutliThread1 m1=new MutliThread1("Window 1");
        new Thread(m1).start();//同一个mt，但是在Thread中就不可以，如果用同一  
        new Thread(m1).start();//个实例化对象mt，就会出现异常  
        new Thread(m1).start();  
	    }
	}
	class MutliThread1 implements Runnable{
	    private int ticket=10;//每个线程都拥有10张票
	    private String name;
	    MutliThread1(String name){
	        this.name=name;//调用父类带参数的构造方法
	    }
	    public void run(){
	        while(ticket>0){
	            System.out.println(ticket--+" is saled by "+name);
	        }
	    }
}
