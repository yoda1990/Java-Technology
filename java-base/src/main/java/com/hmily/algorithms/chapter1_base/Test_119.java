package com.hmily.algorithms.chapter1_base;

import java.util.Scanner;

/**
 * Created by zyzhmily on 2018/8/29.
 * 将一个正整数转化成二进制数，用String表示
 */
public class Test_119 {

    public static void main(String[] args){
        System.out.println("1.1.9:");
        System.out.println("请输入整数：");
        Scanner scanner=new Scanner(System.in);
        int x=scanner.nextInt();
        String s="";
        for (int n=x;n>0;n=n/2){
            s=n%2+s;
        }
        System.out.println(x+"的二进制数为："+s);
    }

}
