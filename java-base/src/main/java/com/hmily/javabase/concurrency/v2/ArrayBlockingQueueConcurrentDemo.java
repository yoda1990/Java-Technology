package com.hmily.javabase.concurrency.v2;
;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by zyzhmily on 2019/4/14.
 */
public class ArrayBlockingQueueConcurrentDemo {

    public static void main(String[] args) throws InterruptedException {

        // 最大允许添加 2个元素
        BlockingQueue blockingQueue=new ArrayBlockingQueue(2,true);
        //申请 2 个大小的线程池
        ExecutorService executorService= Executors.newFixedThreadPool(2);

        for (AtomicInteger i=new AtomicInteger(1);i.get()<100;i.incrementAndGet()){

            //写线程
            executorService.submit(()->{
                try {
                    blockingQueue.put(i.get());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });

            //读线程
            executorService.submit(()->{
                try {
                    System.out.println(blockingQueue.take());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }
        executorService.awaitTermination(10, TimeUnit.SECONDS);
        // 关闭线程池
        executorService.shutdown();

    }

}
