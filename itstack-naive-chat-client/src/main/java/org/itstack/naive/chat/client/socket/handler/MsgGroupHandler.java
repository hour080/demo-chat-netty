package org.itstack.naive.chat.client.socket.handler;

import io.netty.channel.Channel;
import javafx.application.Platform;
import org.itstack.naive.chat.client.application.UIService;
import org.itstack.naive.chat.client.socket.MyBizHandler;
import org.itstack.naive.chat.protocol.msg.MsgGroupResponse;
import org.itstack.navice.chat.ui.view.chat.IChatMethod;

/**
 * TODO
 *
 * @author hourui
 * @version 1.0
 * @Description
 * @date 2023/1/27 20:54
 */
public class MsgGroupHandler extends MyBizHandler<MsgGroupResponse> {
    private UIService uiService;

    public MsgGroupHandler(UIService uiService) {
        this.uiService = uiService;
    }

    @Override
    public void channelRead(Channel channel, MsgGroupResponse msg) {
        IChatMethod chat = uiService.getChat();
        Platform.runLater(() -> {
            //通过talkId获得群组对话框，然后在群组对话框中添加消息记录
            //如果msg.getUserId() 也就是接收群发消息的用户和当前登陆的用户的userId相同，那就无需添加消息记录
            chat.addTalkMsgGroupLeft(msg.getTalkId(), msg.getUserId(),
                    msg.getUserNickName(), msg.getUserHead(),
                    msg.getMsgText(), msg.getMsgType(),
                    msg.getMsgDate(), true, false, true);
        });
    }
}
