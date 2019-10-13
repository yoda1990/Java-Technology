package org.hmily.netty.guide.linebasedframedecoder;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;

import java.util.Date;

public class TimeServer {


    public void bind(int port) throws InterruptedException {
        // 配置服务端NIO线程
        //
        EventLoopGroup bossGroup=new NioEventLoopGroup();
        EventLoopGroup workerGroup=new NioEventLoopGroup();
       try{
           ServerBootstrap b=new ServerBootstrap();
           b.group(bossGroup,workerGroup).channel(NioServerSocketChannel.class)
                   .option(ChannelOption.SO_BACKLOG,1024).childHandler(new ChildChannelHandler());
           //绑定端口，同步等待成功
           ChannelFuture channelFuture=b.bind(port).sync();
           //等待服务端监听端口关闭
           channelFuture.channel().closeFuture().sync();
       }finally {
           bossGroup.shutdownGracefully();
           workerGroup.shutdownGracefully();
       }


    }


    private static class ChildChannelHandler extends ChannelInitializer<SocketChannel>{

        @Override
        protected void initChannel(SocketChannel socketChannel) throws Exception {
            socketChannel.pipeline().addLast(new LineBasedFrameDecoder(1024));
            socketChannel.pipeline().addLast(new StringDecoder());
            socketChannel.pipeline().addLast(new TimeServerHandler());
        }
    }

    public static class TimeServerHandler extends ChannelHandlerAdapter {

        private int counter;

        public void channelRead(ChannelHandlerContext ctx,Object msg) throws Exception {
            ByteBuf buf= (ByteBuf) msg;
            byte[] req=new byte[buf.readableBytes()];
            buf.readBytes(req);
            String body=new String(req,"UTF-8");
            System.out.println("The time server receive order : "+body+" ;the counter is : "+ ++counter);
            String currentTime="QUERY TIME ORDER".equalsIgnoreCase(body)?new Date(System.currentTimeMillis()).toString():"BAD ORDER";
            ByteBuf resp= Unpooled.copiedBuffer(currentTime.getBytes());
            ctx.writeAndFlush(resp);
        }

        public void channelReadComplete(ChannelHandlerContext ctx)throws Exception{
            ctx.flush();
        }

        public void exceptionCaught(ChannelHandlerContext ctx,Throwable cause){
            ctx.close();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        int port=8080;
        if (args!=null&&args.length>0){
            try{
                port= Integer.valueOf(args[0]);
            }catch (NumberFormatException e){
                //采用默认值
            }
        }
        new TimeServer().bind(port);
    }

}
