package com.develop.model.thread.temp;


public class Lock {

	private static Lock me = new Lock();
	public static Lock getInstance(){
		return me;
	}
//	private Lock(){}
	//数量
	public int num = 10;

}
