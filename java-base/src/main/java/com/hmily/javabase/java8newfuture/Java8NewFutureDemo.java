package com.hmily.javabase.java8newfuture;

public class Java8NewFutureDemo {


    public static void main(String[] args) {
        // Java 8 新特性
        // 1、可以给接口一个默认方法 为非抽象方法的实现 default 关键字修饰

        //2、lambda表达式

        // 方法引用 System.out.println(Demo::print)
        // names.forEach(System.out::println);

        //3、函数式接口
        // @FunctionalInterface
        //如定义了一个函数式接口如下：
        //@FunctionalInterface
        //interface GreetingService
        //{
        //    void sayMessage(String message);
        //}
        //那么就可以使用Lambda表达式来表示该接口的一个实现(注：JAVA 8 之前一般是用匿名类实现的)：
        //GreetingService greetService1 = message -> System.out.println("Hello " + message);

        // Stream
        // 集合 流操作
        //Stream（流）是一个来自数据源的元素队列并支持聚合操作
        //元素是特定类型的对象，形成一个队列。 Java中的Stream并不会存储元素，而是按需计算。
        //数据源 流的来源。 可以是集合，数组，I/O channel， 产生器generator 等。
        //聚合操作 类似SQL语句一样的操作， 比如filter, map, reduce, find, match, sorted等。
        //生成流
        //在 Java 8 中, 集合接口有两个方法来生成流：
        //stream() − 为集合创建串行流。
        //parallelStream() − 为集合创建并行流。
        //List<String> strings = Arrays.asList("abc", "", "bc", "efg", "abcd","", "jkl");
        //List<String> filtered = strings.stream().filter(string -> !string.isEmpty()).collect(Collectors.toList());

        //并行（parallel）程序
        //parallelStream 是流并行处理程序的代替方法。以下实例我们使用 parallelStream 来输出空字符串的数量：
        //List<String> strings = Arrays.asList("abc", "", "bc", "efg", "abcd","", "jkl");
        //// 获取空字符串的数量
        //int count = strings.parallelStream().filter(string -> string.isEmpty()).count();

        //5、Date API  Clock 时钟，Timezones 时区，LocalTime 本地时间，LocalDate 本地日期，LocalDateTime 本地日期时间
        //

        // Annotation 注解
        // 多重注解、新的target -》@Target({ElementType.TYPE_PARAMETER, ElementType.TYPE_USE})
    }
}
