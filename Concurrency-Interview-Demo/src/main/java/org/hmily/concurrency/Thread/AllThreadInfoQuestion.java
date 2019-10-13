package org.hmily.concurrency.Thread;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import com.sun.management.ThreadMXBean;



/**
 * Created by zyzhmily on 2019/5/23.
 */
public class AllThreadInfoQuestion {


    public static void main(String[] args) {
        ThreadMXBean threadMXBean= (ThreadMXBean) ManagementFactory.getThreadMXBean();
        long[] threadIds=threadMXBean.getAllThreadIds();
        for (long threadId:threadIds){
//            ThreadInfo threadInfo=threadMXBean.getThreadInfo(threadId);
//            System.out.println(threadInfo.toString());
            long bytes=threadMXBean.getThreadAllocatedBytes(threadId);
            long kBytes=bytes/1024;
            System.out.printf("线程[ID : %d] 分配内存：%s KB\n",threadId,kBytes);
        }
    }

}
