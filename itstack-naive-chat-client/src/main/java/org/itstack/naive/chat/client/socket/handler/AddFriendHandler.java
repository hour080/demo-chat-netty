package org.itstack.naive.chat.client.socket.handler;

import io.netty.channel.Channel;
import javafx.application.Platform;
import lombok.extern.slf4j.Slf4j;
import org.itstack.naive.chat.client.application.UIService;
import org.itstack.naive.chat.client.socket.MyBizHandler;
import org.itstack.naive.chat.protocol.friend.AddFriendResponse;
import org.itstack.navice.chat.ui.view.chat.IChatMethod;

/**
 * @author hourui
 * @version 1.0
 * @Description
 * @date 2023/1/19 22:46
 */
@Slf4j
public class AddFriendHandler extends MyBizHandler<AddFriendResponse> {
    private UIService uiService;

    public AddFriendHandler(UIService uiService){
        this.uiService = uiService;
    }
    @Override
    public void channelRead(Channel channel, AddFriendResponse msg) {
        IChatMethod chat = uiService.getChat();
        if(!msg.isSuccess()){
            System.out.println("添加好友失败");
            return;
        }
        Platform.runLater(() -> {
            log.info("添加好友处理");
            //将当前好友添加到好友列表userListView，并为发送消息按钮sendMsgButton添加事件
            chat.addFriendUser(true, msg.getFriendId(), msg.getFriendNickName(), msg.getFriendHead());
        });
    }
}
