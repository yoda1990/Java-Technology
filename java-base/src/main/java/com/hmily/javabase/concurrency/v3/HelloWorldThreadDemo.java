package com.hmily.javabase.concurrency.v3;

/**
 * Created by zyzhmily on 2019/4/14.
 */
public class HelloWorldThreadDemo {

    public static void main(String[] args) throws InterruptedException {
        // 创建 Java 线程
        // Java 线程对象和JVM OS 线程 并不是同一对象
        Thread t1=new Thread(HelloWorldThreadDemo::helloWorld);
        //显示的启动
        t1.start();// pthread_create()
        //等待线程执行结束
        t1.join();// pthread_join()
        // 当线程 isAlive() 返回false 时，JVM 线程已经消亡了（delete this）
        System.out.printf("线程状态 ：%s，是否存活 ：%s",t1.getState(),t1.isAlive());

    }

    static void helloWorld(){
        System.out.printf("Thread [id %s]-Hello World!!\n",Thread.currentThread().getId());
    }


}
