package org.itstack.naive.chat;

import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;
import org.itstack.naive.chat.infrastructure.common.SocketChannelUtil;
import org.itstack.naive.chat.socket.NettyServer;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * 微信公众号：bugstack虫洞栈 | 欢迎关注学习专题案例
 * 论坛：http://bugstack.cn
 * Create by 小傅哥 on @2019
 */
@SpringBootApplication
@MapperScan("org.itstack.naive.chat.infrastructure.dao")
@Slf4j
public class Application implements InitializingBean {
    @Autowired
    private NettyServer nettyServer;

    //指定springboot项目在外部容器的应用主启动类Application
//    @Override
//    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
//        return builder.sources(Application.class);
//    }

    public static void main(String[] args){
        SpringApplication.run(Application.class, args);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        log.info("NettyServer启动服务开始 port：7397");
        Future<Channel> future = Executors.newFixedThreadPool(2).submit(nettyServer);
        Channel channel = future.get(); //当前线程会阻塞得到NioServerSocketChannel, 监听客户端的连接
        if (null == channel) throw new RuntimeException("netty server start error channel is null");
        //如果channel断开
        while (!channel.isActive()) {
            log.info("NettyServer启动服务 ...");
            Thread.sleep(500);
        }
        log.info("NettyServer启动服务完成 {}", channel.localAddress()); //返回服务端的ip信息
    }
}
