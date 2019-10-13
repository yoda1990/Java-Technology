//package org.hmily.netty.guide.AIO;
//
//import java.nio.ByteBuffer;
//import java.nio.channels.AsynchronousSocketChannel;
//import java.nio.channels.CompletionHandler;
//
//public class AcceptComletionHandler implements CompletionHandler<AsynchronousSocketChannel, TimeServer.AsyncTimeServerHandler> {
//
//    @Override
//    public void completed(AsynchronousSocketChannel result, TimeServer.AsyncTimeServerHandler attachment) {
//        attachment.asynchronousServerSocketChannel.accept(attachment,this);
//        ByteBuffer buffer=ByteBuffer.allocate(1024);
//        result.read(buffer,buffer,new ReadCompletionHandler(result));
//    }
//
//    @Override
//    public void failed(Throwable exc, TimeServer.AsyncTimeServerHandler attachment) {
//        exc.printStackTrace();
//        attachment.countDownLatch.countDown();
//    }
//}
