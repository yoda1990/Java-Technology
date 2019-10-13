package org.hmily.concurrency.collection;

import java.util.*;

public class ThreadSafeCollectionQuestion {

    public static void main(String[] args) {

        // java 9 的实现
        List<Integer> list= Arrays.asList(1,2,3,4,5);
        // Java 9+ of 工厂方法，返回 Immutable 对象
        Set<Integer> set=Set.of(1,2,3,4,5);

        Map<Integer,String> map=Map.of(1,"A");

        // 以上实现都是不变对象，不过第一个除外
        // 通过 Collections#synchronized* 方法返回
        // Wrapper 设计模式（所有的方法都是 synchronized 修饰）

        list= Collections.synchronizedList(list);

        set=Collections.synchronizedSet(set);

        map=Collections.synchronizedMap(map);






    }

}
