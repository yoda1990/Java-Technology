package org.hmily.concurrency.Thread;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;

/**
 * Created by zyzhmily on 2019/5/23.
 */
public class AllThreadStackQuestion {

    //当前所有线程
    public static void main(String[] args) {
        ThreadMXBean threadMXBean= ManagementFactory.getThreadMXBean();
        long[] threadIds=threadMXBean.getAllThreadIds();
        for (long threadId:threadIds){
            ThreadInfo threadInfo=threadMXBean.getThreadInfo(threadId);
            System.out.println(threadInfo.toString());
        }
    }

}
