package com.hmily.javabase.concurrency.v3;

public class ThreadDemo {


    public static void main(String[] args) throws InterruptedException {
        Thread thread =new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.printf("线程Id ：[%s],Hello,World\n",Thread.currentThread().getId());
            }
        });
        thread.start();
        thread.join();

        Thread thread1 = new Thread(ThreadDemo::sayHelloWorld);
        thread1.start();
    }


    // 方法引用
    public static void sayHelloWorld(){
        System.out.printf("方法引用----线程Id ：[%s],Hello,World\n",Thread.currentThread().getId());
    }

}
