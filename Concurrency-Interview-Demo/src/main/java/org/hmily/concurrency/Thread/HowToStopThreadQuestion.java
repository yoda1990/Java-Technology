package org.hmily.concurrency.Thread;

/**
 * Created by zyzhmily on 2019/5/23.
 */
public class HowToStopThreadQuestion {


    public static void main(String[] args) throws InterruptedException {

        Action action=new Action();

        Thread t1=new Thread(action,"t1");
        t1.start();
        action.setStop(true);
        t1.join();

        Thread t2=new Thread(()->{
            if (!Thread.currentThread().isInterrupted()){
                action();
            }
        },"t2");
        t2.start();
        // 中断操作（仅仅设置状态，而非中止线程）
        t2.interrupt();
        t2.join();

    }


    private static class Action implements Runnable{

        private volatile boolean isStop=false;

        @Override
        public void run() {
            if (!isStop){
                action();
            }
        }

        public void setStop(boolean stop) {
            isStop = stop;
        }
    }


    private static void action(){
        System.out.printf("线程[%s]正在执行... \n",Thread.currentThread().getName());
    }


    /**
     *
     * interrupt、interrupted、isInterrupted 的区别
     *
     * interrupt  设置线程的中止状态
     *
     * isInterrupted 判断线程是否是中止状态
     *
     * interrupted 判断线程是否是中止状态 并清除
     */

}
