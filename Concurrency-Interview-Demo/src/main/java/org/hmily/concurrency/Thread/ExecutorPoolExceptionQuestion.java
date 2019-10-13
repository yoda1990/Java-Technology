package org.hmily.concurrency.Thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by zyzhmily on 2019/5/23.
 */
public class ExecutorPoolExceptionQuestion {

    public static void main(String[] args) {

        ExecutorService executorService= Executors.newFixedThreadPool(2);

        executorService.submit(()->{
            throw new RuntimeException("线程异常了！");
        });

        executorService.shutdown();


    }

    private static void action(){
        System.out.printf("线程[%s]正在执行... \n",Thread.currentThread().getName());
    }

}
