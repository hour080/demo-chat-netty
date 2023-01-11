package org.itstack.navice.chat.ui.view.chat;

import javafx.scene.control.ListView;
import javafx.scene.layout.Pane;

import java.util.Date;

public interface IChatEvent {
    /**
     * 聊天窗口退出操作
     */
    void doQuit();

    /**
     * 发送消息按钮
     *
     * @param userId   用户 Id
     * @param talkId   对话框Id(好友 ID/ 群组 ID)
     * @param talkType 对话框类型；0 好友、1 群组
     * @param msg      发送消息内容
     * @param msgDate  发送消息时间
     */
    void doSendMsg(String userId, String talkId, Integer talkType, String msg, Date msgDate);

    /**
     * 事件处理；开启与好友发送消息 [点击发送消息时候触发 -> 添加到对话框、选中、展示对话列表]
     *
     * @param userId       用户 ID
     * @param userFriendId 好友 ID
     */
    void doEventAddTalkUser(String userId, String userFriendId);
    /**
     * 事件处理；开启与群组发送消息
     *
     * @param userId  用户 ID
     * @param groupId 群组 ID
     */
    void doEventAddTalkGroup(String userId, String groupId);
    /**
     * 事件处理；删除指定对话框
     *
     * @param userId 用户 ID
     * @param talkId 对话框 ID
     */
    void doEventDelTalkUser(String userId, String talkId);
    /**
     * 事件处理；查询有缘用户添加到待添加好友列表
     *
     * @param userId   用户 ID
     * @param listView 用户列表 [非必需使用，同步接口可使用]
     */
    void addFriendLuck(String userId, ListView<Pane> listView);
    /**
     * 事件处理；好友搜索 [搜索后结果调用添加：addLuckFriend]
     *
     * @param userId 用户 ID
     * @param text   搜索关键字
     */
    void doFriendLuckSearch(String userId, String text);
    /**
     * 添加好友事件
     *
     * @param userId   个人 ID
     * @param friendId 好友 ID
     */
    void doEventAddLuckUser(String userId, String friendId);

}
