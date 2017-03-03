package com.develop.model.thread.ProducerConsumer;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class ProducerConsumer {
	public static void main(String[] args) {
		BlockingQueue queue=new LinkedBlockingQueue();
		Producer p=new Producer(queue);
		Consumer c=new Consumer(queue);
		Consumer c1=new Consumer(queue);
		new Thread(p).start();
		new Thread(c).start();
		new Thread(c1).start();
	}
    

}

@SuppressWarnings({"unchecked","rawtypes"})
class Producer implements Runnable{
	private final BlockingQueue queue;
	public Producer(BlockingQueue queue){
		this.queue=queue;
	}
	public void run() {
		while(true){
			try {
				queue.put(new Object());
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
	}
	
}
@SuppressWarnings({"rawtypes"})
class Consumer implements Runnable{

	private final BlockingQueue queue;
	public Consumer(BlockingQueue queue){
		this.queue=queue;
	}
	public void run() {
		while(true){
			try {
				queue.take();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
}
