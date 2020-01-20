package com.hmily.javabase.concurrency.v3;

public class SynchronizedDemo {

    public static void main(String[] args) {

    }

    private static class Data{

        private volatile int value;

        public int getValue() {
            return value;
        }

        public void setValue(int value) {
            this.value = value;
        }
    }
}
