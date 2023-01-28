package org.itstack.naive.chat.client;

import io.netty.channel.Channel;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;
import org.itstack.naive.chat.client.application.UIService;
import org.itstack.naive.chat.client.event.ChatEvent;
import org.itstack.naive.chat.client.event.LoginEvent;
import org.itstack.naive.chat.client.infrastructure.util.CacheUtil;
import org.itstack.naive.chat.client.socket.NettyClient;
import org.itstack.naive.chat.protocol.login.ReconnectRequest;
import org.itstack.navice.chat.ui.view.chat.ChatController;
import org.itstack.navice.chat.ui.view.chat.IChatMethod;
import org.itstack.navice.chat.ui.view.login.ILoginMethod;
import org.itstack.navice.chat.ui.view.login.LoginController;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 微信公众号：bugstack虫洞栈 | 欢迎关注学习专题案例
 * 论坛：http://bugstack.cn
 * Create by 小傅哥 on @2020
 */
@Slf4j
public class Application extends javafx.application.Application {

    //默认线程池,为了启动netty客户端
    private static ExecutorService executorService = Executors.newFixedThreadPool(2);

    private ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor(new ThreadFactory() {
        @Override
        public Thread newThread(Runnable r) {
            AtomicInteger number = new AtomicInteger(0);
            Thread thread = new Thread(r);
            thread.setName("hourui-" + number.getAndIncrement());
            return thread;
        }
    });

    @Override
    public void start(Stage primaryStage) throws Exception {
        // 1. 启动窗口,其中ChatEvent和LoginEvent都是客户端自己实现的类, 这样UI界面触发事件时就会执行客户端定义的逻辑
        IChatMethod chat = new ChatController(new ChatEvent());
        ILoginMethod login = new LoginController(new LoginEvent(), chat); //登陆成功以后显示chat界面
        //然后客户端可以调用IChatMethod中定义的方法，将服务端返回的数据填充到框体中
        login.doShow();

        UIService uiService = new UIService();
        uiService.setChat(chat);
        uiService.setLogin(login);

        // 2. 启动socket连接
        log.info("NettyClient连接服务开始 inetHost：{} inetPort：{}", "127.0.0.1", 7397);
        //nettyClient就是一个Callable任务，返回SocketChannel对象
        NettyClient nettyClient = new NettyClient(uiService);
        Future<Channel> future = executorService.submit(nettyClient);
        Channel channel = future.get();
        if (null == channel) throw new RuntimeException("netty client start error channel is null");

        while (!nettyClient.isActive()) {
            log.info("NettyClient启动服务 ...");
            Thread.sleep(500);
        }
        log.info("NettyClient连接服务完成 {}", channel.localAddress());
        // Channel状态定时巡检；3秒后每5秒执行一次
        scheduledExecutorService.scheduleAtFixedRate(() -> {
            while (!nettyClient.isActive()){
                log.info("通信管道巡检：通信管道状态:{}", nettyClient.isActive());
                try{
                    log.info("通信管道巡检：断线重连[Begin]");
                    Channel freshChannel = executorService.submit(nettyClient).get();
                    //客户端发送登陆请求时，就会将userId缓存起来
                    if(null == CacheUtil.userId) continue;
                    log.info("缓存的userId:" + CacheUtil.userId);
                    freshChannel.writeAndFlush(new ReconnectRequest(CacheUtil.userId));
                } catch (Exception e) {
                    log.info("通信管道巡检：断线重连[Error]");
                }
            }
        }, 2, 5, TimeUnit.SECONDS);

    }

    public static void main(String[] args) {
        launch(args);
    }

}
