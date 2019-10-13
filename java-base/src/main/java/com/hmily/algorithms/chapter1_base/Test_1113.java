package com.hmily.algorithms.chapter1_base;

/**
 * Created by zyzhmily on 2018/8/30.
 * M行N列 二维数组转置 行列互换
 */
public class Test_1113 {


    public static void main(String[] args){
        int[][] arr={{1,2,3},{3,2,1}};
        //这是一个2行3列的二维数组
        int m=arr.length;
        System.out.println(m+"行");
        int n=arr[0].length;
        System.out.println(n+"列");
        int[][] newArr=new int[n][m];
        for (int i=0;i<arr.length;i++){
            for (int j=0;j<arr[i].length;j++){
                newArr[j][i]=arr[i][j];
            }
        }
        //转置后为3行2列数组。
        System.out.println(newArr.toString());
        int newM=newArr.length;
        System.out.println(""+newM+"行");
        int newN=newArr[0].length;
        System.out.println(""+newN+"列");
    }
}
