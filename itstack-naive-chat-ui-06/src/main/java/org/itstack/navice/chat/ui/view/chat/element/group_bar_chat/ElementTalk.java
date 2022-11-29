package org.itstack.navice.chat.ui.view.chat.element.group_bar_chat;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import org.itstack.navice.chat.ui.util.DateUtil;
import org.itstack.navice.chat.ui.util.IdUtil;
import org.itstack.navice.chat.ui.view.chat.data.RemindCount;
import org.itstack.navice.chat.ui.view.chat.data.TalkBoxData;

import java.util.Date;

/**
 * TODO
 * 对话框体中的元素类
 * 包含对话面板，头像区域，昵称区域，信息简述，信息时间，消息提醒，删除对话
 * @author hourui
 * @version 1.0
 * @Description
 * @date 2022/11/29 14:10
 */
public class ElementTalk {
    private Pane pane;       // 对话面板 (与好友对话、与群组对话)
    private Label head;      // 头像区域
    private Label nikeName;  // 昵称区域
    private Label msgSketch; // 信息简述
    private Label msgDate;   // 信息时间
    private Label msgRemind; // 消息提醒
    private Button delete;   // 删除对话框按钮

    public ElementTalk(String talkId, Integer talkType, String talkName, String talkHead, String talkSketch, Date talkDate){
        pane = new Pane(); //Pane是Node类的子类
        pane.setId(IdUtil.createTalkPaneId(talkId));
        pane.setUserData(new TalkBoxData(talkId, talkType, talkName, talkHead)); //往Node的Map对象中添加一个以USER_DATA_KEY为键，TalkBoxData为值的键值对
        pane.setPrefSize(270, 80);
        pane.getStyleClass().add("talkElement"); //给面板增加类选择器
        ObservableList<Node> children = pane.getChildren();

        //头像区域
        head = new Label();
        head.setPrefSize(50, 50);
        head.setLayoutX(15);
        head.setLayoutY(15);
        head.getStyleClass().add("element_head");
        head.setStyle(String.format("-fx-background-image: url('/fxml/chat/img/head/%s.png')", talkHead));
        children.add(head);

        //昵称区域
        nikeName = new Label();
        nikeName.setPrefSize(140, 25);
        nikeName.setLayoutX(80);
        nikeName.setLayoutY(15);
        nikeName.setText(talkName);
        nikeName.getStyleClass().add("element_nikeName");
        children.add(nikeName);

        //信息简述
        msgSketch = new Label();
        msgSketch.setId(IdUtil.createMsgSketchId(talkId));
        msgSketch.setPrefSize(200, 25);
        msgSketch.setLayoutX(80);
        msgSketch.setLayoutY(40);
        msgSketch.getStyleClass().add("element_msgSketch");
        children.add(msgSketch);

        //信息时间
        msgDate = new Label();
        msgDate.setId(IdUtil.createMsgDataId(talkId));
        msgDate.setPrefSize(60, 25);
        msgDate.setLayoutX(220);
        msgDate.setLayoutY(15);
        msgDate.getStyleClass().add("element_msgData");
        children.add(msgDate);
        //给信息简述和信息时间中增添内容
        fillMsgSketch(talkSketch, talkDate);

        //设置消息提醒
        msgRemind = new Label();
        msgRemind.setPrefSize(15, 15);
        msgRemind.setLayoutX(60);
        msgRemind.setLayoutY(5);
        msgRemind.setUserData(new RemindCount());
        msgRemind.setText("");
        msgRemind.setVisible(false); //一开始消息提醒是不展示的
        msgRemind.getStyleClass().add("element_msgRemind");
        children.add(msgRemind);

        // 删除对话框按钮
        delete = new Button();
        delete.setVisible(false);
        delete.setPrefSize(4, 4);
        delete.setLayoutY(26);
        delete.setLayoutX(-8);
        delete.getStyleClass().add("element_delete");
        children.add(delete);
    }
    public void fillMsgSketch(String talkSketch, Date talkDate){
        if(talkSketch != null){
            if(talkSketch.length() > 30){
                talkSketch = talkSketch.substring(0, 30);
            }
            msgSketch.setText(talkSketch);
        }
        if (talkDate == null){
            talkDate = new Date();
        }
        String talkFormatDate = DateUtil.formatDate(talkDate);
        msgDate.setText(talkFormatDate);
    }
    public Pane getPane(){
        return pane;
    }
    public Button getDelete(){
        return delete;
    }

    public void clearMsgSketch() {
        msgSketch.setText("");
    }
}
