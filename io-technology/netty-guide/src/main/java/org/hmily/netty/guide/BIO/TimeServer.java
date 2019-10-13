package org.hmily.netty.guide.BIO;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

public class TimeServer {


    public static void main(String[] args) throws IOException {
        int port=8080;
        if (args!=null&&args.length>0){
            try{
                port= Integer.valueOf(args[0]);
            }catch (NumberFormatException e){
                //采用默认值
            }
        }
        ServerSocket serverSocket=null;

        try{
            serverSocket=new ServerSocket(port);
            System.out.println("The time server is start in port :"+port);
            Socket socket=null;
            while (true){
                socket=serverSocket.accept();
                new Thread(new TimeServerHandler(socket)).start();
            }
        }finally {
            if (serverSocket!=null){
                System.out.println("The time server close");
                serverSocket.close();
                serverSocket=null;
            }
        }
    }


    public static class TimeServerHandler implements Runnable{

        private Socket socket;

        public TimeServerHandler(Socket socket) {
            this.socket = socket;
        }

        public void run() {
            BufferedReader in=null;
            PrintWriter out=null;
            try {
                in=new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
                out=new PrintWriter(this.socket.getOutputStream(),true);
                String currentTime=null;
                String body=null;
                while (true){
                    body=in.readLine();
                    if (body==null){
                        break;
                    }
                    System.out.println("The time server receive order : "+body);
                    currentTime="QUERY TIME ORDER".equalsIgnoreCase(body)?new Date(System.currentTimeMillis()).toString() :"BAD ORDER";
                    out.println(currentTime);
                }
            } catch (IOException e) {
                if (in!=null){
                    try{
                        in.close();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
                if (out!=null){
                    out.close();
                    out=null;
                }
                if (this.socket!=null){
                    try {
                        this.socket.close();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                    this.socket=null;
                }
            }
        }
    }



}
