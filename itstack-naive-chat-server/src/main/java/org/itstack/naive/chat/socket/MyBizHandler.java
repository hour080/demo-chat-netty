package org.itstack.naive.chat.socket;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.itstack.naive.chat.application.UserService;
import org.itstack.naive.chat.infrastructure.common.SocketChannelUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * SimpleChannelInboundHandler只会处理一种类型的消息
 * channelRead 是public 类型，可以被外部访问；
 * 而channelRead0是protected类型，只能被当前类及其子类访问。
 * channelRead中调用了channelRead0
 * channelRead额外多做了acceptInboundMessage,也就是检查给定消息是否应该被当前的入站处理器处理
 */
public abstract class MyBizHandler<T> extends SimpleChannelInboundHandler<T> {

    protected Logger logger = LoggerFactory.getLogger(MyBizHandler.class);

    protected UserService userService;

    public MyBizHandler(UserService userService) {
        this.userService = userService;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, T msg) throws Exception {
        channelRead(ctx.channel(), msg); //执行具体子类的channelRead方法
    }

    protected abstract void channelRead(Channel channel, T msg);

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
        logger.info("客户端连接通知：{}", ctx.channel());
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        super.channelInactive(ctx);
        //根据channelId获得userId，然后移除channel
        SocketChannelUtil.removeChannelByChannelId(ctx.channel().id().toString());
        //然后遍历每一个群组，删除其中的用户channel
        SocketChannelUtil.removeChannelGroupByChannel(ctx.channel());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        logger.error("服务端异常断开,异常类型为{},异常消息为{}", cause.getClass(),cause.getMessage());
        SocketChannelUtil.removeChannelByChannelId(ctx.channel().id().toString());
        SocketChannelUtil.removeChannelGroupByChannel(ctx.channel());
    }

}
