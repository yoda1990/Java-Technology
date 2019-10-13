package com.hmily.javabase.thread;

/**
 * Created by zyzhmily on 2019/4/3.
 */
public class DumpThreadDemo {

    public static void main(String[] args) {

        //Throwable API
        new Throwable("Stack trace").printStackTrace(System.out);

        //Thread API
        Thread.dumpStack();

        //性能更优的实现
        // Java 9 StackWaler API
        // StackWalker stackWalker = StackWalker.getInstance();
        // stackWalker.forEack(System.out::println);


    }


}
