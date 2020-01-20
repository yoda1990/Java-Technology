package com.hmily.javabase.concurrency.v3;

public class HowToStopThreadDemo {


    public static void main(String[] args) throws InterruptedException {
        StoppableAction stoppableAction = new StoppableAction();

        Thread thread = new Thread(stoppableAction);

        thread.start();

        stoppableAction.stop();

        thread.join();
    }


    private static class StoppableAction implements Runnable{

        // 保证 可见性
        private volatile boolean stopped;

        @Override
        public void run() {
            if (stopped){
                System.out.println("Action 终止。。。。。");
                return;
            }
            System.out.println("Action 执行。。。。。");
        }

        public void stop(){
            this.stopped = true;
        }
    }


}
