package org.itstack.naive.chat.socket;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.extern.slf4j.Slf4j;
import org.itstack.naive.chat.application.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.InetSocketAddress;
import java.nio.channels.ServerSocketChannel;
import java.util.concurrent.Callable;

/**
  Netty服务器
 Socket 和ServerSocket 是一对 他们是java.net下面实现socket通信的类
 SocketChannel 和ServerSocketChannel是一对他们是java.nio下面实现通信的类,支持异步通信
 服务器必须先建立ServerSocket或者ServerSocketChannel 来等待客户端的连接
 客户端必须建立相对应的Socket或者SocketChannel来与服务器建立连接
 服务器接受到客户端的连接后，再生成一个Socket或者SocketChannel与此客户端通信
 也就是ServerSocket 和ServerSocketChannel一样，只不过是不同的框架下的对应实现
 */
@Slf4j
@Service
public class NettyServer implements Callable<Channel> {

    @Autowired
    private UserService userService;
    private final EventLoopGroup parent = new NioEventLoopGroup();
    private final EventLoopGroup worker = new NioEventLoopGroup();

    private Channel channel;

    @Override
    public Channel call() throws Exception {
        ChannelFuture channelFuture = null;
        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(parent, worker)
                    .channel(NioServerSocketChannel.class) //将serverSocketChannel设置为非阻塞模式
                    .option(ChannelOption.SO_BACKLOG, 128) //设置全连接队列的大小
                    .childHandler(new MyChannelInitializer(userService));
            channelFuture = serverBootstrap.bind(new InetSocketAddress(7397)).syncUninterruptibly();
            channel = channelFuture.channel();//NioServerSocketChannel
        } catch (Exception e) {
            log.error("socket server start error", e.getMessage());
        } finally {
            //如果成功和客户端之间建立连接
            if(channelFuture != null && channelFuture.isSuccess()){
                log.info("socket server start done. ");
            }else{
                log.error("socket server start error. ");
            }
        }
        return channel;
    }
    public void destroy() {
        if (null == channel) return;
        //关闭连接
        channel.close();
        parent.shutdownGracefully();
        worker.shutdownGracefully();
    }
    public Channel getChannel(){
        return channel;
    }
}
