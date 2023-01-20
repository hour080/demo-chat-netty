package org.itstack.naive.chat.socket.handler;

import com.alibaba.fastjson.JSON;
import io.netty.channel.Channel;
import org.itstack.naive.chat.application.UserService;
import org.itstack.naive.chat.domain.user.model.UserInfo;
import org.itstack.naive.chat.infrastructure.common.SocketChannelUtil;
import org.itstack.naive.chat.protocol.login.LoginRequest;
import org.itstack.naive.chat.protocol.login.LoginResponse;
import org.itstack.naive.chat.socket.MyBizHandler;

/**
 * 处理客户端登陆请求的入站处理器
 * @author hourui
 * @version 1.0
 * @Description
 * @date 2023/1/16 22:29
 */
public class LoginHandler extends MyBizHandler<LoginRequest> {

    public LoginHandler(UserService userService){
        super(userService);
    }
    @Override
    protected void channelRead(Channel channel, LoginRequest msg) {
        logger.info("登陆请求处理：{}", JSON.toJSONString(msg));
        //进行登陆校验，返回登陆成功或者失败
        boolean auth = userService.checkAuth(msg.getUserId(), msg.getPassword());
        if(!auth){
            channel.writeAndFlush(new LoginResponse(false)); //向客户端返回登陆失败
            return;
        }
        //登陆成功
        //1.记录当前登陆用户和建立的channel之间的绑定关系
        //用户发送消息给其他用户时候，在服务端找到相应的接收消息的用户ID，获取相应的通信管道 Channel 后，进行消息发送
        SocketChannelUtil.addChannel(msg.getUserId(), channel);
        //2. 反馈消息；用户信息、用户对话框列表、好友列表、群组列表
        LoginResponse loginResponse = new LoginResponse();
        //2.1 用户信息
        UserInfo userInfo = userService.queryUserInfo(msg.getUserId());
        loginResponse.setIsSuccess(true);
        loginResponse.setUserId(userInfo.getUserId());
        loginResponse.setUserHead(userInfo.getUserHead());
        loginResponse.setUserNickName(userInfo.getUserNickName());
        channel.writeAndFlush(loginResponse);
    }
}
