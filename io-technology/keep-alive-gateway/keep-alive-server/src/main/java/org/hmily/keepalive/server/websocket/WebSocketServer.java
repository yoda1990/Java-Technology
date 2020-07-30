package org.hmily.keepalive.server.websocket;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;


@Slf4j
@Component
public class WebSocketServer {

    EventLoopGroup bossGroup = new NioEventLoopGroup();
    EventLoopGroup workerGroup = new NioEventLoopGroup();

    volatile int localPort;

    public void setPort(int port){
        this.localPort = port;
    }

    public void start(){
        new Thread(()->{
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_REUSEADDR, true)
                    .childHandler(new WebSocketServerInitializer())
                    .childOption(ChannelOption.SO_REUSEADDR, true);
            for (int i = 0; i < 100; i++) {
                b.bind(++localPort).addListener(new ChannelFutureListener() {
                    public void operationComplete(ChannelFuture future) throws Exception {
                        //if ("true".equals(System.getProperty("netease.debug")))
                        System.out.println("端口绑定完成：" + future.channel().localAddress());
                    }
                });
            }
            // 端口绑定完成，启动消息随机推送(测试)
            // TestCenter.startTest();
            try {
                System.in.read();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).run();

    }


    // 关闭线程组
    public void shutDown(){
        bossGroup.shutdownGracefully();
        workerGroup.shutdownGracefully();
    }



}
