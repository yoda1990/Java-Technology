package org.hmily.concurrency.Thread;

/**
 * Created by zyzhmily on 2019/5/23.
 */
public class SynchronizedKeywordQuestion {

    public static void main(String[] args) {

        // 语义相同 字节码上的区别。
        // 一个是synchronized关键字  和 monitor 的区别

    }


    private  static void synchronizedBlock(){
         synchronized (SynchronizedKeywordQuestion.class){

         }
    }

    private synchronized static void synchronizedMethod(){

    }

}
