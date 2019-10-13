package com.hmily.algorithms.chapter1_base;

import java.util.Scanner;

/**
 * Created by zyzhmily on 2018/8/29.
 */
public class Test_115 {

    public static void main(String[] args){

        //1.1.5
        System.out.println("1.1.5:");
        Scanner scan=new Scanner(System.in);
        System.out.println("请输入2个数：");
        System.out.println("请输入x：");
        double x=scan.nextDouble();
        System.out.println("请输入y：");
        double y=scan.nextDouble();
        if ((x>0&&x<1)&&(y>0&&y<1)){
            System.out.println(true);
        }else{
            System.out.println(false);
        }
    }

}
