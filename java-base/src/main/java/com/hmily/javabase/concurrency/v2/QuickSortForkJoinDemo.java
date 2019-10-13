package com.hmily.javabase.concurrency.v2;


import java.util.Arrays;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.TimeUnit;

/**
 * Created by zyzhmily on 2019/4/10.
 */
public class QuickSortForkJoinDemo {

    public static void main(String[] args) throws InterruptedException {

        int[] values=new int[]{2,5,6,7,8,8,9,2,1,6,7,5,6,11,23};

        ForkJoinPool pool =new ForkJoinPool();
        pool.submit(new QuickSortTask(values));
        pool.awaitTermination(100, TimeUnit.MICROSECONDS);
        pool.shutdown();
        System.out.println(Arrays.asList(values).toString());
    }

    private static class QuickSortTask extends RecursiveAction{

        private final  int[] parts;

        private final int low;

        private final int high;

        private static final int THRESHOLD=4;

        private QuickSortTask(int[] parts) {
            this.parts = parts;
            this.low=0;
            this.high=parts.length-1;
        }

        public QuickSortTask(int[] parts, int low, int high) {
            this.parts = parts;
            this.low = low;
            this.high = high;
        }

        @Override
        protected void compute() {
              if (high-low<THRESHOLD){
                  sort(parts,low,high);
              }else{
                  int pivot=partition(parts,low,high);
                  QuickSortTask task=new QuickSortTask(parts,low,pivot-1);
                  QuickSortTask task2=new QuickSortTask(parts,pivot+1,high);
                  task.fork().join();
                  task2.fork().join();
              }
        }

        private void sort(int[] parts, int low, int high) {
            Arrays.sort(parts,low,high);
        }

        /**
         * 获取分区索引
         *
         * @param parts 数组对象
         * @param low    低位索引
         * @param high   高位索引
         * @return 分区索引
         */
        private int partition(int[] parts, int low, int high) {
            // 获取 pivot = values[high]

            // [3, 1, 2, 5, 4]
            // pivot = 4
            //              -1
            // [0] = 3 < 4 (0)
            // [1] = 1 < 4 (1)
            // [2] = 2 < 4 (2)
            // [3] = 5 > 4 (3)
            // => [(3, 1, 2), (4), (5)]
            // pIndex = 3

            int pivot=parts[high];
            int i=low;
            for (int j=low;j<high;j++){
                if (parts[j]<pivot){
                    int temp=parts[i];
                    parts[i]=parts[j];
                    parts[j]=temp;
                    i++;
                }
            }
            int temp=parts[i];
            parts[i]=parts[high];
            parts[high]=temp;
            return i;
        }

    }


}
