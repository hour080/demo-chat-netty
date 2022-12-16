package org.itstack.navice.chat.ui.view.chat;

import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.Pane;
import org.itstack.navice.chat.ui.view.chat.data.RemindCount;
import org.itstack.navice.chat.ui.view.chat.data.TalkBoxData;
import org.itstack.navice.chat.ui.view.chat.element.group_bar_friend.*;

/**
 * TODO
 * 聊天窗体的展示，主要用于扩展一些随着用户操作新展示的元素，
 * 例如后续在聊天窗体新增的消息提醒等
 * @author hourui
 * @version 1.0
 * @Description
 * @date 2022/11/26 21:49
 */
public class ChatView {
    private ChatInit chatInit;
    private IChatEvent chatEvent;

    private ObservableList items;


    public ChatView(ChatInit chatInit, IChatEvent chatEvent) {
        this.chatInit = chatInit;
        this.chatEvent = chatEvent;
        ListView friendList = chatInit.getElement("friendList", ListView.class);
        items = friendList.getItems();
        //1. 好友列表添加工具方法‘新的朋友’
        initAddFriendLuck();
        //2. 好友列表添加‘公众号’
        addFriendSubscription();
        //3. 好友群组框体
        addFriendGroupList();
        //4. 好友框体
        addFriendUserList();
    }

    /**
     * 好友框添加好友列表框体
     * @author hourui
     * @date 2022/12/16 10:55
     * @return void
     */
    private void addFriendUserList() {
        ElementFriendTag elementFriendTag = new ElementFriendTag("好友");
        items.add(elementFriendTag.getPane());
        ElementFriendUserList element = new ElementFriendUserList();
        Pane pane = element.getPane();
        items.add(pane);
    }

    /**
     * 好友框添加群组列表框体
     * @author hourui
     * @date 2022/12/16 10:53
     * @return void
     */
    private void addFriendGroupList() {
        ElementFriendTag elementFriendTag = new ElementFriendTag("群聊");
        items.add(elementFriendTag.getPane());
        ElementFriendGroupList element = new ElementFriendGroupList();
        Pane pane = element.getPane();
        items.add(pane);
        //这里就不需要清除群聊和好友列表中选中的内容了，因为当前访问的就是群聊
    }

    /**
     * 好友框添加‘公众号’框体
     * @author hourui
     * @date 2022/12/16 10:46
     * @return void
     */
    private void addFriendSubscription() {
        ElementFriendTag elementFriendTag = new ElementFriendTag("公众号");
        items.add(elementFriendTag.getPane());
        ElementFriendSubscription element = new ElementFriendSubscription();
        Pane pane = element.getPane();
        items.add(pane);
        //添加点击事件，去掉用户列表和群组列表选中的内容
        pane.setOnMousePressed(event -> {
            //因为是 ListView 里嵌套 ListView，鼠标点击不同的框体，都会是选中，不会去掉选中。所以我们需要添加额外的事件来处理。
            chatInit.clearViewListSelectedAll(chatInit.getElement("userListView", ListView.class),
                    chatInit.getElement("groupListView", ListView.class));
        });
    }

    /**
     * 好友框添加新的好友框体
     * 获取了好友列表friendList，并设置了 新的朋友 添加添加到框体中，
     * 并可以看到我们这里设置了标签 items.add(elementFriendTag.pane())
     * @author hourui
     * @date 2022/12/16 09:54
     * @return void
     */
    private void initAddFriendLuck() {
        ElementFriendTag elementFriendTag = new ElementFriendTag("新的朋友");
        items.add(elementFriendTag.getPane());
        ElementFriendLuck elementFriendLuck = new ElementFriendLuck(); //创建一个新的好友对象
        Pane pane = elementFriendLuck.getPane();
        items.add(pane);
        //为好友对象面板设置鼠标点击事件
        pane.setOnMousePressed(event -> {
            //因为是 ListView 里嵌套 ListView，鼠标点击不同的框体，都会是选中，不会去掉选中。所以我们需要添加额外的事件来处理。
            chatInit.clearViewListSelectedAll(chatInit.getElement("userListView", ListView.class),
                    chatInit.getElement("groupListView", ListView.class));
        });
    }


    /**
     * 更新对话框列表元素位置指定并选中[在聊天消息发送时触达]
     * 设置消息提醒
     */
    /**
     * @param talkType        对话框类型[0好友、1群组]
     * @param talkElementPane 对话框元素面板
     * @param msgRemindLabel  消息提醒标签
     * @param idxFirst        是否设置首位
     * @param selected        是否选中
     * @param isRemind        是否提醒
     */
    void updateTalkListIdxAndSelected(int talkType, Pane talkElementPane, Label msgRemindLabel, Boolean idxFirst, Boolean selected, Boolean isRemind) {
        // 对话框ID、好友ID
        TalkBoxData talkBoxData = (TalkBoxData) talkElementPane.getUserData();
        // 填充到对话框s
        ListView<Pane> talkList = chatInit.getElement("talkList", ListView.class);
        // 对话空为空，初始化[置顶、选中、提醒]
        if (talkList.getItems().isEmpty()) {
            if (idxFirst) {
                talkList.getItems().add(0, talkElementPane);
            }
            if (selected) {
                // 设置对话框[√选中]
                talkList.getSelectionModel().select(talkElementPane);
            }
            isRemind(msgRemindLabel, talkType, isRemind);
            return;
        }
        // 对话空不为空，判断第一个元素是否当前聊天Pane
        Pane firstPane = talkList.getItems().get(0);
        // 判断元素是否在首位，如果是首位可返回不需要重新设置首位
        if (talkBoxData.getTalkId().equals(((TalkBoxData) firstPane.getUserData()).getTalkId())) {
            Pane selectedItem = talkList.getSelectionModel().getSelectedItem();
            // 选中判断；如果第一个元素已经选中[说明正在会话]，那么清空消息提醒
            if (null == selectedItem){
                isRemind(msgRemindLabel, talkType, isRemind);
                return;
            }
            TalkBoxData selectedItemUserData = (TalkBoxData) selectedItem.getUserData();
            if (null != selectedItemUserData && talkBoxData.getTalkId().equals(selectedItemUserData.getTalkId())) {
                clearRemind(msgRemindLabel);
            } else {
                isRemind(msgRemindLabel, talkType, isRemind);
            }
            return;
        }
        if (idxFirst) {
            talkList.getItems().remove(talkElementPane);
            talkList.getItems().add(0, talkElementPane);
        }
        if (selected) {
            // 设置对话框[√选中]
            talkList.getSelectionModel().select(talkElementPane);
        }
        isRemind(msgRemindLabel, talkType, isRemind);
    }

    /**
     * 消息提醒
     *
     * @param msgRemindLabel 消息面板
     */
    private void isRemind(Label msgRemindLabel, int talkType, Boolean isRemind) {
        if (!isRemind) return;
        msgRemindLabel.setVisible(true);
        // 群组直接展示小红点
        if (1 == talkType) {
            return;
        }
        RemindCount remindCount = (RemindCount) msgRemindLabel.getUserData();
        // 超过10个展示省略号
        if (remindCount.getCount() > 99) {
            msgRemindLabel.setText("···");
            return;
        }
        int count = remindCount.getCount() + 1;
        msgRemindLabel.setUserData(new RemindCount(count));
        msgRemindLabel.setText(String.valueOf(count));
    }

    private void clearRemind(Label msgRemindLabel) {
        msgRemindLabel.setVisible(false);
        msgRemindLabel.setUserData(new RemindCount(0));
    }

}
