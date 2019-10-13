package com.hmily.javabase.io.exercisedemo;

import java.io.*;

public class OutPutStreamUtils {


    /**
     *  使用FileOutputStream
     *
     * fileOutPutStream 输出
     */
    public static void fileOutputStreamHandler(File file) throws IOException {
        OutputStream outputStream=new FileOutputStream(file);
        try{
            outputStream.write("fileOutputStream".getBytes());
            outputStream.flush();
        }finally {
            if (null!=outputStream){
                outputStream.close();
            }
        }
    }

    /**
     * 使用 PrintStream 输出
     * @param file
     * @throws IOException
     */
    public static void printStreamHandler(File file) throws IOException {
        PrintStream printStream;
        printStream=new PrintStream(new FileOutputStream(file));
        try{
            printStream.append("PrintStream");
            printStream.append("，使用！！");
        }finally {
            if (null!=printStream){
                printStream.close();
            }
        }
    }

}
