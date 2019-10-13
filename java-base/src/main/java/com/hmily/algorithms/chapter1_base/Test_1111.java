package com.hmily.algorithms.chapter1_base;

/**
 * Created by zyzhmily on 2018/8/29.
 * 遍历二位数组
 */
public class Test_1111 {

    public static void main(String[] args){
        boolean[][] result={{true,true,false,true},{false,false,true,false}};
        for (int i=0;i<result.length;i++){
            for (int j=0;j<result[i].length;j++){
                System.out.print("第"+i+"行，第"+j+"列");
                System.out.print(" ");
                if (result[i][j]){
                    System.out.println("*");
                }else{
                    System.out.println(" ");
                }
            }
        }
    }



}
