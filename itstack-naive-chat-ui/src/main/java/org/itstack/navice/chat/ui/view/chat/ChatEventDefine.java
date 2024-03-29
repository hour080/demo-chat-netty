package org.itstack.navice.chat.ui.view.chat;

import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import org.itstack.navice.chat.ui.view.chat.data.TalkBoxData;
import org.itstack.navice.chat.ui.view.face.FaceController;

import java.util.Date;


/**
 * TODO
 * Button类继承自Labeled类，它可以显示文本，图像，或两者兼而有之
 * 当鼠标点击按钮时，首要的功能就是执行一个Action。使用setOnAction的方法来定义当用户点击按钮时将发生什么
 *
 * @author hourui
 * @version 1.0
 * @Description
 * @date 2022/11/26 21:49
 */
public class ChatEventDefine {
    private ChatInit chatInit;

    private IChatMethod chatMethod;

    private IChatEvent chatEvent;

    public ChatEventDefine(ChatInit chatInit, IChatMethod chatMethod, IChatEvent chatEvent){
        this.chatInit = chatInit;
        this.chatMethod = chatMethod;
        this.chatEvent = chatEvent;
        chatInit.move();
        min();               // 最小化
        quit();              // 退出
        this.barChat();
        this.barFriend();
        doEventTextSend();   // 发送消息事件[键盘]
        doEventTouchSend();  // 发送消息事件[按钮]
        doEventToolFace();   // 表情窗体
    }
    //设置表情按钮的点击事件，每次点击按钮，就会将聊天表情框体展开
    private void doEventToolFace() {
        FaceController face = new FaceController(chatInit, chatEvent, chatMethod);
        Button tool_face = chatInit.getElement("tool_face", Button.class);
        //点击表情按钮就会展示聊天表情框体
        tool_face.setOnMousePressed(event -> {
            face.doShowFace(chatMethod.getToolFaceX(), chatMethod.getToolFaceY());
        });
    }

    // 最小化
    private void min() {
        chatInit.getElement("group_bar_chat_min", Button.class).setOnAction(event -> {
            chatInit.setIconified(true);
        });
        chatInit.getElement("group_bar_friend_min", Button.class).setOnAction(event -> {
            chatInit.setIconified(true);
        });
    }

    // 退出
    // 退出事件执行后，需要去发送给客户端执行断开 Socket 等操作
    private void quit() {
        chatInit.getElement("group_bar_chat_close", Button.class).setOnAction(event -> {
            chatEvent.doQuit();
            chatInit.close();
            System.exit(0);
        });
        chatInit.getElement("group_bar_friend_close", Button.class).setOnAction(event -> {
            chatEvent.doQuit();
            chatInit.close();
            System.exit(0);
        });
    }
    public void barChat(){
        //设置鼠标点击事件,在点击的时候切换窗体
        chatInit.barChat.setOnAction(event -> {
            switchBarChat(chatInit.barChat, chatInit.groupBarChat, true); //设置侧边栏中的聊天图标颜色以及聊天会话窗口是否可见
            switchBarFriend(chatInit.barFriend, chatInit.groupBarFriend, false); //设置侧边栏中的好友图标颜色以及好友会话窗口是否可见
        });
        //鼠标移入展示不同的背景颜色，如果不在聊天背景页面(就是黄色)，鼠标移入变为白色
        chatInit.barChat.setOnMouseEntered(event -> {
            boolean visible = chatInit.groupBarChat.isVisible();
            if(visible) return;
            chatInit.barChat.setStyle("-fx-background-image: url('/fxml/chat/img/system/chat_1.png')");
        });
        //鼠标移出展示不同的背景意思呢，如果不在聊天背景页面，鼠标移出变为灰色
        chatInit.barChat.setOnMouseExited(event -> {
            boolean visible = chatInit.groupBarChat.isVisible();
            if(visible) return;
            chatInit.barChat.setStyle("-fx-background-image: url('/fxml/chat/img/system/chat_0.png')");
        });
    }
    public void barFriend(){
        chatInit.barFriend.setOnAction(event -> {
            switchBarChat(chatInit.barChat, chatInit.groupBarChat, false);
            switchBarFriend(chatInit.barFriend, chatInit.groupBarFriend, true);
        });
        chatInit.barFriend.setOnMouseEntered(event -> {
            boolean visible = chatInit.groupBarFriend.isVisible();
            if(visible) return;
            chatInit.barFriend.setStyle("-fx-background-image: url('/fxml/chat/img/system/friend_1.png')");
        });
        chatInit.barFriend.setOnMouseExited(event -> {
            boolean visible = chatInit.groupBarFriend.isVisible();
            if(visible) return;
            chatInit.barFriend.setStyle("-fx-background-image: url('/fxml/chat/img/system/friend_0.png')");
        });
    }
    public void switchBarChat(Button barChat, Pane groupBarChat, boolean toggle){
        //切换bar_chat
        if(toggle){
            barChat.setStyle("-fx-background-image: url('/fxml/chat/img/system/chat_2.png')");
            groupBarChat.setVisible(true);
        }else {
            barChat.setStyle("-fx-background-image: url('/fxml/chat/img/system/chat_0.png')");
            groupBarChat.setVisible(false);
        }
    }
    public void switchBarFriend(Button barFriend, Pane groupBarFriend, boolean toggle){
        if(toggle){
            barFriend.setStyle("-fx-background-image: url('/fxml/chat/img/system/friend_2.png')");
            groupBarFriend.setVisible(true);
        }else {
            barFriend.setStyle("-fx-background-image: url('/fxml/chat/img/system/friend_0.png')");
            groupBarFriend.setVisible(false);
        }
    }
    // 发送消息事件[键盘]
    private void doEventTextSend() {
        TextArea txt_input = chatInit.getElement("txt_input", TextArea.class);
        //回车发送信息
        txt_input.setOnKeyPressed(event -> {
            if (event.getCode().equals(KeyCode.ENTER)) {
                doEventSendMsg();
            }
        });
    }

    // 发送消息事件[按钮]
    private void doEventTouchSend() {
        Label touch_send = chatInit.getElement("touch_send", Label.class);
        //发送按钮点击发送信息
        touch_send.setOnMousePressed(event -> {
            doEventSendMsg();
        });
    }

    private void doEventSendMsg() {
        TextArea txt_input = chatInit.getElement("txt_input", TextArea.class);
        MultipleSelectionModel selectionModel = chatInit.getElement("talkList", ListView.class).getSelectionModel();
        Pane selectedItem = (Pane) selectionModel.getSelectedItem();
        // 对话信息
        if (selectedItem == null){
            System.out.println("不存在选中的对话框用户");
            return;
        }
        TalkBoxData talkBoxData = (TalkBoxData) selectedItem.getUserData();
        String msg = txt_input.getText();
        if (null == msg || "".equals(msg) || "".equals(msg.trim())) {
            return;
        }
        Date msgDate = new Date();
        // 发送消息
        chatEvent.doSendMsg(chatInit.userId, talkBoxData.getTalkId(), talkBoxData.getTalkType(), msg, 0, msgDate);
        // 发送事件给自己添加消息
        chatMethod.addTalkMsgRight(talkBoxData.getTalkId(), msg, 0, msgDate, true, true, false);
        // 原来的文本内容清空
        txt_input.clear();
    }

    public void doEventOpenFriendGroupSendMsg(Button sendMsgButton, String groupId, String groupName, String groupHead) {
        //当用户点击一个按钮时执行一个动作(Action)，setOnAction指明用户点击按钮时发送什么动作
        sendMsgButton.setOnAction(event -> {
            // 1. 将当前群组添加到对话框
            chatMethod.addTalkBox(0, 1, groupId, groupName, groupHead, null, null, true); //这里的chatMethod就是chatController
            // 2. 切换到对话框窗口
            switchBarChat(chatInit.getElement("bar_chat", Button.class),
                    chatInit.getElement("group_bar_chat", Pane.class), true);
            switchBarFriend(chatInit.getElement("bar_friend", Button.class),
                    chatInit.getElement("group_bar_friend", Pane.class), false);
            // 3. 事件处理；填充到对话框
            chatEvent.doEventAddTalkGroup(chatInit.userId, groupId);
        });
    }

    public void doEventOpenFriendUserSendMsg(Button sendMsgButton, String userId, String userNickName, String userHead) {
        sendMsgButton.setOnAction(event -> {
            //将对话框填充到对话框列表的首位
            chatMethod.addTalkBox(0, 0, userId, userNickName, userHead, null, null, true);
            // 2. 切换到对话框窗口
            switchBarChat(chatInit.getElement("bar_chat", Button.class),
                    chatInit.getElement("group_bar_chat", Pane.class), true);
            switchBarFriend(chatInit.getElement("bar_friend", Button.class),
                    chatInit.getElement("group_bar_friend", Pane.class), false);
            // 3. 事件处理；向服务端发送增加对话框请求，填充到对话框
            chatEvent.doEventAddTalkUser(chatInit.userId, userId);
        });
    }
}
