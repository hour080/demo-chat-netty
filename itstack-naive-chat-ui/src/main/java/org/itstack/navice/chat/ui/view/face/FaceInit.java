package org.itstack.navice.chat.ui.view.face;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.StageStyle;
import org.itstack.navice.chat.ui.view.UIObject;
import org.itstack.navice.chat.ui.view.chat.ChatInit;
import org.itstack.navice.chat.ui.view.chat.IChatEvent;
import org.itstack.navice.chat.ui.view.chat.IChatMethod;

import java.io.IOException;

/**
 * TODO
 *
 * @author hourui
 * @version 1.0
 * @Description
 * @date 2023/1/2 21:49
 */
public abstract class FaceInit extends UIObject {
    public Pane rootPane; //聊天表情框体的面板
    public ChatInit chatInit;

    public IChatMethod chatMethod;
    public IChatEvent chatEvent;
    public static final String RESOURCE_NAME = "/fxml/face/face.fxml";
    public FaceInit(){
        try {
            root = FXMLLoader.load(this.getClass().getResource(RESOURCE_NAME)); //Parent root也就是Node节点
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Scene scene = new Scene(root);
        scene.setFill(Color.TRANSPARENT);
        setScene(scene);
        initStyle(StageStyle.TRANSPARENT);
        setResizable(false);
        // 模态窗口， APPLICATION_MODAL;定义一个模式窗口，该窗口阻止事件传递到任何其他应用程序窗口。
        initModality(Modality.APPLICATION_MODAL);
        obtain();
        initView(); //初始化视图
        initEventDefine(); //初始化事件定义
    }
    private void obtain() {
        rootPane = getElement("face", Pane.class);
    }
    public Parent getRoot(){
        return super.root;
    }
}
