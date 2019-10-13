package com.hmily.javabase.thread;

/**
 * Created by zyzhmily on 2019/4/4.
 */
public class ThreadWaitAndNotifyDemo {

    public static void main(String[] args) throws InterruptedException {
        //Thread 实现了 Runnable
        // 如果没有传递Runnnable 对象实现，空执行
        Thread thread1=new Thread(ThreadWaitAndNotifyDemo::sayHelloWorld);//方法引用
        thread1.setName("T1");
        thread1.start();//启动线程

        Thread thread2=new Thread(ThreadWaitAndNotifyDemo::sayHelloWorld);//方法引用
        thread2.setName("T2");
        thread2.start();//启动线程
        // Object.wait（）与 Thread.join() 看起来效果类似
        // 实际上 Thread.join() 方法就是调用了 Thread 对象 wait(int) 方法

        // 调用 wait() 方法对象，再调用 notify() 方法必须是同一对象
        // 因此 以下调用是错误示范
        //thread1.notify();
        //thread2.notify();
        //正确示范
        Class<ThreadInterruptDemo> threadInterruptDemoClass = ThreadInterruptDemo.class;
        Object monitor =threadInterruptDemoClass;
        synchronized (monitor){
            // 为什么 monitor 不能保证完全释放？
            // monitor.notify();
            monitor.notifyAll();
        }

        // wait() 语义：在同步（互斥）场景下


        // Lock 场景： T1、T2 互斥访问资源 R
        // T1 获取：L(T1) -> T2 获取 L（T2）
        // T1.wait() T2.wait() 都要被阻塞（停顿）
        // 第三方可以（条件）控制 T1 或者 T2 释放

        // Java 5 + ： Condition await() 等待， signal() 通知

    }

    public static void sayHelloWorld(){

        Thread thread=Thread.currentThread();

        Class<ThreadInterruptDemo> threadInterruptDemoClass = ThreadInterruptDemo.class;
        Object monitor =threadInterruptDemoClass;
        synchronized (monitor){
            try {
                System.out.printf("线程[%s] 进入等待.....\n", thread.getName());
                monitor.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.printf("线程[%s] 恢复执行.....\n",thread.getName());
        System.out.printf("线程[Id : %s] : Hello World!\n",Thread.currentThread().getId());
    }

}
