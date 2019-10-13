package com.hmily.algorithms.chapter1_base;

import java.util.Scanner;

/**
 * Created by zyzhmily on 2018/8/29.
 */
public class Test_113 {

    public static void main(String[] args){

        //1.1.1
        System.out.println("1.1.3:");
        Scanner scan=new Scanner(System.in);
        System.out.println("请输入3个整数：");
        int num1=scan.nextInt();
        int num2=scan.nextInt();
        int num3=scan.nextInt();
        if (num1==num2&&num1==num3&&num2==num3&&num1==num3){
            System.out.println("equal");
        }else{
            System.out.println("not equal");
        }
    }

}
