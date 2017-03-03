package com.develop.model.thread.runner;

import java.util.Queue;
import java.util.Random;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CountDownLatch;

@SuppressWarnings("unchecked")
public class RunnableTest {
	public static void main(String[] args) throws Exception{
        final CountDownLatch prepare = new CountDownLatch(3);
        final CountDownLatch start = new CountDownLatch(1);
        final CountDownLatch end = new CountDownLatch(3);
        final Queue<String> queue = new ConcurrentLinkedQueue();

        Thread thread1 = new Thread(new Runnable(){
            public void run() {
                try{
                    Thread.sleep(new Random(System.currentTimeMillis()).nextInt(10));
                }catch(Exception e){}
                System.out.println("1号运动员准备完毕");
                prepare.countDown();
                try {
                    start.await();
                }catch (Exception e){}
                System.out.println("1号运动员开始跑");
                try{
                    Thread.sleep(new Random(System.currentTimeMillis()).nextInt(1000));
                }catch(Exception e){}
                System.out.println("1号运动员到达目的地");
                end.countDown();
                queue.add("1号");
            }
        });
        Thread thread2 = new Thread(new Runnable() {
            public void run() {
                try{
                    Thread.sleep(new Random(System.currentTimeMillis()).nextInt(10));
                }catch(Exception e){}
                System.out.println("2号运动员准备完毕");
                prepare.countDown();
                try {
                    start.await();
                }catch (Exception e){}
                System.out.println("2号运动员开始跑");
                try{
                    Thread.sleep(new Random(System.currentTimeMillis()).nextInt(1000));
                }catch(Exception e){}
                System.out.println("2号运动员到达目的地");
                end.countDown();
                queue.add("2号");
            }
        });
        Thread thread3 = new Thread(new Runnable() {
            public void run() {
                try{
                    Thread.sleep(new Random(System.currentTimeMillis()).nextInt(10));
                }catch(Exception e){}
                System.out.println("3号运动员准备完毕");
                prepare.countDown();
                try {
                    start.await();
                }catch (Exception e){}
                System.out.println("3号运动员开始跑");
                try{
                    Thread.sleep(new Random(System.currentTimeMillis()).nextInt(1000));
                }catch(Exception e){}
                System.out.println("3号运动员到达目的地");
                end.countDown();
                queue.add("3号");
            }
        });

        thread1.start();
        thread2.start();
        thread3.start();
        System.out.println("教练在等待运动员准备...");
        prepare.await();
        System.out.println("所有运动员准备完毕，教练开始喊：预备 --- 跑！");
        start.countDown();

        System.out.println("教练在等待所有的运动员跑完...");
        end.await();

        System.out.println("所有运动员都跑完了，开始颁奖啦，本次比赛名次如下：");
        System.out.println("第一名是："+queue.poll());
        System.out.println("第二名是："+queue.poll());
        System.out.println("第三名是："+queue.poll());
    }
}