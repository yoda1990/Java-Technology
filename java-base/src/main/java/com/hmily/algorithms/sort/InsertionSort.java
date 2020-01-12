package com.hmily.algorithms.sort;

import java.util.stream.Stream;


/**
 * 插入排序实现
 * @param <T>
 */
public class InsertionSort<T extends Comparable<T>> implements Sort<T> {

    /**
     *
     * @param values
     */
    @Override
    public void sort(T[] values) {
          int size = values.length;
          for (int i = 1;i<size;i++){
              T t = values[i];
              for (int k = i-1;k<i;k++){
                  if (t.compareTo(values[k])<1){
                      values[i] = values[k];
                      values[k] = t;
                  }
              }
          }
    }

    public static void main(String[] args) {
        Integer[] values = of(3,1,2,5,4);
        Sort<Integer> integerSort = new InsertionSort();
        integerSort.sort(values);
        Stream.of(values).forEach(System.out::println);
    }


    private static <T> T[] of(T... values){
        return values;
    }
}
