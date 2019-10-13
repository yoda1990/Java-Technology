package org.hmily.concurrency.Thread;

/**
 * Created by zyzhmily on 2019/5/23.
 */
public class CompleteAllThreadQuestion {

    public static void main(String[] args) throws InterruptedException {
        //main-> 子线程
        Thread t1=new Thread(CompleteAllThreadQuestion::action,"t1");
        Thread t2=new Thread(CompleteAllThreadQuestion::action,"t2");
        Thread t3=new Thread(CompleteAllThreadQuestion::action,"t3");
        // 不确定 t1 t2 t3 是否调用 start()

        // 创建了 N 个 Thread

        Thread mainThread =Thread.currentThread();
        // 获取main 线程组
        ThreadGroup threadGroup =mainThread.getThreadGroup();
        //活跃的线程数
        int count=threadGroup.activeCount();
        Thread[] threads=new Thread[count];
        // 把所有线程复制 threads 数组
        threadGroup.enumerate(threads,true);

        for (Thread thread:threads){
            if (Thread.State.NEW.equals(thread.getState())){
                thread.start();
                thread.join();
            }
        }
    }



    private static void action(){
        System.out.printf("线程[%s]正在执行... \n",Thread.currentThread().getName());
    }

}
