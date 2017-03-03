package com.develop.model.thread.ThreadLocal;

public class TestClient extends Thread{
	private TestNum t;
	public TestClient(TestNum t,String name){
		super(name);
		this.t=t;
	}
	@Override
	public void run() {
		for(int i=0;i<3;i++){
			System.out.println("Thread["+Thread.currentThread().getName()+"] 的序列号 --->"+t.getNextNum());
		}
	}
}
