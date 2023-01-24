package org.itstack.naive.chat.socket.handler;

import io.netty.channel.Channel;
import org.itstack.naive.chat.application.UserService;
import org.itstack.naive.chat.protocol.talk.DelTalkRequest;
import org.itstack.naive.chat.socket.MyBizHandler;

/**
 * 处理删除对话框请求的处理器
 * @author hourui
 * @version 1.0
 * @Description
 * @date 2023/1/24 12:41
 */
public class DelTalkHandler extends MyBizHandler<DelTalkRequest> {
    public DelTalkHandler(UserService userService) {
        super(userService);
    }

    @Override
    protected void channelRead(Channel channel, DelTalkRequest msg) {
        userService.deleteTalkBox(msg.getUserId(), msg.getTalkId());
    }
}
