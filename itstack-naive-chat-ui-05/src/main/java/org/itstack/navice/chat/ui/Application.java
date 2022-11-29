package org.itstack.navice.chat.ui;

import javafx.stage.Stage;
import org.itstack.navice.chat.ui.view.chat.ChatController;
import org.itstack.navice.chat.ui.view.chat.IChatMethod;
import org.itstack.navice.chat.ui.view.login.ILoginMethod;
import org.itstack.navice.chat.ui.view.login.LoginController;

/**
 * TODO
 * Application是javafx程序的入口点，就是Main类要继承Application类，然后覆盖其start方法，
 * 而start方法用于展示stage舞台，stage舞台是一个顶级容器，代表一个窗口。
 * 它用于容纳场景Scene，场景Scene是一个类似于Swing的JFrame的容器。
 * 但是却是以树的形式组织的，每一个子组件就是它的一个节点。
 * @author hourui
 * @version 1.0
 * @Description
 * @date 2022/11/23 22:05
 */
public class Application extends javafx.application.Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        IChatMethod chat = new ChatController();
        chat.doShow();
//        ILoginMethod login = new LoginController(((userId, userPassword) -> {
//            System.out.println("登陆 userId：" + userId + "userPassword：" + userPassword);
//        }));
//        login.doShow();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
