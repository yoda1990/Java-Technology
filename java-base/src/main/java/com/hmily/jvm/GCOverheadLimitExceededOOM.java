package com.hmily.jvm;


import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * GCOverheadLimitExceeded
 */
public class GCOverheadLimitExceededOOM {


    /**
     * -Xmx16m 最大堆大小
     * -XX:-UseGCOverheadLimit 只做GC
     * -XX:+PrintFlagsFinal 打印参数 比较重要。
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
