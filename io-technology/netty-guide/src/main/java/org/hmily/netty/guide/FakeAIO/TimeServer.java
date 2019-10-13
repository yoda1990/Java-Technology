package org.hmily.netty.guide.FakeAIO;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.*;


/**
 * 伪异步 IO 的 服务端代码
 */
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
            Socket socket=null;
            serverSocket=new ServerSocket(port);
            System.out.println("The time server is start in port :"+port);
            TimeServerHandlerExecutePool singleExecutor=new TimeServerHandlerExecutePool(50,10000);
            while (true){
                socket=serverSocket.accept();
                singleExecutor.execute(new org.hmily.netty.guide.BIO.TimeServer.TimeServerHandler(socket));
            }
        }finally {
            if (serverSocket!=null){
                System.out.println("The time server close");
                serverSocket.close();
                serverSocket=null;
            }
        }
    }

    public static class TimeServerHandlerExecutePool{

        private ExecutorService executorService;

        public TimeServerHandlerExecutePool(int maxPoolSize,int queueSize) {
            this.executorService = new ThreadPoolExecutor(Runtime.getRuntime().availableProcessors(),
                                                          maxPoolSize,
                                          120L, TimeUnit.SECONDS,
                                                          new ArrayBlockingQueue<Runnable>(queueSize));
        }

        public void execute(Runnable task){
            executorService.execute(task);
        }


    }



}
