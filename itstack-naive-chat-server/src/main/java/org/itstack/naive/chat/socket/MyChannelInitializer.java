package org.itstack.naive.chat.socket;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.itstack.naive.chat.application.UserService;
import org.itstack.naive.chat.codec.ObjDecoder;
import org.itstack.naive.chat.codec.ObjEncoder;
import org.itstack.naive.chat.socket.handler.*;

/**
 */
public class MyChannelInitializer extends ChannelInitializer<NioSocketChannel> {
    private UserService userService;

    public MyChannelInitializer(UserService userService) {
        this.userService = userService;
    }

    @Override
    protected void initChannel(NioSocketChannel ch) throws Exception {
        ch.pipeline().addLast(new ObjDecoder()); //首先将字节数组解析为LoginRequest对象
        ch.pipeline().addLast(new LoginHandler(userService));
        ch.pipeline().addLast(new SearchFriendHandler(userService));
        ch.pipeline().addLast(new AddFriendHandler(userService));
        ch.pipeline().addLast(new TalkNoticeHandler(userService));
        ch.pipeline().addLast(new DelTalkHandler(userService));
        ch.pipeline().addLast(new MsgHandler(userService));
        ch.pipeline().addLast(new MsgGroupHandler(userService));
        ch.pipeline().addLast(new ReconnectHandler(userService));
        ch.pipeline().addLast(new ObjEncoder());

    }
}
