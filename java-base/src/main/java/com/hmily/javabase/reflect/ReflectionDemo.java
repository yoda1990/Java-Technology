package com.hmily.javabase.reflect;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class ReflectionDemo {

    // 反射学习
    public static void main(String[] args) throws Exception{
        // 通过反射获取一个类的Class
        Fruit fruit = new Fruit();
        //方法一
        Class<?> class1 = fruit.getClass();
        //方法二
        Class<?> class2 = Fruit.class;
        //方法三，如果这里不指定类所在的包名会报 ClassNotFoundException 异常
        Class class3 = null;
        try {
            class3 = Class.forName("com.hmily.javabase.reflect.ReflectionDemo");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        System.out.println(class1 + "  " +class2 + "    " + class3);

        // 通过反射获取类的方法
        Proxy target =new Proxy();
        Method method=Proxy.class.getDeclaredMethod("run");
        method.invoke(target);

        //通过反射获取成员变量
        printFieldMessage(new Fruit(1,"apple"));

        // 构造函数的反射
        printConMessage(new Fruit(1,"apple"));

        //动态加载类
        newInstance("com.hmily.javabase.reflect.ReflectionDemo");


    }

   public static class Proxy {
       public void run() {
           System.out.println("run");
      }
   }

   public static class Fruit{
        private int type;

        private String name;

       public Fruit() {
       }

       public Fruit(int type, String name) {
           this.type = type;
           this.name = name;
       }

       public int getType() {
           return type;
       }

       public void setType(int type) {
           this.type = type;
       }

       public String getName() {
           return name;
       }

       public void setName(String name) {
           this.name = name;
       }
   }


    public static void printFieldMessage(Object obj){
        Class c = obj.getClass();
        Field[] fs = c.getFields();
        for (Field field : fs) {
            //得到成员变量的类型的类类型
            Class fieldType = field.getType();
            String typeName = fieldType.getName();
            //得到成员变量的名称
            String fieldName = field.getName();
            System.out.println(typeName+" "+fieldName);
        }
    }

    public static void printConMessage(Object obj){
        Class c = obj.getClass();
        /*
         * 首先构造函数也是对象，是java.lang.Constructor类的对象
         * 也就是java.lang. Constructor中封装了构造函数的信息
         * 和前面说到的一样，它也有两个方法：
         * getConstructors()方法获取所有的public的构造函数
         * getDeclaredConstructors()方法得到所有的自己声明的构造函数
         */
        //Constructor[] cs = c.getConstructors();
        Constructor[] cs = c.getDeclaredConstructors();
        for (Constructor constructor : cs) {
            //我们知道构造方法是没有返回值类型的，但是我们可以：
            System.out.print(constructor.getName()+"(");
            //获取构造函数的参数列表》》得到的是参数列表的类类型
            Class[] paramTypes = constructor.getParameterTypes();
            for (Class class1 : paramTypes) {
                System.out.print(class1.getName()+",");
            }
            System.out.println(")");
        }
    }


    public static void newInstance(String className){
        try{
            Class cl= Class.forName(className);
            //通过类类型，创建该类的对象
            ReflectionDemo reflectionDemo = (ReflectionDemo) cl.newInstance();
            reflectionDemo.printConMessage(reflectionDemo);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

}
