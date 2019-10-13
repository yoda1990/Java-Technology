package com.hmily.javabase.io.exercisedemo;

import java.io.*;

public class ReaderUtils {


    /**
     * fileRead 读取文件
     *
     * @param fileName
     * @throws IOException
     */
    public static void fileReadHandler(String fileName) throws IOException {
        File file = new File(fileName);
        Reader reader=new FileReader(fileName);
        long length=file.length();
        char[] chars=new char[Math.toIntExact(length)];
        StringBuilder stringBuilder = new StringBuilder();
        try{
            while (reader.read(chars)>0){
                stringBuilder.append(chars);
            }
            System.out.println("FileReaad 读取的内容是："+stringBuilder.toString());
        }finally {
            if (reader!=null){
                reader.close();
            }
        }
    }


    /**
     * InputStreamReader+BufferedReader读取字符串 ，
     * InputStreamReader类是从字节流到字符流的桥梁，
     * 按行读对于要处理的格式化数据是一种读取的好方式
     * @param fileName
     * @throws IOException
     */
    public static void bufferReadHandler(String fileName) throws IOException {
          BufferedReader reader=new BufferedReader(new FileReader(fileName));
          StringBuilder stringBuilder=new StringBuilder();
          try{
              String data=null;
              while (reader.readLine()!=null){
                  data=reader.readLine();
                  stringBuilder.append(reader.readLine());
              }
              System.out.println("BufferedReader 读取的内容是："+stringBuilder.toString());
          }finally {
              if (reader!=null){
                  reader.close();
              }
          }


    }

}
