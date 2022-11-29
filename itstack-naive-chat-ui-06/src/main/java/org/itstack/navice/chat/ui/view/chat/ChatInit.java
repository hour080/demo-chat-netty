package org.itstack.navice.chat.ui.view.chat;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.StageStyle;
import org.itstack.navice.chat.ui.view.UIObject;

import java.io.IOException;

/**
 * TODO
 *
 * @author hourui
 * @version 1.0
 * @Description
 * @date 2022/11/26 21:49
 */
public abstract class ChatInit extends UIObject {
    private static final String RESOURCE_NAME = "/fxml/chat/chat.fxml";

    public Button barChat;

    public Pane groupBarChat;

    public Button barFriend;

    public Pane groupBarFriend;

//    public Button bar_location;
//
//    public Button bar_set;
//
//    public Button bar_portrait;


    public ChatInit(){
        try {
            root = FXMLLoader.load(this.getClass().getResource(RESOURCE_NAME));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Scene scene = new Scene(root); //给舞台设置一个场景
        scene.setFill(Color.TRANSPARENT); //设置场景的背景颜色
        setScene(scene);
        initStyle(StageStyle.TRANSPARENT); //设置样式的背景颜色
        setResizable(false); //设置窗口大小不可调整
        this.getIcons().add(new Image("/fxml/chat/img/head/logo.png"));
        obtain();
        initView();
        initEventDefine();
    }
    public void obtain(){
        barChat = getElement("bar_chat", Button.class); //这里调用的是父类的getElement的方法
        groupBarChat = getElement("group_bar_chat", Pane.class);
        barFriend = getElement("bar_friend", Button.class);
        groupBarFriend = getElement("group_bar_friend", Pane.class);
    }
}
