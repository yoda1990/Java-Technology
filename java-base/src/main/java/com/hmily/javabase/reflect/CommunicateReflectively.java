package com.hmily.javabase.reflect;

import java.lang.reflect.Method;

public class CommunicateReflectively {


    public static void perform(Object speaker){
        Class<?> spkr=speaker.getClass();
        try {
            Method speak=spkr.getMethod("speak");
            speak.invoke(speaker);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(speaker+" cannot speak");
        }
        try {
            Method sit=spkr.getMethod("sit");
            sit.invoke(speaker);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(speaker+" cannot sit");
        }

    }


}
