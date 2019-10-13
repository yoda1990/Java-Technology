package org.hmily.netty.guide.netty_nio;


import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import java.util.Date;


public class TimeServer {

    public void bind(int port) throws InterruptedException {
        EventLoopGroup bossLoopGroup=new NioEventLoopGroup();
        EventLoopGroup workerLoopGroup=new NioEventLoopGroup();
        ServerBootstrap b =new ServerBootstrap();
        try{
            b.group(bossLoopGroup,workerLoopGroup).channel(NioServerSocketChannel.class).
                    option(ChannelOption.SO_BACKLOG,1024).childHandler(new ChildChannelHandler());
            //绑定端口，同步等待成功
            ChannelFuture f=b.bind(port).sync();
            //等待服务端，释放线程池资源
            f.channel().closeFuture().sync();
        }finally{
            bossLoopGroup.shutdownGracefully();
            workerLoopGroup.shutdownGracefully();
        }
    }

    private static class ChildChannelHandler extends ChannelInitializer<SocketChannel>{

        protected void initChannel(SocketChannel socketChannel) throws Exception {
            socketChannel.pipeline().addLast(new TimeServerHandler());
        }
    }

    public static class TimeServerHandler extends ChannelHandlerAdapter{

        public void channelRead(ChannelHandlerContext ctx,Object msg) throws Exception {
            ByteBuf buf= (ByteBuf) msg;
            byte[] req=new byte[buf.readableBytes()];
            buf.readBytes(req);
            String body=new String(req,"UTF-8");
            System.out.println("The time server receive order : "+body);
            String currentTime="QUERY TIME ORDER".equalsIgnoreCase(body)?new Date(System.currentTimeMillis()).toString():"BAD ORDER";
            ByteBuf resp= Unpooled.copiedBuffer(currentTime.getBytes());
            ctx.write(resp);
        }

        public void channelReadComplete(ChannelHandlerContext ctx)throws Exception{
            ctx.flush();
        }

        public void exceptionCaught(ChannelHandlerContext ctx,Throwable cause){
            ctx.close();
        }

    }

    public static void main(String[] args) throws Exception {
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
