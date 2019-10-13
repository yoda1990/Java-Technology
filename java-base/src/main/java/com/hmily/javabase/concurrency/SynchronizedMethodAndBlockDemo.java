package com.hmily.javabase.concurrency;

/**
 * Created by zyzhmily on 2019/4/4.
 */
public class SynchronizedMethodAndBlockDemo {

    public static void main(String[] args) {
        // synchronized 保证互斥
        // 当某个线程 获得锁 （1） 重新又 见到 synchronized（+1）
        // 以此类推
        echo("Hello,World!"); // echo 到底重进入几次？ 3
        // echo -> PrintStream.println -> newLine()
        doEcho("Hello,World!");// doEcho 到底重进入几次？ 4
        // doEche -> echo -> PrintStream.println -> newLine()
    }

    private static void doEcho(String message){
        Object object=new Object();
        synchronized (object){
            // wait 和 notify
            echo(message);
        }
    }

    private synchronized static void echo(String message){
         System.out.println(message);
    }

}
