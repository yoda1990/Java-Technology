//package org.hmily.netty.guide.AIO;
//
//import java.io.IOException;
//import java.net.InetSocketAddress;
//import java.nio.channels.AsynchronousServerSocketChannel;
//import java.nio.channels.AsynchronousSocketChannel;
//import java.util.concurrent.CountDownLatch;
//
//public class TimeServer {
//
//    public static void main(String[] args) {
//        int port=8080;
//        if (args!=null&&args.length>0){
//            try{
//                port= Integer.valueOf(args[0]);
//            }catch (NumberFormatException e){
//                //采用默认值
//            }
//        }
//        new Thread(new AsyncTimeServerHandler(port),"AIO-AsyncTimeServerHandler-001").start();
//
//    }
//
//
//    public static class AsyncTimeServerHandler implements Runnable{
//
//        private int port;
//        CountDownLatch countDownLatch;
//        AsynchronousServerSocketChannel asynchronousServerSocketChannel;
//
//        public AsyncTimeServerHandler(int port) {
//            this.port = port;
//            try {
//                asynchronousServerSocketChannel=AsynchronousServerSocketChannel.open();
//                asynchronousServerSocketChannel.bind(new InetSocketAddress(port));
//                System.out.println("The time server is start in port : "+port);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//
//        }
//
//        @Override
//        public void run() {
//
//            countDownLatch=new CountDownLatch(1);
//            doAccept();
//            try {
//                countDownLatch.await();
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
//
//        private void doAccept() {
//            asynchronousServerSocketChannel.accept(this,new AcceptComletionHandler());
//        }
//    }
//
//}
