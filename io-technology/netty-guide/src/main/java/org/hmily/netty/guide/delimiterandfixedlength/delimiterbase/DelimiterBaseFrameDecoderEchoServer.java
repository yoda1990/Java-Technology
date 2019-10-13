package org.hmily.netty.guide.delimiterandfixedlength.delimiterbase;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;


public class DelimiterBaseFrameDecoderEchoServer {
    public void bind(int port) throws InterruptedException {
        // 配置服务端NIO线程
        //
        EventLoopGroup bossGroup=new NioEventLoopGroup();
        EventLoopGroup workerGroup=new NioEventLoopGroup();
        try{
            ServerBootstrap b=new ServerBootstrap();
            b.group(bossGroup,workerGroup).channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG,1024)
                    .handler(new LoggingHandler(LogLevel.INFO))
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            ByteBuf delimiter=Unpooled.copiedBuffer("$_".getBytes());
                            socketChannel.pipeline().addLast(new DelimiterBasedFrameDecoder(1024,delimiter));
                            socketChannel.pipeline().addLast(new StringDecoder());
                            socketChannel.pipeline().addLast(new EchoServerHandler());
                        }
                    });
            //绑定端口，同步等待成功
            ChannelFuture channelFuture=b.bind(port).sync();
            //等待服务端监听端口关闭
            channelFuture.channel().closeFuture().sync();
        }finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }


    }

    public static class EchoServerHandler extends ChannelHandlerAdapter {

        private int counter=0;

        public void channelRead(ChannelHandlerContext ctx,Object msg) throws Exception {
            String body= (String) msg;
            System.out.println("The time server receive client : "+body+" ;the counter is : "+ ++counter);
            body+="$_";
            //String currentTime="QUERY TIME ORDER".equalsIgnoreCase(body)?new Date(System.currentTimeMillis()).toString():"BAD ORDER";
            ByteBuf resp= Unpooled.copiedBuffer(body.getBytes());
            ctx.writeAndFlush(resp);
        }

        public void channelReadComplete(ChannelHandlerContext ctx)throws Exception{
            ctx.flush();
        }

        public void exceptionCaught(ChannelHandlerContext ctx,Throwable cause){
            cause.printStackTrace();
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
        new DelimiterBaseFrameDecoderEchoServer().bind(port);
    }

}
