package com.hmily.javabase.io.nio;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;


/**
 * ServerSocket 例子
 */
public class DemoServer extends Thread{

    private ServerSocket serverSocket;

    public int getPort(){
        return serverSocket.getLocalPort();
    }

    public void run(){
        try {
            serverSocket=new ServerSocket(0);
            while (true){
                Socket socket=serverSocket.accept();
                RequestHandler requestHndler=new RequestHandler(socket);
                requestHndler.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (serverSocket!=null){
                try {
                    serverSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args)throws IOException {
           DemoServer demoServer=new DemoServer();
           demoServer.start();
           try(Socket client=new Socket(InetAddress.getLocalHost(),demoServer.getPort())){
               BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(client.getInputStream()));
               bufferedReader.lines().forEach(s -> System.out.println(s));
           }
    }

    class RequestHandler extends  Thread{
        private Socket socket;

        RequestHandler(Socket socket){
            this.socket=socket;
        }

        public void run(){
            try(PrintWriter out=new PrintWriter(socket.getOutputStream());){
                out.println("Hello world!");
                out.flush();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

}
