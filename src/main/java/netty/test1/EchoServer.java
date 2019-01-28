package netty.test1;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.net.InetSocketAddress;

/**
 * 在Netty 4 中，所有的I/O操作和事件都由已经被分配给了
 EventLoop的那个Thread来处理 ②

 当出站事件触发了入站事件时，将会导致另一个负面影响。当 Channel.write()方法导
 致异常时，需要生成并触发一个 exceptionCaught 事件。但是在 Netty 3 的模型中，由于这是
 一个入站事件，需要在调用线程中执行代码，然后将事件移交给 I/O 线程去执行，然而这将带来
 额外的上下文切换。
 Netty 4 中所采用的线程模型，通过在同一个线程中处理某个给定的 EventLoop 中所产生的
 所有事件，解决了这个问题。这提供了一个更加简单的执行体系架构，并且消除了在多个
 ChannelHandler 中进行同步的需要（除了任何可能需要在多个 Channel 中共享的）。
 */
public class EchoServer {

    private final int port;

    public EchoServer(int port) {
        this.port = port;
    }

    public static void main(String[] args) throws Exception {
//        if (args.length != 1) {
//            System.err.println(
//                    "Usage: " + EchoServer.class.getSimpleName() +
//                            " <port>");
//        }
        int port = 5555;
        new EchoServer(port).start();
    }

    public void start() throws Exception {
        final EchoServerHandler serverHandler = new EchoServerHandler();
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap();
//            b.group(group,group);
            ServerBootstrap option = b.group(group)
                    .channel(NioServerSocketChannel.class)
                    .localAddress(new InetSocketAddress(port))
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        public void initChannel(SocketChannel ch)
                                throws Exception {
                            ch.pipeline().addLast(serverHandler);
                        }
                    }).option(ChannelOption.SO_BACKLOG, 128)
                    .childOption(ChannelOption.SO_KEEPALIVE, true);
            ChannelFuture f = b.bind().sync();
            //异 常会逐步的往下传递，需要添加监听看是否需要获取
            f.addListener(new ChannelFutureListener() {
                @Override
                public void operationComplete(ChannelFuture future) throws Exception {
                    if(!future.isSuccess()){
                    }
                }
            });
            f.channel().closeFuture().sync();
        } finally {
            group.shutdownGracefully().sync();
        }
    }
}
