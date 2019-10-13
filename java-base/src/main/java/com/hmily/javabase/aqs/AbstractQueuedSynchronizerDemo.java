package com.hmily.javabase.aqs;

import java.io.IOException;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by zyzhmily on 2019/5/21.
 */
public class AbstractQueuedSynchronizerDemo {

    private static Lock lock=new ReentrantLock();

    public static void main(String[] args) throws InterruptedException {
        ExecutorService executorService= Executors.newFixedThreadPool(2);

        //一个线程获得锁，另外一个线程入队
        executorService.submit(AbstractQueuedSynchronizerDemo::action);
        executorService.submit(AbstractQueuedSynchronizerDemo::action);

        // 非公平锁：
        // thread-1 unlock -> release -> unpark thread-2 -> try acquire
        // thread-4 or thread-5 lock -> try acquire

        // ps： unpark = LockSupport.unpark

        // 公平锁：
        // thread-1 unlock -> release -> unpark thread-2 -> thread-2 try acquire
        // thread-2 lock -> ..
        // thread-3 wait
        // thread-4 wait
        // thread-5 wait

        // 等待200 秒
        executorService.awaitTermination(200, TimeUnit.SECONDS);
        //关闭线程
        executorService.shutdown();
    }

    private static void action(){
        System.out.printf("当前线程 [%s],正在等待您的输入!\n",Thread.currentThread().getName());
        try {
            // 利用 ReentrantLock 作为AQS 实现，理解内部数据结构
            lock.lock();
            System.in.read();
        } catch (IOException e) {
            throw new RuntimeException();
        }finally {
            lock.unlock();
        }
    }


}
