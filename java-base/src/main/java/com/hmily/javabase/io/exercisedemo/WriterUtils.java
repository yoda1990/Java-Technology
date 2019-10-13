package com.hmily.javabase.io.exercisedemo;

import java.io.*;

public class WriterUtils {


    /**
     * 使用BufferedWriter写入
     * @param file
     * @throws IOException
     */
    public static void bufferedWriterHandler(FileWriter file) throws IOException {
        BufferedWriter bufferedWriter=new BufferedWriter(file);
        bufferedWriter.append("BufferedWriter ");
        bufferedWriter.append(" 测试中。。。");
        bufferedWriter.close();
    }



    /**
     * 使用PrintWriter写入
     * @param file
     */
    public static void printWriterHandler(FileWriter file){
        PrintWriter printWriter=new PrintWriter(file);
        printWriter.append("PrintWriter ");
        printWriter.append(" 测试中。。。");
        printWriter.close();
    }

}
