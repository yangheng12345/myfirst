package netty.test1;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;
@ChannelHandler.Sharable
public class EchoClientHandler extends SimpleChannelInboundHandler<ByteBuf> {

        @Override
        public void channelActive(ChannelHandlerContext ctx) {
            System.out.println("hello");
            ctx.writeAndFlush(Unpooled.copiedBuffer("Netty rocks!ooooooooooooooooooooooooooooooooooooo",
                    CharsetUtil.UTF_8));
        }
        @Override
        public void channelRead0(ChannelHandlerContext ctx, ByteBuf in) {
            System.out.println(
                    "Client received: " + in.toString(CharsetUtil.UTF_8));
            System.out.println(System.currentTimeMillis());
            System.out.println("Client is complection");
        }
        @Override
        public void exceptionCaught(ChannelHandlerContext ctx,
                                    Throwable cause) {
            cause.printStackTrace();
            ctx.close();
        }
}
