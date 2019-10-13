package com.hmily.javabase.process;

import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;

/**
 * Created by zyzhmily on 2019/4/4.
 */
public class ChildProcessDemo {

    public static void main(String[] args) throws IOException {

        // IDEA(主进程) -> 启动 ChildProcessDemo -> Windowa 计算器 （calc）
        OperatingSystemMXBean operatingSystemMXBean= ManagementFactory.getOperatingSystemMXBean();

        if (operatingSystemMXBean.getName().startsWith("Windows")){
            // 启动计算器
            Runtime.getRuntime().exec("calc");
        }


    }

}
