package org.itstack.naive.chat.client.event;

import io.netty.channel.Channel;
import org.itstack.naive.chat.client.infrastructure.util.BeanUtil;
import org.itstack.naive.chat.client.infrastructure.util.CacheUtil;
import org.itstack.naive.chat.protocol.login.LoginRequest;
import org.itstack.navice.chat.ui.view.login.ILoginEvent;

/**
 * 事件实现；登陆窗口
 */
public class LoginEvent implements ILoginEvent {

    @Override
    public void doLoginCheck(String userId, String userPassword) {
        Channel channel = BeanUtil.getBean("channel", Channel.class);
        channel.writeAndFlush(new LoginRequest(userId, userPassword));
        CacheUtil.userId = userId;
    }

}
