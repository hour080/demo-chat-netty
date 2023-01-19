package org.itstack.navice.chat.ui.view.chat.element.group_bar_friend;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.ListView;
import javafx.scene.layout.Pane;

/**
 * TODO
 * 群组列表信息
 * @author hourui
 * @version 1.0
 * @Description
 * @date 2022/12/15 21:31
 */
public class ElementFriendGroupList {
    private Pane pane; //群组列表的底板信息
    private ListView<Pane> groupListView; //群组列表

    public ElementFriendGroupList() {
        pane = new Pane();
        pane.setId("friendGroupList");//群组列表的底层面板, 这样id为friendList的ListView只要添加friendGroupList即可
        pane.setPrefWidth(314);
        pane.setPrefHeight(0); //根据其中有多少个friendGroup来计算 70 * item_size + 10
        pane.setLayoutX(-10);
        pane.getStyleClass().add("elementFriendGroupList");
        ObservableList<Node> children = pane.getChildren();
        groupListView = new ListView<>();
        groupListView.setId("groupListView"); //群组列表框体
        groupListView.setPrefWidth(314);
        groupListView.setPrefHeight(0); // 自动计算；groupListView.setPrefHeight(70 * items.size() + 10);
        groupListView.setLayoutX(-10);
        groupListView.getStyleClass().add("elementFriendGroupList_listView");
        children.add(groupListView);
    }

    public Pane getPane() {
        return pane;
    }
}
