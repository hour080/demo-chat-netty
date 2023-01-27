package org.itstack.naive.chat.client;

import io.netty.channel.Channel;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;
import org.itstack.naive.chat.client.application.UIService;
import org.itstack.naive.chat.client.event.ChatEvent;
import org.itstack.naive.chat.client.event.LoginEvent;
import org.itstack.naive.chat.client.socket.NettyClient;
import org.itstack.navice.chat.ui.view.chat.ChatController;
import org.itstack.navice.chat.ui.view.chat.IChatMethod;
import org.itstack.navice.chat.ui.view.login.ILoginMethod;
import org.itstack.navice.chat.ui.view.login.LoginController;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;

/**
 * 微信公众号：bugstack虫洞栈 | 欢迎关注学习专题案例
 * 论坛：http://bugstack.cn
 * Create by 小傅哥 on @2020
 */
@Slf4j
public class Application extends javafx.application.Application {

    //默认线程池
    private static ExecutorService executorService = Executors.newFixedThreadPool(2);

    @Override
    public void start(Stage primaryStage) throws Exception {
        // 1. 启动窗口,其中ChatEvent和LoginEvent都是客户端自己实现的类, 这样UI界面触发事件时就会执行客户端定义的逻辑
        IChatMethod chat = new ChatController(new ChatEvent());
        ILoginMethod login = new LoginController(new LoginEvent(), chat); //登陆成功以后显示chat界面
        //c
        login.doShow();

        UIService uiService = new UIService();
        uiService.setChat(chat);
        uiService.setLogin(login);

        // 2. 启动socket连接
        log.info("NettyClient连接服务开始 inetHost：{} inetPort：{}", "127.0.0.1", 7397);
        NettyClient nettyClient = new NettyClient(uiService);
        Future<Channel> future = executorService.submit(nettyClient);
        Channel channel = future.get();
        if (null == channel) throw new RuntimeException("netty client start error channel is null");

        while (!nettyClient.isActive()) {
            log.info("NettyClient启动服务 ...");
            Thread.sleep(500);
        }
        log.info("NettyClient连接服务完成 {}", channel.localAddress());

    }

    public static void main(String[] args) {
        launch(args);
    }

}
