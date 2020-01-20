package com.hmily.javabase.concurrency.v3;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CyclicBarrierDemo {

    public static void main(String[] args) throws BrokenBarrierException, InterruptedException {
        CyclicBarrier cb = new CyclicBarrier(4);
        ExecutorService service = Executors.newFixedThreadPool(3);
        for (int i =0 ;i<3; i++){
            service.submit(()->{
                try {
                    echoThread();
                    cb.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
            });
        }

        cb.await();
        service.shutdown();
    }

    private static void echoThread(){
        System.out.printf("当前线程[%s]正在执行....\n",Thread.currentThread().getName());
    }

}
