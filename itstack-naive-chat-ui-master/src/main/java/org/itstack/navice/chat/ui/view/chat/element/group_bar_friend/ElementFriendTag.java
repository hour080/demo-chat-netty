package org.itstack.navice.chat.ui.view.chat.element.group_bar_friend;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;

/**
 * TODO
 * 占位标签；标识下方面板是哪方面的内容
 * 例如新的朋友、公众号、群组、好友
 * @author hourui
 * @version 1.0
 * @Description
 * @date 2022/12/15 22:28
 */
public class ElementFriendTag {
    private Pane pane; //pane里面添加一个button，pane的宽度为270，其中button从x坐标为5的位置出发，长度为260

    public ElementFriendTag(String tagText){

        pane = new Pane();
        pane.setPrefSize(270, 24);
        pane.setStyle("-fx-background-color: transparent;");
        ObservableList<Node> children = pane.getChildren();

        Button label = new Button();
        label.setPrefSize(260,24);
        label.setLayoutX(5);
        label.setText(tagText);
        label.getStyleClass().add("element_friend_tag");
        children.add(label);
    }

    public Pane getPane() {
        return pane;
    }
}
