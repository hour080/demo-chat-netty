package org.itstack.navice.chat.ui.view.chat.element.group_bar_friend;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import org.itstack.navice.chat.ui.util.IdUtil;
import org.itstack.navice.chat.ui.view.chat.data.GroupsData;

/**
 * TODO
 * 好友框中群列表中某一个具体的群信息
 * 群信息中包含一个Pane面板，面板中存有群组信息
 * 群组信息包含群组id，群组名称和群组头像
 * 群组展示中主要包含；底板、头像、名称
 * 同时我们设置了数据元素；群组 id、群组名称、群组头像
 * @author hourui
 * @version 1.0
 * @Description
 * @date 2022/12/15 20:14
 */
public class ElementFriendGroup {
    private Pane groupPane;
    /**
     * @param groupId 群组id
     * @param groupName 群聊名称
     * @param groupHead 群聊头像
     * @author hourui
     * @date 2022/12/15 20:16
     * @return
     */
    public ElementFriendGroup(String groupId, String groupName, String groupHead){
        groupPane = new Pane();
        groupPane.setId(IdUtil.createFriendGroupId(groupId));
        groupPane.setUserData(new GroupsData(groupId, groupName, groupHead));
        groupPane.setPrefWidth(250);
        groupPane.setPrefHeight(70);
        groupPane.getStyleClass().add("elementFriendGroup");
        ObservableList<Node> children = groupPane.getChildren();
        //群的头像区域
        Label groupHeadLabel = new Label();
        groupHeadLabel.setPrefSize(50, 50);
        groupHeadLabel.setLayoutX(15);
        groupHeadLabel.setLayoutY(10);
        groupHeadLabel.getStyleClass().add("elementFriendGroup_head");
        groupHeadLabel.setStyle(String.format("-fx-background-image: url('/fxml/chat/img/head/%s.png')", groupHead));
        children.add(groupHeadLabel);
        //q名称区域
        Label groupNameLabel = new Label();
        groupNameLabel.setPrefSize(200, 40);
        groupNameLabel.setLayoutX(80);
        groupNameLabel.setLayoutY(15);
        groupNameLabel.setText(groupName);
        groupNameLabel.getStyleClass().add("elementFriendGroup_name");
        children.add(groupNameLabel);
    }

    public Pane getGroupPane() {
        return groupPane;
    }
}
