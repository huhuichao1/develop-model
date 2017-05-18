package com.develop.model.thread.Callable;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import org.elasticsearch.common.base.Stopwatch;


/**
 * get()方法会使所在线程阻塞，直到返回结果。
 * @author huhuichao
 *
 */
public class CallableTest {
	
    public static final Stopwatch stopwatch = Stopwatch.createStarted();
    public static final ExecutorService executorPool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());


	public static void main(String[] args) throws InterruptedException, ExecutionException {
		long start=System.currentTimeMillis();
		List<Future<String>> tasks=new ArrayList<>();
		tasks.add(executorPool.submit(new MyCallable("1", 1000l)));
		tasks.add(executorPool.submit(new MyCallable("2", 1200l)));
		tasks.add(executorPool.submit(new MyCallable("3", 3060l)));
		tasks.add(executorPool.submit(new MyCallable("4", 2060l)));
		List<String> results= new ArrayList<String>();
//		while(!tasks.isEmpty()){
//			for(int i=0;i<tasks.size();i++){
//				if (tasks.get(i).isDone()) {
//                    results.add(tasks.get(i).get());
//                    tasks.remove(i);
//                    i--;
//                }
//			}
//		}
		for(int i=0;i<tasks.size();i++){
               results.add(tasks.get(i).get());
		}
		long now=System.currentTimeMillis();

//		System.out.println("main[" + stopwatch.elapsed(TimeUnit.MILLISECONDS) + "] list=" + results);
		System.out.println("main[" + (now-start) + "] list=" + tasks);
        executorPool.shutdown();

	}
	
	private static final class MyCallable implements Callable<String>{

		
		private final String s;
		private final long time ;
		MyCallable(String s,long time){
			this.s=s;
			this.time=time;
		}
		@Override
		public String call() throws Exception {
			Thread.sleep(time);
            System.out.println(s + ":" + stopwatch.elapsed(TimeUnit.MILLISECONDS));
			return s;
		}
		
	}

}
