package com.hmily.jvm.G1;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class G1Demo {


    /**
     * -Xmx1g 最大堆
     * -Xms1g 最小堆
     * -Xmn256m 新生代
     *
     * -Xmn 会覆盖 -XX:G1NewSizePercent=10
     * 实验性参数 -XX:G1NewSizePercent=10 加 -XX:+UnlockExperimentalVMOptions 开放实验性参数
     * -XX:+UnlockExperimentalVMOptions
     * -XX:G1NewSizePercent=10
     *
     * -XX:+UseG1GC 使用G1垃圾收集器
     * -XX:ParallelGCThreads=2 并行收集线程数
     * -XX:+PrintFlagsFinal 打印全部参数
     * -XX:+PrintGCDetails  打印GC详情
     * -XX:-UseGCOverheadLimit
     * -XX:MaxGCPauseMillis=100 GC停顿时间
     * -XX:GCPauseIntervalMillis=2000 GC停顿间隔时间
     * @param args
     */
    public static void main(String[] args) {
        Map<String,String> map = new HashMap<>();
        Random r = new Random();
        while (true){
            map.put(String.valueOf(r.nextInt()),"truw");
        }
    }

}
