package org.hmily.concurrency.Thread;

/**
 * Created by zyzhmily on 2019/5/23.
 */
public class DaemonThreadQuestion {

    public static void main(String[] args) {
        //main-> 子线程
        Thread t1=new Thread(()->{// new Runnable(){public void run(){....}}
            System.out.printf("线程[%s]正在执行... \n",Thread.currentThread().getName());
        },"子线程1");

        // 变成守候线程
        t1.setDaemon(true);
        t1.start();

        // 守候线程的执行依赖于执行时间 非唯一评判。



    }

}
