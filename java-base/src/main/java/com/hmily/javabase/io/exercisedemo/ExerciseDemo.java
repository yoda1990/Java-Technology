package com.hmily.javabase.io.exercisedemo;

import java.io.*;


public class ExerciseDemo {

    public static void main(String[] args) throws IOException {
        //demo1();
        String fileName = ExerciseDemo.class.getResource("/inputTest.txt").getFile();

        /************** InputStream ****************/
        // 按段部分读取
        InputStreamUtils.readString1(fileName);
        // 一次性全部读取
        InputStreamUtils.readString2(fileName);

        /************** OutputStream ****************/
        // FileOutputStream 输出
        String path1 = "/F:/GitHub/java-base/target/classes/FileOutputStreamTest.txt";
        File file1=new File(path1);
        OutPutStreamUtils.fileOutputStreamHandler(file1);
        // PrintStream 输出
        String path2 = "/F:/GitHub/java-base/target/classes/PrintStreamTest.txt";
        File file2=new File(path2);
        OutPutStreamUtils.printStreamHandler(file2);

        /*********** Reader ***********/
        // fileRead 读取
        ReaderUtils.fileReadHandler(fileName);
        // bufferedReader 读取
        ReaderUtils.bufferReadHandler(fileName);

        /************ Writer ****************/
        FileWriter fileWriter1=new FileWriter( "/F:/GitHub/java-base/target/classes/BufferedWriterTest.txt");
        WriterUtils.bufferedWriterHandler(fileWriter1);
        FileWriter fileWriter2=new FileWriter( "/F:/GitHub/java-base/target/classes/PrintWriterTest.txt");
        WriterUtils.printWriterHandler(fileWriter2);
    }

    /**
     * 相对路径读取文件
     *
     * 相对路径则在 target 的 classes 的 文件夹下面
     *
     */
    private static  void demo1(){
        //方法一
        String fileName = ExerciseDemo.class.getResource("/inputTest.txt").getFile();
        System.out.println("*****方法一相对路径读取到的文件地址："+fileName);
        //方法二
        String fileName1 = Thread.currentThread().getContextClassLoader().getResource("inputTest.txt").getFile();
        System.out.println("*****方法二相对路径读取到的文件地址："+fileName1);
    }




}
