package com.hmily.jvm;


/**
 * java.lang.OutOfMemoryError: unable to create native thread
 */
public class UnableToCreateNewNativeThreadOOM {


    public static void main(String[] args) {
        for (;;){
            new Thread(()->{
               System.out.println("Hello World!");
            }).start();
        }
    }

}
