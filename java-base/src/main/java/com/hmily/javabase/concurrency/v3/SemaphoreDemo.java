package com.hmily.javabase.concurrency.v3;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

public class SemaphoreDemo {

    public static void main(String[] args) throws InterruptedException {
        Semaphore semaphore = new Semaphore(4);
        ExecutorService service = Executors.newFixedThreadPool(3);
        for (int i =0 ;i<3; i++){
            service.submit(()->{
                try {
                    echoThread();
                    semaphore.acquire();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }
        Thread.sleep(5*100);

        service.shutdown();
    }

    private static void echoThread(){
        System.out.printf("当前线程[%s]正在执行....\n",Thread.currentThread().getName());
    }
}
