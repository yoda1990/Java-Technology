package com.hmily.javabase.java_memory_model;


/**
 * Created by zyzhmily on 2019/5/20.
 */
public class HappensBeforeRelationshipDemo {

    // 性能开销： Lock > CAS > volatile
    // 术语    ： Mutex > CAS > Memory Barrier
    // 底层分析： N多指令 > 多个指令 > 单个或若干指令

    public static void main(String[] args) {

    }


    private static void inSameThread(){
        // action 1
        // action 2

    }

    private static void constructorHappensBeforeFinalizer(){
        // 构造早于销毁 （终结）之前
        // 构造对象是在用户线程（main、子线程）
        // Finalizer 操作是JVM 线程 （GC线程）

        // 对象存放在 Heap 里面，Heap 对于线程是共享的。
        // 假设 Object 刚创建，Finalizer线程看到该对象，马上回收
    }

    /**
     * lock 对象是一个锁对象，也可视作 monitor
     */
    private static final Object monitor=new Object();

    private static void synchronizedAndWait() throws InterruptedException {

        // JMM 描述：
        // monitor (lock) happens-before monitor.wait()
        // monitor.wait() happens-before monitor (unlock)

        // 实际情况：
        // monitor (lock) synchronizes-with monitor.wait()
        // monitor.wait() synchronizes-with monitor (unlock)

        // if x synchronizes-with y,then x happens-before y
        synchronized (monitor){
            monitor.wait();//当 wait() 方法所属对象没有被synchronized 关键字修饰
            //将抛出 IllegalMonitorStateException
        }
    }


    private static void threadAtartAndJoin() throws InterruptedException {
        Thread t=new Thread(()->{
            // action 动作
        });

        // main 线程调用线程 t 的 join() 方法
        // 在 join() 方法返回之前，t 所有的actions 已执行结束
        t.join();

    }



}
