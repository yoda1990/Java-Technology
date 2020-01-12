package com.hmily.algorithms.sort;


import java.util.stream.Stream;

/**
 * 冒泡排序实现
 * @param <T>
 */
public class BubleSort<T extends Comparable<T>> implements Sort<T>{

    /**
     * Comparable.compareTo
     * 1  >
     * 0  =
     * -1 <
     * @param values
     */
    public void sort(T[] values) {
        int size = values.length;
        for (int i=0;i<size;i++){
            T t = values[i];
            for (int k = i+1;k<size;k++){
                  if(t.compareTo(values[k])==1){
                        values[i] = values[k];
                        values[k] = t;
                        break;
                  }
            }
        }
    }


    public static void main(String[] args) {
        Integer[] values = of(3,1,2,5,4);
        Sort<Integer> bubleSort = new BubleSort();
        bubleSort.sort(values);
        Stream.of(values).forEach(System.out::println);
    }


    private static <T> T[] of(T... values){
          return values;
    }
}
