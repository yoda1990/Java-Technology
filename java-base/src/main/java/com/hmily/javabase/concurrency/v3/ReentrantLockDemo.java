package com.hmily.javabase.concurrency.v3;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockDemo {

    public static void main(String[] args) {

        Lock lock = new ReentrantLock();
        try {
            lock.tryLock(3, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    private static void conditionObject(){
        Lock lock =new ReentrantLock();
        Condition condition = lock.newCondition();
        try {
            condition.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void tryLockInTimeout(){
        Lock lock = new ReentrantLock();

        try {
            if (lock.tryLock(3,TimeUnit.SECONDS)){
                // Lock API 语义 补充了 synchronized 原语的不足
            }
        } catch (InterruptedException e) {
            // 重置中止状态 （防止被中途清楚状态）
            Thread.currentThread().interrupt();
        }
    }

    private static void synchronizedStatement(){
        // 假设线程出现死锁或者饥饿
        synchronized (ReentrantLockDemo.class){
            if (3>2){
                // Object 线程通讯方法
                try {
                    ReentrantLockDemo.class.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
