package org.hmily.concurrency.Thread;

/**
 * Created by zyzhmily on 2019/4/15.
 */
public class ThreadCreationDemo {

    /**
     *
     * Java 创建线程的方法有哪些？
     * 只有一种  new Thread();
     *
     *
     * @param args
     */
    public static void main(String[] args) {

        // 创建线程 只有一个方法  就是  new
        //main-> 子线程
        Thread t1=new Thread(()->{},"子线程1");

    }

    /**
     * 不鼓励自定义 扩展Thread
     */
    private static class MyThread extends Thread{

        /**
         * 多态的方式，覆盖父类实现
         */
        @Override
        public void run(){
            super.run();
        }
    }

}
