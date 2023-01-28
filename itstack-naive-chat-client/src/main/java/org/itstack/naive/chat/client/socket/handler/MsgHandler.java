package org.itstack.naive.chat.client.socket.handler;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import javafx.application.Platform;
import org.itstack.naive.chat.client.application.UIService;
import org.itstack.naive.chat.client.socket.MyBizHandler;
import org.itstack.naive.chat.protocol.msg.MsgGroupResponse;
import org.itstack.naive.chat.protocol.msg.MsgResponse;
import org.itstack.navice.chat.ui.view.chat.IChatMethod;

/**
 * TODO
 *
 * @author hourui
 * @version 1.0
 * @Description
 * @date 2023/1/26 22:47
 */
public class MsgHandler extends SimpleChannelInboundHandler<MsgResponse> {
    private UIService uiService;

    public MsgHandler(UIService uiService) {
        this.uiService = uiService;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MsgResponse msg) throws Exception {
        IChatMethod chat = uiService.getChat();
        Platform.runLater(() -> {
            //填充对方的消息, 设置对话框填充到第一个，设置消息提醒，但是并不选中
            chat.addTalkMsgUserLeft(msg.getFriendId(), msg.getMsgText(), msg.getMsgType(), msg.getMsgDate(), true, false, true);
        });
    }
}
