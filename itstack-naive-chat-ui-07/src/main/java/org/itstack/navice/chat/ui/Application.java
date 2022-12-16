package org.itstack.navice.chat.ui;

import javafx.stage.Stage;
import org.itstack.navice.chat.ui.view.chat.ChatController;
import org.itstack.navice.chat.ui.view.chat.IChatEvent;
import org.itstack.navice.chat.ui.view.chat.IChatMethod;

import java.util.Date;

/**
 * TODO
 * Application是javafx程序的入口，就是Main类要继承Application类，然后必须要覆盖其start方法，
 * 而start方法用于展示stage舞台，stage舞台是一个顶级容器，代表一个窗口。
 * 它用于容纳场景Scene，场景Scene是一个类似于Swing的JFrame的容器。
 * 然后需要在场景Scene中添加一个Parent对象，其中Parent对象是以树的形式组织的，每一个子组件就是它的一个节点。
 *
 *
 * 在调用launch方法时，launch先调用init方法，然后再调用start方法，
 * 当我们关闭窗口的时候，才会调用stop方法使得Application死亡。
 * 也就是必须在重写的start方法中调用Stage类的show方法将窗口展示出来，然后人工关闭窗口，才会调用stop方法。
 *
 * 在线程方面, 先开启main线程，
 * 当执行init的时候，再开启JavaFX-Launcher线程
 * 当执行start的时候再开启JavaFX Application Thread线程，随后直到关闭窗口线程才结束.
 * @author hourui
 * @version 1.0
 * @Description
 * @date 2022/11/23 22:05
 */
public class Application extends javafx.application.Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        IChatMethod chat = new ChatController(new IChatEvent() {
        });

        chat.doShow(); //ChatController会调用父类Stage的show方法
        chat.setUserInfo("1000001", "拎包冲", "02_50");

        // 好友 - 对话框
        chat.addTalkBox(-1, 0, "1000004", "哈尼克兔", "04_50", null, null, false);
        chat.addTalkMsgUserLeft("1000004", "沉淀、分享、成长，让自己和他人都有所收获！", new Date(), true, false, true);
        chat.addTalkMsgRight("1000004", "今年过年是放假时间最长的了！", new Date(), true, true, false);

        chat.addTalkBox(-1, 0, "1000002", "铁锤", "03_50", "秋风扫过树叶落，哪有棋盘哪有我", new Date(), false);
        chat.addTalkMsgUserLeft("1000002", "秋风扫过树叶落，哪有棋盘哪有我", new Date(), true, false, true);
        chat.addTalkMsgRight("1000002", "我Q，传说中的老头杀？", new Date(), true, true, false);

        // 群组
        chat.addFriendGroup("5307397", "虫洞 · 技术栈(1区)", "group_1");
        chat.addFriendGroup("5307392", "CSDN 社区专家", "group_2");
        chat.addFriendGroup("5307399", "洗脚城VIP", "group_3");

        // 群组 - 对话框
        chat.addTalkBox(0, 1, "5307397", "虫洞 · 技术栈(1区)", "group_1", "", new Date(), true);
        chat.addTalkMsgRight("5307397", "你炸了我的山", new Date(), true, true, false);
        chat.addTalkMsgGroupLeft("5307397", "1000002", "小傅哥", "01_50", "推我过忘川", new Date(), true, false, true);
        chat.addTalkMsgGroupLeft("5307397", "1000003", "铁锤", "03_50", "奈河桥边的姑娘", new Date(), true, false, true);
        chat.addTalkMsgGroupLeft("5307397", "1000004", "哈尼克兔", "04_50", "等我回头看", new Date(), true, false, true);

        // 好友
        chat.addFriendUser(false, "1000004", "哈尼克兔", "04_50");
        chat.addFriendUser(false, "1000001", "拎包冲", "02_50");
        chat.addFriendUser(false, "1000003", "铁锤", "03_50");
        chat.addFriendUser(true, "1000002", "小傅哥", "01_50");
    }

    public static void main(String[] args) {
        launch(args);
    }
}
