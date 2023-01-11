package org.itstack.navice.chat.ui.view.chat.element.group_bar_friend;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

/**
 * TODO
 * 好友搜索框面板，主要承担好友的搜索和添加
 * ElementFriendLuckUser代表要添加的某个好友信息
 * @author hourui
 * @version 1.0
 * @Description
 * @date 2022/12/16 14:57
 */
public class ElementFriendLuckUser {
    private Pane pane; //好友搜索的底板
    private Label idLabel; // 展现的用户的id
    private Label headLabel; //头像区域
    private Label nameLabel; //昵称区域
    private Label statusLabel; //用户的状态  0 待添加 1 已添加
    private Label line; //底线

    /**
     * @param userId  用户id
     * @param userNickName  用户昵称
     * @param userHead  用户头像
     * @param status  用户状态  0 待添加 1 已添加
     * @author hourui
     * @date 2022/12/16 15:11
     * @return
     */
    public ElementFriendLuckUser(String userId, String userNickName, String userHead, Integer status) {
        pane = new Pane();
        pane.setUserData(userId);
        pane.setPrefWidth(250);
        pane.setPrefHeight(70);
        pane.getStyleClass().add("elementFriendLuckUser");
        ObservableList<Node> children = pane.getChildren();
        //头像区域
        headLabel = new Label();
        headLabel.setPrefSize(50, 50);
        headLabel.setLayoutX(125);
        headLabel.setLayoutY(10);
        headLabel.getStyleClass().add("elementFriendLuckUser_head");
        headLabel.setStyle(String.format("-fx-background-image: url('/fxml/chat/img/head/%s.png')", userHead));
        children.add(headLabel);
        //名称区域
        nameLabel = new Label();
        nameLabel.setPrefSize(200, 30);
        nameLabel.setLayoutX(190);
        nameLabel.setLayoutY(10);
        nameLabel.setText(userNickName);
        nameLabel.getStyleClass().add("elementFriendLuckUser_name");
        children.add(nameLabel);
        //ID区域
        idLabel = new Label();
        idLabel.setPrefSize(200, 20);
        idLabel.setLayoutX(190);
        idLabel.setLayoutY(40); //名称区域的高度30 + Y的坐标10
        idLabel.setText(userId);
        idLabel.getStyleClass().add("elementFriendLuckUser_id");
        children.add(idLabel);

        // 底线
        line = new Label();
        line.setPrefSize(582,1);
        line.setLayoutX(125);
        line.setLayoutY(50);
        line.getStyleClass().add("elementFriendLuck_line");
        children.add(line);

        // 状态区域
        statusLabel = new Label();
        statusLabel.setPrefSize(56, 30);
        statusLabel.setLayoutX(650);
        statusLabel.setLayoutY(20);
        String statusText = "添加";
        if(status == 1){
            statusText = "允许";
        }else if(status == 2){
            statusText = "已添加";
        }
        statusLabel.setText(statusText);
        statusLabel.setUserData(status);
        statusLabel.getStyleClass().add("elementFriendLuckUser_statusLabel_" + status);
        children.add(statusLabel);
    }

    public Pane getPane() {
        return pane;
    }

    public Label getStatusLabel() {
        return statusLabel;
    }
}
