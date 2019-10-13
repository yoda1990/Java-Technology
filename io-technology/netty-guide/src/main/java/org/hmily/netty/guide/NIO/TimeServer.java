package org.hmily.netty.guide.NIO;


import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Date;
import java.util.Iterator;
import java.util.Set;

public class TimeServer {


    public static void main(String[] args) {
        int port=8080;
        if (args!=null&&args.length>0){
            try{
                port= Integer.valueOf(args[0]);
            }catch (NumberFormatException e){
                //采用默认值
            }
        }
        MultiplexerTimeServer timeServer=new MultiplexerTimeServer(port);
        new Thread(timeServer,"NIO-MultiplexerTimeServer-001").start();
    }

    public static class MultiplexerTimeServer implements Runnable{

        private Selector selector;

        private ServerSocketChannel serverSocketChannel;

        private volatile boolean stop;

        public MultiplexerTimeServer(int port) {
            try {
                selector=Selector.open();
                serverSocketChannel=ServerSocketChannel.open();
                serverSocketChannel.configureBlocking(false);
                serverSocketChannel.socket().bind(new InetSocketAddress(port),1024);
                serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
                System.out.println("The time server is start in port : "+ port);
            } catch (IOException e) {
                e.printStackTrace();
                System.exit(1);
            }
        }

        public void stop(){
            this.stop=true;
        }


        public void run() {
            while (!stop){
                try {
                    selector.select(1000);
                    Set<SelectionKey> selectionKeys=selector.selectedKeys();
                    Iterator<SelectionKey> it=selectionKeys.iterator();
                    SelectionKey selectionKey=null;
                    while (it.hasNext()){
                        selectionKey=it.next();
                        it.remove();
                        try{
                            handleInput(selectionKey);
                        }catch (Exception e){
                            if (selectionKey!=null){
                                selectionKey.cancel();
                                if (selectionKey.channel()!=null){
                                    selectionKey.channel().close();
                                }
                            }
                        }
                    }
                } catch (Throwable e) {
                    e.printStackTrace();
                }
            }

            if (selector!=null){
                try {
                    selector.close();
                } catch (IOException e) {

                }
            }
        }

        private void handleInput(SelectionKey selectionKey) throws IOException {
            if (selectionKey.isValid()){
                if (selectionKey.isAcceptable()){
                    ServerSocketChannel ssc= (ServerSocketChannel) selectionKey.channel();
                    SocketChannel socketChannel=ssc.accept();
                    socketChannel.configureBlocking(false);
                    socketChannel.register(selector,SelectionKey.OP_READ);
                }
                if (selectionKey.isReadable()){
                    SocketChannel sc= (SocketChannel) selectionKey.channel();
                    ByteBuffer readBuffer=ByteBuffer.allocate(1024);
                    int readBytes=sc.read(readBuffer);
                    if (readBytes>0){
                        readBuffer.flip();
                        byte[] bytes=new byte[readBuffer.remaining()];
                        readBuffer.get(bytes);
                        String body=new String(bytes,"UTF-8");
                        System.out.println("The time server receiver order : "+body);
                        String currentTime="QUERY TIME ORDER".equalsIgnoreCase(body)?new Date(System.currentTimeMillis()).toString():" BAD ORDER";
                        doWrite(sc,currentTime);
                    }else if(readBytes<0){
                        selectionKey.cancel();
                        sc.close();
                    }else{
                        ;
                    }
                }
            }
        }

        private void doWrite(SocketChannel sc, String response) throws IOException {
            if (response!=null&&response.trim().length()>0){
                byte[] bytes=response.getBytes();
                ByteBuffer writeBuffer=ByteBuffer.allocate(bytes.length);
                writeBuffer.put(bytes);
                writeBuffer.flip();
                sc.write(writeBuffer);
            }
        }


    }

}
