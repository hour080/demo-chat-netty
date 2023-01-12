package org.itstack.naive.chat.client;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.itstack.naive.chat.codec.ObjDecoder;
import org.itstack.naive.chat.codec.ObjEncoder;
import org.itstack.naive.chat.domain.MsgInfo;

/**
 * TODO
 *
 * @author hourui
 * @version 1.0
 * @Description
 * @date 2023/1/11 20:45
 */
public class MyChannelInitializer extends ChannelInitializer<NioSocketChannel> {

    @Override
    protected void initChannel(NioSocketChannel ch) throws Exception {
        ch.pipeline().addLast(new ObjDecoder(MsgInfo.class)); //入站处理器
        ch.pipeline().addLast(new ObjEncoder(MsgInfo.class)); //出站处理器
        // 在管道中添加我们自己的接收数据实现方法
        ch.pipeline().addLast(new MyClientHandler());
    }
}
