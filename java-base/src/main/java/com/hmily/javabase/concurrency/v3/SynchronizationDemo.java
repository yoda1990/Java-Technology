package com.hmily.javabase.concurrency.v3;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by zyzhmily on 2019/4/14.
 */
public class SynchronizationDemo {

    static Lock lock=new ReentrantLock();

    static int counter=0;

    public static void main(String[] args) throws InterruptedException {
        // pthread_cond_t condition1;
        Condition condition=lock.newCondition();
        //前提：Lock#lock()
        //await() 和 signal() 或signalAll();

        //前提：synchronized(object)->
        //Object wait() 和 notify（）或notifyAll();

        synchronized (Object.class){

        }


        Thread t1=new Thread(SynchronizationDemo::addCounter);
        Thread t2=new Thread(SynchronizationDemo::addCounter);
        t1.start();
        t2.start();
        t1.join();
        t2.join();


    }

    private static void addCounter(){
        lock.lock();
        System.out.println(getThreadPrefix()+"Before Counter : "+counter);

        counter++;

        System.out.println(getThreadPrefix()+"After Counter : "+counter);
        lock.unlock();
    }

    private static String getThreadPrefix(){
        return "Thread["+Thread.currentThread().getId()+"]:";
    }

}
