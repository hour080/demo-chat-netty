package org.itstack.navice.chat.ui.view.chat.element.group_bar_friend;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.text.TextAlignment;

/**
 * TODO
 * 公众号信息
 * @author hourui
 * @version 1.0
 * @Description
 * @date 2022/12/15 22:16
 */
public class ElementFriendSubscription {
    private Pane pane;

    private Label head;  // 头像

    private Label name;  // 名称

    private Pane subPane; // 点击公众号，右侧展示公众号面板信息

    public ElementFriendSubscription() {
        pane = new Pane();
        pane.setPrefSize(270, 70);
        pane.getStyleClass().add("elementFriendSubscription");
        ObservableList<Node> children = pane.getChildren();

        // 头像区域
        head = new Label();
        head.setPrefSize(50, 50);
        head.setLayoutX(15);
        head.setLayoutY(10);
        head.getStyleClass().add("elementFriendSubscription_head");
        children.add(head);

        // 名称区域
        name = new Label();
        name.setPrefSize(200, 40);
        name.setLayoutX(80);
        name.setLayoutY(15);
        name.setText("公众号");
        name.getStyleClass().add("elementFriendSubscription_name");
        children.add(name);

        subPane = new Pane(); //点击公众号，展示右侧面板的详细信息
        subPane.setPrefSize(850, 560);
        subPane.setStyle("-fx-background-color:transparent;"); //设置背景颜色为透明色
        ObservableList<Node> subPaneChildren = subPane.getChildren();

        Button gzh_button = new Button(); //设置公众号的图标
        gzh_button.setPrefSize(65,65);
        gzh_button.setLayoutX(110);
        gzh_button.setLayoutY(30);
        gzh_button.setStyle("-fx-background-color: transparent;" +
                "-fx-background-radius: 0px;" +
                "-fx-border-width: 50px;" +
                "-fx-background-image: url('/fxml/login/img/system/bugstack_logo.png');");
        subPaneChildren.add(gzh_button);

        //设置公众号的文本信息
        Label gzh_label = new Label();
        gzh_label.setPrefSize(150,20);
        gzh_label.setLayoutX(95);
        gzh_label.setLayoutY(100);
        gzh_label.setText("bugstack虫洞栈");
        gzh_label.setStyle("-fx-background-color: transparent;-fx-border-width: 0; -fx-text-fill: #999999;" +
                "-fx-font-size: 14px;");
        gzh_label.setTextAlignment(TextAlignment.CENTER);
        subPaneChildren.add(gzh_label);
    }

    public Pane getPane() {
        return pane;
    }

    public Pane getSubPane() {
        return subPane;
    }
}
