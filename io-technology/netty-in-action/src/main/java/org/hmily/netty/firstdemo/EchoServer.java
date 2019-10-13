package org.hmily.netty.firstdemo;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.nio.ByteBuffer;

public class EchoServer {

    private final int port;

    public EchoServer(int port) {
        this.port = port;
    }

    public void start() throws InterruptedException {
        EventLoopGroup eventExecutors=new NioEventLoopGroup();
        try{
            ServerBootstrap b=new ServerBootstrap();
            b.group(eventExecutors).channel(NioServerSocketChannel.class).localAddress(port).childHandler(new ChannelInitializer<Channel>() {
                protected void initChannel(Channel channel) throws Exception {
                    channel.pipeline().addLast(new EchoServerHandler());
                }
            });

            ChannelFuture channelFuture=b.bind().sync();
            System.out.println(EchoServer.class.getName()+"Started and listen on "+channelFuture.channel().localAddress());
            channelFuture.channel().closeFuture().sync();
        }finally {
            eventExecutors.shutdownGracefully().sync();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        new EchoServer(65535).start();
    }

    @ChannelHandler.Sharable
    public static class EchoServerHandler extends ChannelInboundHandlerAdapter {

        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg) {
            ByteBuffer in = (ByteBuffer) msg;
            System.out.println(
                    "Server received: " + in.toString());//将消息记录到控制台

            ctx.write(in); //将接收到的消息写给发送者，而不冲刷出站消息

        }

        @Override
        public void channelReadComplete(ChannelHandlerContext ctx) {
            ctx.writeAndFlush(Unpooled.EMPTY_BUFFER)
                    .addListener(ChannelFutureListener.CLOSE); //将未决消息冲刷到远程节点，并且关闭该Channel
        }

        @Override
        public void exceptionCaught(ChannelHandlerContext ctx,
                                    Throwable cause) {
            cause.printStackTrace();//打印异常栈跟踪
            ctx.close();//关闭该Channel
        }


    }

}
