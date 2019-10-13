package org.hmily.netty.guide.linebasedframedecoder;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;

import java.util.logging.Logger;

public class TimeClient {

    public void connect(int port,String host){
        EventLoopGroup group=new NioEventLoopGroup();
        try{
            Bootstrap b=new Bootstrap();
            b.group(group).channel(NioSocketChannel.class).
                    option(ChannelOption.TCP_NODELAY,true)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) {
                            socketChannel.pipeline().addLast(new LineBasedFrameDecoder(1024));
                            socketChannel.pipeline().addLast(new StringDecoder());
                            socketChannel.pipeline().addLast(new TimeClientHandler());
                        }
                    });
            //发起异步链接操作
            ChannelFuture f=b.connect(host,port).sync();
            //等待客户端链路关闭
            f.channel().closeFuture().sync();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            //优雅退出，释放NIO线程组
            group.shutdownGracefully();
        }
    }

    public static void main(String[] args) {
        int port=8080;
        if (args!=null&&args.length>0){
            try{
                port= Integer.valueOf(args[0]);
            }catch (NumberFormatException e){
                //采用默认值
            }
        }
        new TimeClient().connect(port,"127.0.0.1");


    }

    public static class TimeClientHandler extends ChannelHandlerAdapter {

        private static final Logger logger= Logger.getLogger(TimeClientHandler.class.getName());

        private int counter;

        private byte[] req;

        public TimeClientHandler() {
            req=("QUERY TIME ORDER".getBytes()+System.getProperty("line.separator")).getBytes();
        }

        public void channelActive(ChannelHandlerContext ctx)throws Exception{
            ByteBuf message=null;
            for (int i=0;i<100;i++){
                message=Unpooled.buffer(req.length);
                message.writeBytes(req);
                ctx.writeAndFlush(message);
            }
        }

        public void channelRead(ChannelHandlerContext ctx,Object msg)throws Exception{
            String body= (String) msg;
            System.out.println("NOW IS : "+body+"; the counter is : "+ ++counter);

        }

        public void exceptionCaught(ChannelHandlerContext ctx,Throwable cause){
            //释放资源
            logger.warning("Unexpected exception from downstream : "+cause.getMessage());
            ctx.close();
        }
    }
}
