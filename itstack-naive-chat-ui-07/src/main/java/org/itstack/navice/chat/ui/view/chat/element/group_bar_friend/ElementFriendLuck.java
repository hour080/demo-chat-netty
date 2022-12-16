package org.itstack.navice.chat.ui.view.chat.element.group_bar_friend;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;


/**
 * TODO
 * 新添加的好友信息
 * 后续会在这里添加一个面板来展示搜索好友框和添加好友等功能
 * @author hourui
 * @version 1.0
 * @Description
 * @date 2022/12/15 21:50
 */
public class ElementFriendLuck {
    private Pane pane;

    private Label head; //头像

    private Label name; //名称

    private Pane friendLuckPane;  // 点击好友框中的好友在右侧展示的用户面板

    private TextField friendLuckSearch;  // 用户搜索

    private ListView<Pane> friendLuckListView; // 用户列表[待添加好友用户]

    public ElementFriendLuck(){
        pane = new Pane();
        pane.setId("elementFriendLuck");
        pane.setPrefSize(270, 70);
        pane.getStyleClass().add("elementFriendLuck");
        ObservableList<Node> children = pane.getChildren();
        //好友的头像区域
        head = new Label();
        head.setPrefSize(50, 50);
        head.setLayoutX(15);
        head.setLayoutY(10);
        head.getStyleClass().add("elementFriendLuck_head");
        children.add(head);
        // 好友的名称区域
        name = new Label();
        name.setPrefSize(200, 40);
        name.setLayoutX(80);
        name.setLayoutY(15);
        name.setText("新的朋友");
        name.getStyleClass().add("elementFriendLuck_name");
        children.add(name);

        // 初始化框体区域[搜索好友框、展现框]
        friendLuckPane = new Pane();
        friendLuckPane.setPrefSize(850, 560);
        friendLuckPane.getStyleClass().add("friendLuckPane");
        ObservableList<Node> friendLuckPaneChildren = friendLuckPane.getChildren();

        //好友搜索的输入框
        friendLuckSearch = new TextField();
        friendLuckSearch.setPrefSize(600,50);
        friendLuckSearch.setLayoutX(125);
        friendLuckSearch.setLayoutY(25);
        //设置输入框的提示语，用来提示用户可以输入什么样的文本。
        friendLuckSearch.setPromptText("搜一搜");
        friendLuckSearch.setPadding(new Insets(10));
        friendLuckSearch.getStyleClass().add("friendLuckSearch");
        friendLuckPaneChildren.add(friendLuckSearch);

        // 用户列表框[初始化，未装载]
        friendLuckListView = new ListView<>();
        friendLuckListView.setId("friendLuckListView");
        friendLuckListView.setPrefSize(850, 460);
        friendLuckListView.setLayoutY(75);
        friendLuckListView.getStyleClass().add("friendLuckListView");
        friendLuckPaneChildren.add(friendLuckListView);
    }

    public Pane getPane() {
        return pane;
    }

    public Pane getFriendLuckPane() {
        return friendLuckPane;
    }

    public TextField getFriendLuckSearch() {
        return friendLuckSearch;
    }

    public ListView<Pane> getFriendLuckListView() {
        return friendLuckListView;
    }
}
