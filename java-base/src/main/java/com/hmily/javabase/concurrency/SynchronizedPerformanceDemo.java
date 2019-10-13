package com.hmily.javabase.concurrency;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;


/**
 * Created by zyzhmily on 2019/4/4.
 */
public class SynchronizedPerformanceDemo {

    public static void main(String[] args) {
        // 对比 Vector 和 ArrayList
        // 相同点：两者都使用Array 作为存储，集合算法类似
        Vector vector=new Vector();
        ArrayList list= new ArrayList();
        testAdd(10000,vector);
        testAdd(10000,list);
        System.gc();
        testAdd(100000,vector);
        testAdd(100000,list);
    }

    private static void testAdd(int count,Vector vector) {
        doTest(count,vector);
    }

    private static void testAdd(int count,ArrayList list) {
        doTest(count,list);
    }

    private static void doTest(int count, List list){
        long startTime=System.currentTimeMillis();
        for (int i=0;i<count;i++){
            list.add(new Object());
        }
        long costTime=System.currentTimeMillis()-startTime;
        System.out.printf("%s add %d costing %s ms \n",list.getClass().getName(),count,costTime);
    }
}
