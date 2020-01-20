package com.hmily.javabase.concurrency.v3;

public class AcquireAndReleaseDemo {

    public static void main(String[] args) {
        // Lock 机制
        // 获得 Acquire
        //    Thread 进入synchronized -> 获得锁
        // 释放 Release
        //    1、当Thread（hold lock）,调用Object#wait() 时候
        //    2、Thread park -> LockSupport.part(Object)
        //    3、Condition#await()
        //    4、运行期异常，Thread 消亡
        //    5、Java 9 自旋转 Thread.onSpinWait();
        //    6、Thread.yield();
        // 所谓的公平（Fair）和非公平（NonFair）
        // 公平（Fair）线程 FIFO
        // 非公平（NonFair）线程随线程调度
        // 最佳实践：创建线程时，除非必要，不要调整线程优先级（Priority）
    }


}
