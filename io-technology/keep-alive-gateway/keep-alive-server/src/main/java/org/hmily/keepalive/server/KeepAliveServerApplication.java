package org.hmily.keepalive.server;

import io.netty.channel.ChannelFuture;
import org.hmily.keepalive.server.netty.NettyServer;
import org.hmily.keepalive.server.websocket.WebSocketServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class KeepAliveServerApplication implements CommandLineRunner {

    @Value("${netty.port}")
    private int port;
    @Value("${netty.url}")
    private String url;
    @Value("${websocket.port}")
    private int websocketPort;

    @Autowired
    private NettyServer echoServer;

    @Autowired
    private WebSocketServer webSocketServer;

    public static void main(String[] args) {
        SpringApplication.run(KeepAliveServerApplication.class, args);
    }


    public void run(String... args) throws Exception {
        ChannelFuture future = echoServer.start(url, port);
        webSocketServer.setPort(websocketPort);
        webSocketServer.start();
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                echoServer.destroy();
                webSocketServer.shutDown();
            }
        });
        //服务端管道关闭的监听器并同步阻塞,直到channel关闭,线程才会往下执行,结束进程
        future.channel().closeFuture().syncUninterruptibly();
    }
}