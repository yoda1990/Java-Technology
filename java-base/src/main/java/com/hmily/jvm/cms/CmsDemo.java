package com.hmily.jvm.cms;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class CmsDemo {


    /**
     * -XX:+UseConcMarkSweepGC  使用 CMS垃圾收集器
     * -XX:CMSInitiatingOccupancyFraction=50  收集频率
     *
     * -XX:+PrintFlagsFinal 打印参数
     * -XX:+PrintGCDetails  打印细节
     * -XX:+PrintGCDateStamps  打印日期时间戳
     * -XX:+PrintGCTimeStamps  打印时间戳
     *
     * -XX:-UseGCOverheadLimit 关闭GC限制，一直GC。
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
