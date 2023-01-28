package org.itstack.naive.chat.client.socket.handler;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import javafx.application.Platform;
import lombok.extern.slf4j.Slf4j;
import org.itstack.naive.chat.client.application.UIService;
import org.itstack.naive.chat.client.socket.MyBizHandler;
import org.itstack.naive.chat.protocol.friend.SearchFriendResponse;
import org.itstack.naive.chat.protocol.friend.dto.UserDto;
import org.itstack.navice.chat.ui.view.chat.IChatMethod;

import java.util.List;

/**
 * TODO
 *
 * @author hourui
 * @version 1.0
 * @Description
 * @date 2023/1/19 22:30
 */
@Slf4j
public class SearchFriendHandler extends SimpleChannelInboundHandler<SearchFriendResponse> {
    private UIService uiService;

    public SearchFriendHandler(UIService uiService){
        this.uiService = uiService;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, SearchFriendResponse msg) throws Exception {
        List<UserDto> luckFriends = msg.getFriends();
        if (luckFriends == null || luckFriends.size() == 0) return;
        IChatMethod chat = uiService.getChat();
        Platform.runLater(() -> {
            log.info("搜索好友处理");
            for (UserDto luckFriend : luckFriends) {
                //addLuckFriend将从数据库查询到的好友添加到新的好友列表，并给好友的状态标签设置鼠标点击事件
                //addFriendLuck是点击新的好友，然后向服务端发送SearchFriendRequest请求
                chat.addLuckFriend(luckFriend.getUserId(), luckFriend.getUserNickName(), luckFriend.getUserHead(), luckFriend.getStatus());
            }
        });
    }
}
