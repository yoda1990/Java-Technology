package org.hmily.netty.guide.messagepack;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

public class EchoClient {

    private final String host;

    private final int port;

    private final int sendNumber;

    public EchoClient(String host, int port, int sendNumber) {
        this.host = host;
        this.port = port;
        this.sendNumber = sendNumber;
    }

    public void run() {
        // configure the client
        EventLoopGroup group = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(group).channel(NioSocketChannel.class)
                .option(ChannelOption.TCP_NODELAY, true)
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 3000)
                .handler(new ChannelInitializer<SocketChannel>() {
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        socketChannel.pipeline().addLast("msgpack decoder", new MsgpackDecoder());
                        socketChannel.pipeline().addLast("msgpack encoder", new MsgpackEncoder());
                        socketChannel.pipeline().addLast(new EchoClientHandler(sendNumber));
                    }
                });
    }

    public static class EchoClientHandler extends ChannelHandlerAdapter {
        private final int sendNumber;

        public EchoClientHandler(int sendNumber) {
            this.sendNumber = sendNumber;
        }

        public void channelActive(ChannelHandlerContext ctx){
            UserInfo[] userInfos=UserInfo(sendNumber);
            for (UserInfo userInfo:userInfos){
                ctx.write(userInfo);
            }
            ctx.flush();
        }

        public void channelRead(ChannelHandlerContext ctx,Object msg){
            System.out.printf("Client receive the msgpack message : %s",msg);
            ctx.flush();
        }

    }

    private static UserInfo[] UserInfo(int sendNumber){
        UserInfo[] userInfos=new UserInfo[sendNumber];
        UserInfo userInfo=null;
        for (int i=0;i<sendNumber;i++){
            userInfo=new UserInfo();
            userInfo.setAge(i);
            userInfo.setName("ABCDEFG---->"+i);
            userInfos[i]=userInfo;
        }
        return userInfos;
    }

    public static class UserInfo{
        private int age;
        private String name;

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    public static void main(String[] args) {
        new EchoClient("127.0.0.1",8080,10).run();
    }

}
