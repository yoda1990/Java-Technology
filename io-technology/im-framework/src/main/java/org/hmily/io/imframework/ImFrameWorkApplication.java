package org.hmily.io.imframework;


import io.netty.channel.ChannelFuture;
import org.hmily.io.imframework.server.NettyServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ImFrameWorkApplication implements CommandLineRunner {

    @Value("${netty.port}")
    private int port;
    @Value("${netty.url}")
    private String url;
    @Autowired
    private NettyServer echoServer;
    public static void main(String[] args) {
        SpringApplication.run(ImFrameWorkApplication.class, args);
    }


    public void run(String... args) throws Exception {
        ChannelFuture future = echoServer.start(url,port);
        Runtime.getRuntime().addShutdownHook(new Thread(){
            @Override
            public void run() {
                echoServer.destroy();
            }
        });
        //服务端管道关闭的监听器并同步阻塞,直到channel关闭,线程才会往下执行,结束进程
        future.channel().closeFuture().syncUninterruptibly();
    }

}
