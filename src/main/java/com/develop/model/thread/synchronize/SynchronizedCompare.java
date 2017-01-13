package com.develop.model.thread.synchronize;



/**
 * 只有等方法执行完毕才能释放锁
 * 一：synchronized 作用在普通方法上  ；（=synchronized(this)）
	锁住当前对象，方法的synchronized 关键字, 在阅读的时候可以自动替换为synchronized(this){}就很好理解了.
	如果作用在同一对象上，那么该对象中的其他synchronized方法 将不能使用，必须等待该方法执行完毕，容易出现死锁。
	如果作用在不同对象上，那么不起作用
二：synchronized 作用在静态方法上：（=synchronized(Sync.class)）
	锁住当前类。实现了全局锁的效果。
	static synchronized方法，static方法可以直接类名加方法名调用，方法中无法使用this，所以它锁的不是this，而是类的Class对象，所以，static synchronized方法也相当于全局锁，相当于锁住了代码段。
 * @author huhuichao
 *
 */
public class SynchronizedCompare {

    public synchronized void m1() throws InterruptedException{
        System. out.println("m1 call" );
        Thread. sleep(2000);
        System. out.println("m1 call done" );
   }

   public void m2() throws InterruptedException{
         synchronized (this ) {
            System. out.println("m2 call" );
            Thread. sleep(2000);
            System. out.println("m2 call done" );
        }
   }

   public static void main(String[] args) {
         final SynchronizedCompare thisObj  = new SynchronizedCompare();

        Thread t1 = new Thread(){
             @Override
             public void run() {
                 try {
                     thisObj.m1();
                } catch (InterruptedException e) {
                     e.printStackTrace();
                }
            }
        };

        Thread t2 = new Thread(){
             @Override
             public void run() {
                 try {
                     thisObj.m2();
                } catch (InterruptedException e) {
                     e.printStackTrace();
                }
            }
        };

        t1.start();
        t2.start();

   }
}
