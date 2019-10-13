package org.hmily.concurrency.Thread;

import java.io.IOException;

/**
 * Created by zyzhmily on 2019/4/15.
 */
public class ProcessCreationDemo {

    /**
     *
     * Java  如何创建一个进程
     *
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        Runtime runtime=Runtime.getRuntime();
        Process process=runtime.exec("calc");
        Process process2=runtime.exec("cmd /k start http://www.baidu.com");
        process.exitValue();
        process2.exitValue();
    }


}
