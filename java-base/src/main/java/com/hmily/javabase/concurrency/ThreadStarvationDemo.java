package com.hmily.javabase.concurrency;

/**
 * Created by zyzhmily on 2019/4/5.
 *
 * 线程
 */
public class ThreadStarvationDemo {


    public static void main(String[] args) {
        new ThreadStarvationDemo();
    }

    public void finalize(){
        System.out.printf("Thread [%s] executes current objects finalization",Thread.currentThread().getName());
    }


}
