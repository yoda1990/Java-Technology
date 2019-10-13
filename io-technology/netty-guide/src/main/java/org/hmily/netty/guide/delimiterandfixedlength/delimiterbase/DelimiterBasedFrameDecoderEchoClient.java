package org.hmily.netty.guide.delimiterandfixedlength.delimiterbase;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import org.hmily.netty.guide.linebasedframedecoder.TimeClient;

import java.util.logging.Logger;

public class DelimiterBasedFrameDecoderEchoClient {

    public void connect(int port,String host){
        EventLoopGroup group=new NioEventLoopGroup();
        try{
            Bootstrap b=new Bootstrap();
            b.group(group).channel(NioSocketChannel.class).
                    option(ChannelOption.TCP_NODELAY,true)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) {
                            ByteBuf delimiter=Unpooled.copiedBuffer("$_".getBytes());
                            socketChannel.pipeline().addLast(new DelimiterBasedFrameDecoder(1024,delimiter));
                            socketChannel.pipeline().addLast(new StringDecoder());
                            socketChannel.pipeline().addLast(new EchoClientHandler());
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
        new DelimiterBasedFrameDecoderEchoClient().connect(port,"127.0.0.1");


    }

    public static class EchoClientHandler extends ChannelHandlerAdapter {

        private static final Logger logger= Logger.getLogger(TimeClient.TimeClientHandler.class.getName());

        private int counter;

        static final String  ECHO_PEO="Hi,Hmily.Welcome to Netty .$_";

        public EchoClientHandler() {

        }

        public void channelActive(ChannelHandlerContext ctx)throws Exception{
            for (int i=0;i<10;i++){
                ctx.writeAndFlush(Unpooled.copiedBuffer(ECHO_PEO.getBytes()));
            }
        }

        public void channelRead(ChannelHandlerContext ctx,Object msg)throws Exception{
            String body= (String) msg;
            System.out.println("time receiver server : "+body+"; the counter is : "+ ++counter);

        }

        public void channelReadComplete(ChannelHandlerContext ctx)throws Exception{
            ctx.flush();
        }

        public void exceptionCaught(ChannelHandlerContext ctx,Throwable cause){
            //释放资源
            cause.printStackTrace();
            logger.warning("Unexpected exception from downstream : "+cause.getMessage());
            ctx.close();
        }
    }
}
