package com.hmily.javabase.thread.task;

/**
 * Created by zyzhmily on 2019/5/22.
 */
public class ThreadRunnableDemo {

    public static void main(String[] args) {
        Runnable r=new MyRunnable();
        Thread thread=new Thread(r);
        //thread.start();
        //thread.stop();

        Thread thread1=new MyThread();
        thread1.start();
    }


    public static class MyThread extends Thread{
        public void run(){
            System.out.print("线程执行了。。。。\n");
        }

    }


}
