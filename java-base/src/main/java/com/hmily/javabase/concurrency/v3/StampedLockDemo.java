package com.hmily.javabase.concurrency.v3;

import java.util.Locale;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.StampedLock;

public class StampedLockDemo {


    public static void main(String[] args) {
        // Java 1.8 之后提供
        StampedLock stampedLock = new StampedLock();
        long stamp = stampedLock.tryOptimisticRead();
        Lock lock = stampedLock.asReadLock();
        try {
            lock.lock();
            stampedLock.validate(stamp);
        }finally {
            lock.unlock();
        }

    }


}
