package com.hmily.javabase.process;


import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;

/**
 * Created by zyzhmily on 2019/4/4.
 */
public class ProcessIdDemo {

    public static void main(String[] args) {

        // java 9 之前的实现
        getProcessIdInBeforeJava9();

        // Java 9 +
        getProcessIdInJava9();

        // Java 10 
        getProcessIdInJava10();


    }

    private static void getProcessIdInJava10() {
        // RuntimeMXBean runtimeMxBean=ManageMentFactory.getRuntimeMXBean();
        //System.out.println("【Java 10 的方法】当前进程 ID : "+runtimeMxBean.getPid());
    }

    private static void getProcessIdInJava9() {
        //long pid= ProcessHandle.current().pid();
        //System.out.println("【Java 9 之后的方法】当前进程 ID : "+pid);

    }

    private static void getProcessIdInBeforeJava9() {

        RuntimeMXBean runTimeMxBean= ManagementFactory.getRuntimeMXBean();
        String name=runTimeMxBean.getName();
        String pid=name.substring(0,name.indexOf("@"));
        System.out.println("【Java 9 之前的方法】当前进程 ID : "+pid);

    }

    
}
