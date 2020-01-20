package com.hmily.javabase.concurrency.v3;

public class ThreadInterruptDemo {


    public static void main(String[] args) throws InterruptedException {
        // Thread 实现 Runnable
        // 如果没有Runnable 对象实现，空执行。
        Thread thread = new Thread(ThreadInterruptDemo::sayHelloWorld);
        // thread.interrupt() 方法在 start 调用之前是没有效果
        thread.start();
        thread.interrupt();// main线程 interrupt 子线程
        // interrupt() 并不中止线程，但是可以传递 interrupt状态
        thread.join();

    }


    public static void sayHelloWorld(){
//        if (Thread.currentThread().isInterrupted()){
//            System.out.printf("线程 【Id : %s】 被终止 ...",Thread.currentThread().getId());
//            return;
//        }
        Object monitor = ThreadInterruptDemo.class;
        synchronized (monitor){
            try {
                monitor.wait(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
                System.out.printf("线程 [Id : %s] 当前interrupted 状态 - %s\n",
                        Thread.currentThread().getId(),Thread.currentThread().isInterrupted());
            }
        }

        System.out.printf("线程 【Id : %s】 Hello World ...",Thread.currentThread().getId());
    }


}
