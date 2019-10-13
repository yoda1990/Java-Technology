package com.hmily.javabase.io.exercisedemo;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class InputStreamUtils {

    /**
     * 以字节为单位读取文件，常用于读二进制文件，如图片、声音、影像等文件。
     * 当然也是可以读字符串的。
     * @param fileName
     * @throws IOException
     */
    public static void readString1(String fileName) throws IOException {
        InputStream inputStream=new FileInputStream(fileName);
        StringBuilder stringBuilder;
        try {
            byte[] bytes=new byte[1024];
            stringBuilder=new StringBuilder();
            while (inputStream.read(bytes)!=-1){
                stringBuilder.append(bytes);
            }
            System.out.printf("读取的内容为  %s  \n",stringBuilder.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (null!=inputStream){
                inputStream.close();
            }
        }
    }


    /**
     *
     * 按字节读取字符串,一次性读取完
     *
     * @param fileName
     * @throws IOException
     */
    public static void readString2(String fileName) throws IOException {
        InputStream inputStream=new FileInputStream(fileName);
        StringBuilder stringBuilder=null;
        try {
            byte[] bytes=new byte[inputStream.available()];
            inputStream.read(bytes);
            System.out.printf("读取的内容为  %s  \n",new String(bytes,"UTF-8"));
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (null!=inputStream){
                inputStream.close();
            }
        }
    }


}
