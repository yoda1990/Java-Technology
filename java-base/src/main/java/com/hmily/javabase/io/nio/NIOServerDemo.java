package com.hmily.javabase.io.nio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.channels.*;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.Set;

public class NIOServerDemo extends Thread {

    public void run(){
        try(Selector selector=Selector.open();
            ServerSocketChannel serverSocketChannel=ServerSocketChannel.open();){
            serverSocketChannel.bind(new InetSocketAddress(InetAddress.getLocalHost(),8888));
            serverSocketChannel.configureBlocking(false);
            //注册到 Selector ，并说明关注点
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
            while (true){
                selector.select();
                Set<SelectionKey> selectionKeys=selector.keys();
                Iterator<SelectionKey> iterator=selectionKeys.iterator();
                while (iterator.hasNext()){
                    SelectionKey selectionKey=iterator.next();
                    //生产系统中一般会额外进行就绪状态检查
                    sayHello((ServerSocketChannel) selectionKey.channel());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sayHello(ServerSocketChannel serverSocketChannel) throws IOException {
        try(SocketChannel client=serverSocketChannel.accept();){
            client.write(Charset.defaultCharset().encode("Hello World!!"));
        }
    }

    public static void main(String[] args)throws IOException {
        NIOServerDemo demoServer=new NIOServerDemo();
        demoServer.start();
        try(Socket client=new Socket(InetAddress.getLocalHost(),8888)){
            BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(client.getInputStream()));
            bufferedReader.lines().forEach(s -> System.out.println(s));
        }
    }

}
