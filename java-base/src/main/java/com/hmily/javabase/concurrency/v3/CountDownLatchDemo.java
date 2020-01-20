package com.hmily.javabase.concurrency.v3;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CountDownLatchDemo {

    public static void main(String[] args) throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(4);
        ExecutorService service = Executors.newFixedThreadPool(4);

        for (int i =0 ;i<3; i++){
            service.submit(()->{
                echoThread();
                countDownLatch.countDown();
            });
        }
        // 当 count 数量等于 0，才会释放
        // 1 != 0
        countDownLatch.await();

        service.shutdown();
    }

    private static void echoThread(){
        System.out.printf("当前线程[%s]正在执行....\n",Thread.currentThread().getName());
    }

}
