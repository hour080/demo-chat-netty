package org.itstack.navice.chat.ui.view.chat;

import javafx.scene.control.Button;
import javafx.scene.layout.Pane;


/**
 * TODO
 *
 * @author hourui
 * @version 1.0
 * @Description
 * @date 2022/11/26 21:49
 */
public class ChatEventDefine {
    private ChatInit chatInit;

    public ChatEventDefine(ChatInit chatInit){
        this.chatInit = chatInit;
        chatInit.move();
        this.barChat();
        this.barFriend();
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
}
