package org.itstack.naive.chat.client.socket.handler;

import com.alibaba.fastjson.JSON;
import io.netty.channel.Channel;
import javafx.application.Platform;
import lombok.extern.slf4j.Slf4j;
import org.itstack.naive.chat.client.application.UIService;
import org.itstack.naive.chat.client.socket.MyBizHandler;
import org.itstack.naive.chat.protocol.login.LoginResponse;
import org.itstack.navice.chat.ui.view.chat.IChatMethod;

/**
 * 客户端的登陆响应消息处理器
 * 在 JavaFx 中，如果在非Fx线程要执行Fx线程相关的任务，必须在 Platform.runlater 中执行，
 * 这个方法在JavaFX Application线程空闲时运行。
 * runLater本质上将您的Runnable放在队列中，当FX线程可用时，会从队列中取出任务来执行。
 * @author hourui
 * @version 1.0
 * @Description
 * @date 2023/1/16 23:32
 */
@Slf4j
public class LoginHandler extends MyBizHandler<LoginResponse> {
    private UIService uiService;

    public LoginHandler(UIService uiService) {
        this.uiService = uiService;
    }

    @Override
    public void channelRead(Channel channel, LoginResponse msg) {
        log.info("消息内容:" + JSON.toJSONString(msg));
        if(!msg.getIsSuccess()){
            System.out.println("登陆失败");
            return;
        }
        //登录成功那么则进行调用登录成功接口
        Platform.runLater(() -> {
            log.info("登陆处理");
            //uiService中的LoginController和chatController都是javafx的stage类的子类
            uiService.getLogin().doLoginSuccess(); // doLoginSuccess方法会调用chatController的show方法
            IChatMethod chat = uiService.getChat();
            //进行用户的基本信息设置。这里的基本信息也就是聊天窗体中，左侧头像框的信息
            chat.setUserInfo(msg.getUserId(), msg.getUserNickName(), msg.getUserHead());
        });
    }
}
