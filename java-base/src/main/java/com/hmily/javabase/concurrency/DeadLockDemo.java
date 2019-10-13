package com.hmily.javabase.concurrency;

/**
 * Created by zyzhmily on 2019/4/5.
 * 死锁
 *
 */
public class DeadLockDemo {

    private static final Object m1=new Object();

    private static final Object m2=new Object();

    public static void main(String[] args) {
        new Thread(()->{
             synchronized (m1){
                 System.out.printf("Thread [ ID  %d] holds m1 \n",Thread.currentThread().getId());
                 synchronized (m2){
                     System.out.printf("Thread [ ID  %d] holds m2 \n",Thread.currentThread().getId());
                 }
             }
        }).start();

        new Thread(()->{
            synchronized (m2){
                System.out.printf("Thread [ ID  %d] holds m2 \n",Thread.currentThread().getId());
                synchronized (m1){
                    System.out.printf("Thread [ ID  %d] holds m1 \n",Thread.currentThread().getId());
                }
            }
        }).start();
    }

}
