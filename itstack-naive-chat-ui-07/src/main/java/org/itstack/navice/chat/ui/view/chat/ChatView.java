package org.itstack.navice.chat.ui.view.chat;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
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
        //1. 好友列表添加工具方法‘新的朋友’, 点击新的哦嗯有会填充右边展示面板，并且回车会默认添加新的好友
        initAddFriendLuck();
        //2. 好友列表添加‘公众号’, 点击公众号会填充右边展示面板
        addFriendSubscription();
        //3. 好友群组框体，点击某个群组会填充右边展示面板
        addFriendGroupList();
        //4. 好友框体， 点击某个好友会填充右边展示面板
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
            Pane subPane = element.getSubPane();
            //将子面板填充到右侧展示面板上
            setContentPaneBox(subPane, "itstack-naive-chat-ui-chat-friend-subscription", "公众号");
        });
    }

    /**
     * 获取了好友列表friendList，并设置了 新的朋友 添加添加到框体中，
     * 并可以看到我们这里设置了标签 items.add(elementFriendTag.pane())
     * 首先在面板点击事件中，也就是点击我们的 新的朋友 时候将我们的好友搜索面板 Pane friendLuckPane = element.friendLuckPane()，获取后填充到 ID：content_pane_box 中。
     * 同时我们填充相应的处理事件清空原有的好友搜索结果
     * 那么在用户在好友搜索框 回车 后我们通过设定好的事件；friendLuckSearch.setOnKeyPressed，来执行搜索内容的添加
     * @author hourui
     * @date 2022/12/16 09:54
     * @return void
     */
    private void initAddFriendLuck() {
        //创建一个新的好友的标志
        ElementFriendTag elementFriendTag = new ElementFriendTag("新的朋友");
        items.add(elementFriendTag.getPane());
        //创建一个新的好友对象（包含头像，名称·，右边展现面板，搜索栏，用户栏）
        ElementFriendLuck elementFriendLuck = new ElementFriendLuck();
        Pane pane = elementFriendLuck.getPane();
        items.add(pane);
        //为点击“新的朋友”面板设置鼠标点击事件,
        pane.setOnMousePressed(event -> {
            //获得好友栏中的新的朋友对应的右边界面面板friendLuckPane
            Pane friendLuckPane = elementFriendLuck.getFriendLuckPane();
            //填充右边界面面板 以及 右边界面面板名称
            setContentPaneBox(friendLuckPane, "itstack-naive-chat-ui-chat-friend-luck", "新的朋友");
            //因为是 ListView 里嵌套 ListView，鼠标点击不同的框体，都会是选中，不会去掉选中。所以我们需要添加额外的事件来处理。
            chatInit.clearViewListSelectedAll(chatInit.getElement("userListView", ListView.class),
                    chatInit.getElement("groupListView", ListView.class));
            //得到待添加的好友列表信息
            ListView<Pane> listView = elementFriendLuck.getFriendLuckListView();
            listView.getItems().clear();
            chatEvent.addFriendLuck(chatInit.userId, listView); //添加新的好友
//            System.out.println("添加好友");
        });
        //搜索栏
        TextField friendLuckSearch = elementFriendLuck.getFriendLuckSearch();

        //键盘事件；搜索好友  setOnKeyPressed: 按下某个按键时执行函数
        friendLuckSearch.setOnKeyPressed(event -> {
            //如果按下回车键
            if(event.getCode().equals(KeyCode.ENTER)){
                String text = friendLuckSearch.getText();
                if(text == null) text = "";
                if(text.length() > 30) text = text.substring(0, 30);
                text = text.trim();
                chatEvent.doFriendLuckSearch(chatInit.userId, text);
//                System.out.println("搜搜好友：" + text);
                // 搜索清空元素
                elementFriendLuck.getFriendLuckListView().getItems().clear();
            }
        });
    }
    /**
     * 填充右边展现面板 以及 面板名称
     * @param node 右侧展现面板
     * @param id  用户、群组等ID
     * @param name 右侧展现面板名称
     * @author hourui
     * @date 2022/12/16 16:54
     * @return void
     */
    public void setContentPaneBox(Node node, String id, String name) {
        Pane content_pane_box = chatInit.getElement("content_pane_box", Pane.class);
        content_pane_box.setUserData(id);
        content_pane_box.getChildren().clear();
        content_pane_box.getChildren().add(node);
        Label content_name = chatInit.getElement("content_name", Label.class);
        //设定右边展现界面的名称
        content_name.setText(name);
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
