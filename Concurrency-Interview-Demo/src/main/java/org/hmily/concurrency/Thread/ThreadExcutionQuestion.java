package org.hmily.concurrency.Thread;

/**
 * Created by zyzhmily on 2019/5/23.
 */
public class ThreadExcutionQuestion {




    public static void main(String[] args) throws InterruptedException {
        waitThread();
    }

    public static void waitThread(){
        //线程 顺序执行实现。
        Thread t1=new Thread(ThreadExcutionQuestion::action,"t1");
        Thread t2=new Thread(ThreadExcutionQuestion::action,"t2");
        Thread t3=new Thread(ThreadExcutionQuestion::action,"t3");

        threadStartAndWait(t1);
        threadStartAndWait(t2);
        threadStartAndWait(t3);
    }


    private static void threadStartAndWait(Thread thread){
        if (Thread.State.NEW.equals(thread.getState())){
            thread.start();
        }
        while (thread.isAlive()){
            synchronized (thread){
                try {
                    thread.wait();
                } catch (InterruptedException e) {
                    throw new RuntimeException();
                }
            }
        }
    }



    private static void sleepThread() throws InterruptedException {
        //线程 顺序执行实现。
        Thread t1=new Thread(ThreadExcutionQuestion::action,"t1");
        Thread t2=new Thread(ThreadExcutionQuestion::action,"t2");
        Thread t3=new Thread(ThreadExcutionQuestion::action,"t3");

        t1.start();
        while (t1.isAlive()){
              Thread.sleep(0);
        }

        t2.start();
        while (t2.isAlive()){
            Thread.sleep(0);
        }

        t3.start();
        while (t3.isAlive()){
            Thread.sleep(0);
        }
    }



    private static void loopThread(){
        //线程 顺序执行实现。
        Thread t1=new Thread(ThreadExcutionQuestion::action,"t1");
        Thread t2=new Thread(ThreadExcutionQuestion::action,"t2");
        Thread t3=new Thread(ThreadExcutionQuestion::action,"t3");

        t1.start();
        while (t1.isAlive()){

        }

        t2.start();
        while (t2.isAlive()){

        }

        t3.start();
        while (t3.isAlive()){

        }
    }



    private static void threadJoinOneByOne() throws InterruptedException {
        //线程 顺序执行实现。
        Thread t1=new Thread(ThreadExcutionQuestion::action,"t1");
        Thread t2=new Thread(ThreadExcutionQuestion::action,"t2");
        Thread t3=new Thread(ThreadExcutionQuestion::action,"t3");

        // start() 仅是通知线程启动
        t1.start();
        // join() 控制线程必须执行完成
        t1.join();

        t2.start();
        t2.join();

        t3.start();
        t3.join();
    }

    private static void action(){
        System.out.printf("线程[%s]正在执行... \n",Thread.currentThread().getName());
    }
}
