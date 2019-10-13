package org.hmily.concurrency.Thread;

/**
 * Created by zyzhmily on 2019/4/15.
 */
public class ThreadStateQuestion {

    /**
     *
     * Java 如何销毁一个线程？
     * @param args
     */
    public static void main(String[] args) {

        //main-> 子线程
        Thread t1=new Thread(()->{// new Runnable(){public void run(){....}}
            System.out.printf("线程[%s]正在执行... \n",Thread.currentThread().getName());
        },"子线程1");
        t1.start();
        System.out.printf("线程[%s] 是否还活着：%s \n",t1.getName(),t1.isAlive());
        // Java 中，执行线程 Java 是没有办法销毁它的，
        // 但是当 Thread.isAlive() 返回 false 时，实际底层的Thread 已经被销毁了。
    }

}
