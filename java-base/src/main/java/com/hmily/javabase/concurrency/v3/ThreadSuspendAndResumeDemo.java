package com.hmily.javabase.concurrency.v3;

public class ThreadSuspendAndResumeDemo {

    public static void main(String[] args) {

        // Thread.suspend() 和 Thread.resume() 方法可以运用任意区域
        // suspend()：指定线程挂起，resume() ：指定线程回复
        // suspend() 和 resume() ，导致死锁。
        // Object.wait() 和 Object.notify() 只能用在 synchronized
        // 通过对象Monitor 控制线程状态
        //
        // stop() 方法弃用是因为：当对象获得锁的时候调用这个方法，
        // 会导致ThreadDeath，这样会释放锁，其他线程看到的monitor数据不一样，出现线程不安全问题。
        //

    }


}
