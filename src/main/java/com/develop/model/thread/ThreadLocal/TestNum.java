
package com.develop.model.thread.ThreadLocal;


/**
 * 每个线程都有自己独立的序列号
 * @author a1
 *
 */
public class TestNum  {

	private static ThreadLocal<Integer> seqNum=new ThreadLocal<Integer>(){
		@Override
		protected Integer initialValue() {
			// TODO Auto-generated method stub
			return 0;
		}
	};
	
	public int getNextNum(){
		seqNum.set(seqNum.get()+1);
		return seqNum.get();
	}
	
	public static void main(String[] args) {
		TestNum t=new TestNum();
		TestClient c1=new TestClient(t,"c1");
		TestClient c2=new TestClient(t,"c2");
		TestClient c3=new TestClient(t,"c3");
		c1.start();
		c2.start();
		c3.start();
	}
	
	
	
}
