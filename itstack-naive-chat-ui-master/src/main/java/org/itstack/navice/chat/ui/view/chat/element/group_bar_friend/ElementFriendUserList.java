package org.itstack.navice.chat.ui.view.chat.element.group_bar_friend;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.ListView;
import javafx.scene.layout.Pane;

/**
 * TODO
 * 已添加的好友元素列表
 * @author hourui
 * @version 1.0
 * @Description
 * @date 2022/12/15 22:33
 */
public class ElementFriendUserList {
    private Pane pane;
    private ListView<Pane> userListView; // 好友列表
    public ElementFriendUserList(){
        pane = new Pane();
        pane.setId("friendUserList");
        pane.setPrefWidth(314);
        pane.setPrefHeight(0);// 自动计算；userListView.setPrefHeight(70 * items.size() + 10);
        pane.setLayoutX(-10);
        pane.getStyleClass().add("elementFriendUserList");
        ObservableList<Node> children = pane.getChildren();

        userListView = new ListView<>();
        userListView.setId("userListView");
        userListView.setPrefWidth(314);
        userListView.setPrefHeight(0); // 自动计算；userListView.setPrefHeight(70 * items.size() + 10);
        userListView.setLayoutX(-10);
        userListView.getStyleClass().add("elementFriendUser_listView");
        children.add(userListView);
    }

    public Pane getPane() {
        return pane;
    }
}
