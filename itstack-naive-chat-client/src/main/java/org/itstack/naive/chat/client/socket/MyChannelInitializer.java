package org.itstack.naive.chat.client.socket;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LoggingHandler;
import org.itstack.naive.chat.client.application.UIService;
import org.itstack.naive.chat.client.socket.handler.LoginHandler;
import org.itstack.naive.chat.codec.ObjDecoder;
import org.itstack.naive.chat.codec.ObjEncoder;

/**
 * 微信公众号：bugstack虫洞栈 | 欢迎关注学习专题案例
 * 论坛：http://bugstack.cn
 * Create by 小傅哥 on @2020
 */
public class MyChannelInitializer extends ChannelInitializer<NioSocketChannel> {
    private UIService uiService;

    public MyChannelInitializer(UIService uiService) {
        this.uiService = uiService;
    }

    @Override
    protected void initChannel(NioSocketChannel channel) throws Exception {
        // 对象传输处理[解码]
        channel.pipeline().addLast(new ObjDecoder());
        channel.pipeline().addLast(new LoginHandler(uiService));
        channel.pipeline().addLast(new ObjEncoder());
    }

}
