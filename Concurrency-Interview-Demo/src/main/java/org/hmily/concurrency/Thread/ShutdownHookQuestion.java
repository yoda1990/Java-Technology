package org.hmily.concurrency.Thread;

/**
 * Created by zyzhmily on 2019/5/23.
 */
public class ShutdownHookQuestion {

    public static void main(String[] args) {
        Runtime runtime=Runtime.getRuntime();

        runtime.addShutdownHook(new Thread(()->{
            action();
        },"Shutdown Hook Question"));
    }


    private static void action(){
        System.out.printf("线程[%s]正在执行... \n",Thread.currentThread().getName());
    }
}
