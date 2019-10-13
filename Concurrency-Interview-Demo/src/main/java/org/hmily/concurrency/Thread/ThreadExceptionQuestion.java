package org.hmily.concurrency.Thread;

/**
 * Created by zyzhmily on 2019/5/23.
 */
public class ThreadExceptionQuestion {

    public static void main(String[] args) throws InterruptedException {

        // 线程捕获异常
        Thread.setDefaultUncaughtExceptionHandler((thread,throwable)->{
            System.out.printf("线程[%s] 遇到了异常，详细信息：%s\n",thread.getName(),throwable.getMessage());
        });

        Thread t1=new Thread(()->{
           throw new RuntimeException("线程异常了！");
        },"子线程1");

        t1.start();
        t1.join();

        // Java Thread 是一个包装，它由GC 做垃圾回收
        // JVM Thread 可能是一个OS Thread，JVM 管理
        // 当线程执行完毕（正常或异常）回收。
        System.out.println(t1.isAlive());
    }


}
