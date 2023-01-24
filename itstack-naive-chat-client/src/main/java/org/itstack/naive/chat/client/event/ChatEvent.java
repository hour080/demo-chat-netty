package org.itstack.naive.chat.client.event;

import io.netty.channel.Channel;
import javafx.scene.control.ListView;
import javafx.scene.layout.Pane;
import org.itstack.naive.chat.client.infrastructure.util.BeanUtil;
import org.itstack.naive.chat.protocol.friend.AddFriendRequest;
import org.itstack.naive.chat.protocol.friend.SearchFriendRequest;
import org.itstack.naive.chat.protocol.talk.DelTalkRequest;
import org.itstack.naive.chat.protocol.talk.TalkNoticeRequest;
import org.itstack.navice.chat.ui.view.chat.IChatEvent;

import java.util.Date;

/**
 */
public class ChatEvent implements IChatEvent {

    @Override
    public void doQuit() {

    }

    @Override
    public void doSendMsg(String userId, String talkId, Integer talkType, String msg, Integer msgType, Date msgDate) {

    }

    /**
     * 在好友栏中点击与好友对话按钮，触发的事件
     * 具体在ChatEventDefine.doEventOpenFriendUserSendMsg中
     * @param userId
     * @param userFriendId
     * @author hourui
     * @date 2023/1/23 22:10
     * @return void
     */
    @Override
    public void doEventAddTalkUser(String userId, String userFriendId) {
        Channel channel = BeanUtil.getBean("channel", Channel.class);
        channel.writeAndFlush(new TalkNoticeRequest(userId, userFriendId, 0));
    }


    @Override
    public void doEventAddTalkGroup(String userId, String groupId) {
        Channel channel = BeanUtil.getBean("channel", Channel.class);
        channel.writeAndFlush(new TalkNoticeRequest(userId, groupId, 1));
    }

    @Override
    public void doEventDelTalkUser(String userId, String talkId) {
        Channel channel = BeanUtil.getBean("channel", Channel.class);
        channel.writeAndFlush(new DelTalkRequest(userId, talkId));
    }


    /**
     * 点击新的朋友按钮触发该函数，右侧窗体初始化数据库中的用户列表信息
     * @param userId
     * @param listView
     * @author hourui
     * @date 2023/1/19 20:49
     * @return void
     */
    @Override
    public void addFriendLuck(String userId, ListView<Pane> listView) {
        Channel channel = BeanUtil.getBean("channel", Channel.class);
        //点击新的朋友按钮, 初始化右侧可添加的好友信息
        channel.writeAndFlush(new SearchFriendRequest(userId, ""));
    }

    /**
     * 在点击新的好友按钮出现的右侧界面中，搜索框按下回车键触发该函数
     * @param userId
     * @param text
     * @author hourui
     * @date 2023/1/19 22:44
     * @return void
     */
    @Override
    public void doFriendLuckSearch(String userId, String text) {
        Channel channel = BeanUtil.getBean("channel", Channel.class);
        //根据指定的搜索条件搜索可添加的好友
        channel.writeAndFlush(new SearchFriendRequest(userId, text));
    }

    @Override
    public void doEventAddLuckUser(String userId, String friendId) {
        System.out.println("userId:" + userId + "friendId:" + friendId);
        Channel channel = BeanUtil.getBean("channel", Channel.class);
        channel.writeAndFlush(new AddFriendRequest(userId, friendId));
    }
}
