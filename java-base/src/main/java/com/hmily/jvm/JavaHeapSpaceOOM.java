package com.hmily.jvm;


/**
 * java.lang.OutOfMemoryError:Java heap space 案例
 */
public class JavaHeapSpaceOOM {

    public static void main(String[] args) {
        int size = 2 * 1024 * 1024; // 2M

        // -Xmx10m = 10 * 1024 * 1024
        // int = 32 bit = 4 Byte
        // size(2MB) * 4 = 8MB

        // [OK] -Xmx16m
        // [OK] -Xmx32m
        // [OK] -Xmx64m
        // [OK] -Xmx128m
        int[] array = new int[size];
    }

}
