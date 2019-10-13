package com.hmily.javabase.java8newfuture;

public interface Formaula {


    // Java 允许  给接口添加一个非抽象的方法实现，使用关键字 default 就行
    //
    default double cal(){
        return 12.0;
    }
}
