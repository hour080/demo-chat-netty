package org.itstack.naive.chat.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.itstack.naive.chat.util.MsgUtil;

/**
 * TODO
 *
 * @author hourui
 * @version 1.0
 * @Description
 * @date 2023/1/11 20:45
 */
public class NettyClient {
    public static void main(String[] args) {
        EventLoopGroup worker = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(worker);
            bootstrap.channel(NioSocketChannel.class);
            bootstrap.option(ChannelOption.AUTO_READ, true); //该值为True时，每次读操作完毕后会自动调用channel.read()，从而有数据到达便能读取；
            bootstrap.handler(new MyChannelInitializer());
            ChannelFuture f = bootstrap.connect("127.0.0.1", 7397).sync();
            System.out.println("itstack-demo-netty client start done.");
            f.channel().writeAndFlush(MsgUtil.buildMsg(f.channel().id().toString(),"你好，使用 protobuf 通信格式的服务端，我是 https://bugstack.cn"));
            f.channel().writeAndFlush(MsgUtil.buildMsg(f.channel().id().toString(),"你好，使用 protobuf 通信格式的服务端，我是 https://bugstack.cn"));
            f.channel().writeAndFlush(MsgUtil.buildMsg(f.channel().id().toString(),"你好，使用 protobuf 通信格式的服务端，我是 https://bugstack.cn"));
            f.channel().writeAndFlush(MsgUtil.buildMsg(f.channel().id().toString(),"你好，使用 protobuf 通信格式的服务端，我是 https://bugstack.cn"));
            f.channel().writeAndFlush(MsgUtil.buildMsg(f.channel().id().toString(),"你好，使用 protobuf 通信格式的服务端，我是 https://bugstack.cn"));
            f.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            worker.shutdownGracefully();
        }



    }
}
