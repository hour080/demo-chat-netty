package org.itstack.navice.chat.ui.view.login;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.StageStyle;
import org.itstack.navice.chat.ui.view.UIObject;

import java.io.IOException;

/**
 * TODO
 * 窗体的初始化操作
 * @author hourui
 * @version 1.0
 * @Description
 * @date 2022/11/25 15:18
 */
public abstract class LoginInit extends UIObject {
    private static final String RESOURCE_NAME = "/fxml/login/login.fxml";
    protected ILoginEvent loginEvent;
    public Button login_min;          // 登陆窗口最小化
    public Button login_close;        // 登陆窗口退出
    public Button login_button;       // 登陆按钮
    public TextField userId;          // 用户账户窗口
    public PasswordField userPassword;// 用户密码窗口
    public LoginInit(ILoginEvent loginEvent){
        this.loginEvent = loginEvent;
        try {
            root = FXMLLoader.load(getClass().getResource(RESOURCE_NAME));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Scene scene = new Scene(root); //布置一个场景
        scene.setFill(Color.TRANSPARENT);
        setScene(scene);
        initStyle(StageStyle.TRANSPARENT); //设置初始化样式
        setResizable(false); //设置此窗体是否可由用户调整大小
        this.getIcons().add(new Image("/fxml/login/img/system/logo.png"));
        obtain();
        initView(); //初始化页面，初始化舞台，按钮和文本
        initEventDefine(); //初始化事件定义，包括按钮点击事件，鼠标移动事件
    }
    public void obtain(){
        login_min = getElement("login_min", Button.class);
        login_close = getElement("login_close", Button.class);
        login_button = getElement("login_button", Button.class);
        userId = getElement("userId", TextField.class);
        userPassword = getElement("userPassword", PasswordField.class);
    }

}
