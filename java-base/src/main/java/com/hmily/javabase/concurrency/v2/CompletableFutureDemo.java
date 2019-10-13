package com.hmily.javabase.concurrency.v2;

import java.util.concurrent.CompletableFuture;

/**
 * Created by zyzhmily on 2019/4/13.
 */
public class CompletableFutureDemo {

    public static void main(String[] args) {

        CompletableFuture.supplyAsync(()->{
            return 1;
        }).thenApply(String::valueOf).completeExceptionally(new RuntimeException());//异常的方式结束。



    }
}
