package org.itstack.naive.chat.client.socket.handler;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import javafx.application.Platform;
import org.itstack.naive.chat.client.application.UIService;
import org.itstack.naive.chat.client.socket.MyBizHandler;
import org.itstack.naive.chat.protocol.talk.TalkNoticeResponse;
import org.itstack.navice.chat.ui.view.chat.IChatMethod;

/**
 * 好友接收到对话框信息后，在自己的对话框列表中添加对话框
 * @author hourui
 * @version 1.0
 * @Description
 * @date 2023/1/24 12:48
 */
public class TalkNoticeHandler extends SimpleChannelInboundHandler<TalkNoticeResponse> {
    private UIService uiService;

    public TalkNoticeHandler(UIService uiService) {
        this.uiService = uiService;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TalkNoticeResponse msg) throws Exception {
        IChatMethod chat = uiService.getChat();
        Platform.runLater(() -> {
            //在好友界面点击发送消息按钮以后，当前用户创建对话框
            //这里是其他用户创建对话框
            chat.addTalkBox(-1, 0, msg.getTalkId(), msg.getTalkName(), msg.getTalkHead(), msg.getTalkSketch(), msg.getTalkDate(), false);
        });
    }
}
