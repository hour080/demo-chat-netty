package org.itstack.naive.chat.client.socket;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 */
public abstract class MyBizHandler<T>  extends SimpleChannelInboundHandler<T> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, T msg) throws Exception {
        channelRead(ctx.channel(), msg);
    }

    public abstract void channelRead(Channel channel, T msg);

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        super.channelInactive(ctx);
        System.out.println(ctx.channel().localAddress() + "断开连接了");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println("出现异常:" + cause.getMessage() + "关闭id为" + ctx.channel().id() + "的通道");
    }

}
