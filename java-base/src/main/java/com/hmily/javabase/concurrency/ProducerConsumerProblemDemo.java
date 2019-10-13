package com.hmily.javabase.concurrency;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Created by zyzhmily on 2019/4/5.
 * java 并发 生产者 消费者模型
 */
public class ProducerConsumerProblemDemo {

    public static void main(String[] args) throws InterruptedException {
        ExecutorService executorService= Executors.newFixedThreadPool(2);
        Container container = new Container();

        Future producFuture= executorService.submit(()->{
            container.produce();
        });
        Future consumeFuture=executorService.submit(()->{
            container.consume();
        });

        Thread.sleep(1000L);
        executorService.shutdown();
    }

    public static class Container {
        private List<Integer> data=new LinkedList<>();
        private static final int MAX_SIZE=5;

        private Random random=new Random();

        public void produce(){
            //生产者永久执行
            while (true){
                synchronized (this){
                    try {
                        //当数据超过上限 MAX_SIZE ，停止生产
                        while (data.size()>=MAX_SIZE){
                            wait();
                        }
                        int value=random.nextInt(100);
                        System.out.printf("线程[%s] 正在生产数据 : %d\n", Thread.currentThread().getName(), value);
                        data.add(value);
                        //唤起线程
                        notify();
                        Thread.sleep(1000L);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        public void consume(){
            //消费者永久执行
            while (true){
                synchronized (this){
                    try {
                        //当数据为空时，停止消费
                        while (data.isEmpty()){
                            wait();
                        }
                        int value = data.remove(0);
                        System.out.printf("线程[%s] 正在消费数据 : %d\n", Thread.currentThread().getName(), value);
                        //唤起线程
                        notify();
                        Thread.sleep(1000L);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
