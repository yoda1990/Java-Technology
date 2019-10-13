package com.hmily.javabase.concurrency;

/**
 * Created by zyzhmily on 2019/4/4.
 */
public class SynchroizedDemo {

    public static void main(String[] args) {

        synchronized (SynchroizedDemo.class){

        }

    }

    private static void changeValue(){
        // 线程私有对象，尽管它也在堆里面
        // 栈保存 value 名称，data 变量名称
        // 堆共享（被其他线程可见） 是线程不安全的，保存内存
        // 当线程不加以控制数量的话，容易导致JVM OOM
        Data data=new Data();
        data.setValue(1);
    }


    private static class Data{

        private volatile int value;

        public int getValue() {
            return value;
        }

        public void setValue(int value) {
            this.value = value;
        }
    }

}
