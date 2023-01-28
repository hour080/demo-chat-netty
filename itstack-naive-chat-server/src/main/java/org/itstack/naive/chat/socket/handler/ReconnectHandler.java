package org.itstack.naive.chat.socket.handler;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;
import org.itstack.naive.chat.application.UserService;
import org.itstack.naive.chat.infrastructure.common.SocketChannelUtil;
import org.itstack.naive.chat.protocol.login.ReconnectRequest;
import org.itstack.naive.chat.socket.MyBizHandler;

import java.util.List;

/**
 * 服务器处理断线重连
 * @author hourui
 * @version 1.0
 * @Description
 * @date 2023/1/28 09:32
 */
@Slf4j
public class ReconnectHandler extends SimpleChannelInboundHandler<ReconnectRequest> {
    private UserService userService;

    public ReconnectHandler(UserService userService) {
       this.userService = userService;
    }

    /**
     * 一旦客户端那边监控到和服务端断开了连接，则会不断定时和服务端建立连接
     * 一旦建立连接成功，就会发送ReconnectRequest请求，服务端针对请求进行处理
     * @param ctx
     * @param msg
     * @author hourui
     * @date 2023/1/28 09:40
     * @return void
     */

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ReconnectRequest msg) throws Exception {
        log.info("客户端断线重连处理。userId：{}", msg.getUserId());
        //删除用户本来的连接映射，增加新的连接映射
        Channel oldChannel = SocketChannelUtil.getChannel(msg.getUserId());
        SocketChannelUtil.removeChannelByUserId(msg.getUserId());
        SocketChannelUtil.addChannel(msg.getUserId(), ctx.channel());
        //添加群组的通信
        List<String> groupIdList = userService.queryUserGroupIdList(msg.getUserId());
        for (String groupId : groupIdList) {
            //删除旧的channel， 这里所有的客户端都会发送请求
            SocketChannelUtil.removeChannelGroup(groupId, oldChannel);
            SocketChannelUtil.addChannelGroup(groupId, ctx.channel());
        }
    }
}
