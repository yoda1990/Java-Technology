#Netty权威指南
##一、
##二、
##三、
##四、
## 五、分割符和定长解码器的应用
##### TCP以流的方式进行数据传输，上层的应用协议为了对消息进行区分，往往采用如下4中方式
  1.消息长度固定，累计读取到长度总和为定长LEN的报文后，就认为读取到了一个完整的信息；将计数器置位，重新开始读取下一个数据报
  2.将回车换行符作为消息结束符，例如FTP协议，这种方式在文本协议中应用比较广泛
  3.将特殊的分隔符作为消息的结束标志，回车换行符就是一种特殊的结束分隔符
  4.通过在消息头中定义长度字段来标识消息的总长度
##### LineBasedFrameDecoder 可以解决TCP的粘包问题
##### DelimiterBaseFrameDecoder 可以自动完成以分隔符做结束标志的消息解码
##### FixedLengthFrameDecoder 可以自动完成对定长消息的解码
##### DelimiterBaseFrameDecoder 应用例子
DelimiterBaseFrameDecoderEchoServer.java

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

DelimiterBaseFrameDecoderEchoClient.java

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
    
##### FixedLengthFrameDecoder 应用例子
FixedLengthFrameDecoderEchoServer.java

    public class FixedLengthFrameDecoderEchoServer {
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
                                socketChannel.pipeline().addLast(new FixedLengthFrameDecoder(20));
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
                System.out.printf("Receive client : [ %s ] \n",msg);
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
            new FixedLengthFrameDecoderEchoServer().bind(port);
        }
    
    }
## 五、编解码技术
###1、Java序列化的缺点
    1、无法跨语言（最致命的问题）：流行框架都没有用到Java序列化
    2、序列化后码流太大：
    3、序列化性能太低
    
###2、判断编解码框架的优劣因素
    1、是否支持跨语言，支持的语言种类是否丰富
    2、编解码后的码流大小
    3、编解码的性能
    4、类库是否小巧，API使用是否方便
    5、使用者需要手工开发的工作量和难度
    
###3、业界主流的编解码框架
    1、Protobuf
    它将数据结构以.proto文件进行描述，将通过代码生成工具可以生成对应数据结构的POJO对象和Protobuf相关的方法和属性。
   
    特点: 1、结构化数据存储格式（XML,JSON等）
          2、高效的编码性能
          3、语言无关、平台无关、扩展性好
          4、官方支持Java、C++、Python三种语言
    Protobuf另一个比较吸引人的地方就是它的数据描述文件和代码生成机制，利用数据描述文件对数据结构进行说明的优点如下：
          1、文本化的数据结构描述语言，可以实现语言和平台无关，特别适合异构系统间的集成；
          2、通过标识字段顺序，可以实现协议的前向兼容；
          3、自动代码生成，不需要手工编写同样数据结构的C++和Java版本；
          4、方便后续的管理和维护。相比于代码，结构化的文档更容易管理和维护。
    2、Thrift
    Thrift主要由5部分组成：
       （1）、语言系统及IDL编译器：负责由用户给定的IDL文件生成相应语言的接口代码；
       （2）、TProtocol:RPC的协议层，可以选择多种不同的对象序列化方式，如JSON和Binary；
       （3）、TTransport:RPC的传输层，同样可以选择不同的传输层实现，如socket、NIO、MemoryBuffer等；
       （4）、TProcessor:作为协议层和用户提供的服务实现之间的纽带，负责调用服务实现的接口；
       （5）、Tserver：聚合TProtocol、TTransport和TProcessor等对象
    Thrift支持三种比较典型的编解码方式。
       （1）、通用的二进制编解码；
       （2）、压缩二进制编解码；
       （3）、优化的可选字段压缩编解码。
       
    3、JBoss Marshalling 
    JBoss Marshalling 是一个Java对象的序列化API包，修正了JDK自带序列化包的很多问题，但又保持跟java.io.Serializable接口的兼容。同时，增加了一些可调的参数和附加的特性，并且这些参数和特性通过工厂类进行配置。
    相比于传统的Java序列化机制，它的优点如下：
       （1）、可插拔的类解析器，提供更加便捷的类加载定制策略，通过一个接口即可实现定制；
       （2）、可插拔的对象替换技术，不需要通过继承的方式；
       （3）、可插拔的预定义类缓存表，可以减小序列化的字节数组长度，提升常用类型的对象序列化性能；
       （4）、无须实现java.io.Serializable接口，即可实现Java序列化
       （5）、通过缓存技术提升对象的序列化性能。
          
## 七、MessagePack编解码
###MessagePack
   MessagePack是一个高效的二进制序列化框架，它像JSON一样支持不同语言间的数据交换，但是它的性能更快，序列化之后的码流也更小。
####1、MessagePack介绍
    MessagePack的特点如下：
       （1）、编解码高效，性能高；
       （2）、序列化之后的码流小
       （3）、支持跨语言
    
    maven代码：
    <!-- https://mvnrepository.com/artifact/org.msgpack/msgpack -->
    <dependency>
        <groupId>org.msgpack</groupId>
        <artifactId>msgpack</artifactId>
        <version>0.6.12</version>
    </dependency>
####2、MessagePack编码器和解码器开发
    MessagePack编码器开发
    public class MsgpackEncoder extends MessageToByteEncoder<Object> {
    
        @Override
        protected void encode(ChannelHandlerContext channelHandlerContext, Object o, ByteBuf byteBuf) throws Exception {
            MessagePack msgpack=new MessagePack();
            // Serialize
            byte[] raw=msgpack.write(o);
            byteBuf.writeBytes(raw);
        }
    }
    
    MessagePack解码器开发
    public class MsgpackDecoder extends MessageToMessageDecoder<ByteBuf> {
    
        protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
            final byte[] array;
            final int length=byteBuf.readableBytes();
            array=new byte[length];
            byteBuf.getBytes(byteBuf.readerIndex(),array,0,length);
            MessagePack messagePack=new MessagePack();
            list.add(messagePack.read(array));
            
        }
    }
    
    功能测试：
    客户端代码：
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
    
    