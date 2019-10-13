package com.hmily.javabase.io.aio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class JavaAIODemo {

    public static void main(String[] args) {


        try {
            test01();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private static void test01() throws IOException {
        char ch;
        BufferedReader reader=new BufferedReader(new InputStreamReader(System.in));
        while ((ch = (char) reader.read()) != -1){
            System.out.println(ch);
        }
    }
}
