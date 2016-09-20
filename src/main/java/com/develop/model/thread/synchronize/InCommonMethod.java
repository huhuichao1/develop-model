package com.develop.model.thread.synchronize;

public class InCommonMethod {

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
         final InCommonMethod thisObj  = new InCommonMethod();

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
