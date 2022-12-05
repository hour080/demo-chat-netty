package org.itstack.navice.chat.ui.view.chat;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.Pane;
import org.itstack.navice.chat.ui.util.CacheUtil;
import org.itstack.navice.chat.ui.util.IdUtil;
import org.itstack.navice.chat.ui.view.chat.data.GroupsData;
import org.itstack.navice.chat.ui.view.chat.data.RemindCount;
import org.itstack.navice.chat.ui.view.chat.data.TalkData;
import org.itstack.navice.chat.ui.view.chat.element.group_bar_chat.ElementInfoBox;
import org.itstack.navice.chat.ui.view.chat.element.group_bar_chat.ElementTalk;

import java.util.Date;

/**
 * TODO
 *
 * @author hourui
 * @version 1.0
 * @Description
 * @date 2022/11/26 21:49
 */
public class ChatController extends ChatInit implements IChatMethod{

    private ChatEventDefine chatEventDefine;

    private ChatView chatView;

    public ChatController(IChatEvent chatEvent) {
        super(chatEvent);
    }
    //通过调用
    @Override
    public void initView() {
        chatView = new ChatView(this, chatEvent);
    }

    @Override
    public void initEventDefine() {
        chatEventDefine = new ChatEventDefine(this, this); //会进行窗口和侧边栏图标的变换
    }

    @Override
    public void doShow() {
        super.show(); //这里会调用父类Stage的show方法
    }

    @Override
    public void setUserInfo(String userId, String userNickName, String userHead) {
        super.userId = userId;
        super.userNickName = userNickName;
        super.userHead = userHead;
        Button button = getElement("bar_portrait", Button.class);
        button.setStyle(String.format("-fx-background-image: url('/fxml/chat/img/head/%s.png')", userHead));
    }

    /**
     * 将elementTalk中的pane添加到ListView中
     * 一开始先判断缓存中是否有elementTalk
     *    - 如果缓存中有talkId对应的elementTalk元素
     *      - 如果ListView中没有elementTalk的面板，则添加进ListView中
     *      - 如果有，则判断是否选中，没有选中进行选中
     *    - 如果缓存中没有talkId对应的elementTalk元素
     *      - 将elementTalk元素添加，并将pane添加进ListView中。
     * @param talkIdx 对话框在ListView中的位置（0， -1）
     * @param talkType 对话框的类型，是好友还是群聊（0，1）
     * @param talkId 对话框id，做缓存时使用
     * @param talkName 对话框用户名称
     * @param talkHead  对话框用户头像
     * @param talkSketch 对话框附加信息
     * @param talkDate  对话框日期
     * @param selected 对话框是否选中
     * @author hourui
     * @date 2022/11/29 20:47
     * @return void
     */
    @Override
    public void addTalkBox(int talkIdx, Integer talkType, String talkId, String talkName, String talkHead, String talkSketch, Date talkDate, Boolean selected) {
        ListView talkList = getElement("talkList", ListView.class);
        // 判断会话框是否有该对象
        ElementTalk elementTalk = CacheUtil.talkMap.get(talkId);
        if(elementTalk != null){
            Node talkNode = talkList.lookup("#" + IdUtil.createTalkPaneId(talkId));
            if(talkNode == null){
                talkList.getItems().add(talkIdx, elementTalk.getPane());
                // 填充对话框消息栏
                fillInfoBox(elementTalk, talkName);
            }
            if(selected){
                //选中ListView中的对应面板Pane元素
                talkList.getSelectionModel().select(elementTalk.getPane());
            }
            // 填充对话框消息栏
            fillInfoBox(elementTalk, talkName);
            return;
        }
        //增加到缓存
        ElementTalk talkElement = new ElementTalk(talkId, talkType, talkName, talkHead, talkSketch, talkDate);
        CacheUtil.talkMap.put(talkId, talkElement);
        //填充到对话框
        ObservableList items = talkList.getItems();
        Pane pane = talkElement.getPane();
        if(talkIdx >= 0){
            items.add(talkIdx, pane);  //添加到第一个位置
        }else{
            items.add(pane); //否则顺序末尾添加
        }
        if(selected){
            //默认选中ListView中的对应面板Pane元素
            talkList.getSelectionModel().select(pane);
        }
        // 对话框元素点击事件
        pane.setOnMousePressed(event -> {
            System.out.println("点击对话框：" + talkName);
            // 填充消息栏
            fillInfoBox(talkElement, talkName);
            // 清除消息提醒
            Label msgRemind = talkElement.msgRemind();
            msgRemind.setUserData(new RemindCount(0));
            msgRemind.setVisible(false);
        });
        // 鼠标事件移入，删除按钮展现
        pane.setOnMouseEntered(event -> {
            talkElement.getDelete().setVisible(true);
        });
        pane.setOnMouseExited(event -> {
            talkElement.getDelete().setVisible(false);
        });
        // 填充对话框消息栏
        fillInfoBox(talkElement, talkName);
        // 从对话框中删除
        talkElement.getDelete().setOnMouseClicked(event -> {
            talkList.getItems().remove(pane);
            getElement("info_pane_box", Pane.class).getChildren().clear();
            getElement("info_pane_box", Pane.class).setUserData(null);
            getElement("info_name", Label.class).setText("");
            talkElement.getInfoBoxList().getItems().clear();
            talkElement.clearMsgSketch();
            System.out.println("删除对话框通知");
        });
    }
    /**
     * 私有方法
     * 填充对话框消息内容
     *
     * @param talkElement 对话框元素
     * @param talkName    对话框名称
     */
    private void fillInfoBox(ElementTalk talkElement, String talkName) {
        String talkId = talkElement.getPane().getUserData().toString();
        // 填充对话列表
        Pane info_pane_box = getElement("info_pane_box", Pane.class);
        String boxUserId = (String) info_pane_box.getUserData();
        // 判断是否已经填充[talkId]，当前已填充则返回
        if (talkId.equals(boxUserId)) return;
        ListView<Pane> listView = talkElement.getInfoBoxList();
        info_pane_box.setUserData(talkId);
        info_pane_box.getChildren().clear();
        info_pane_box.getChildren().add(listView);
        // 对话框名称
        Label info_name = getElement("info_name", Label.class);
        info_name.setText(talkName);
    }

    @Override
    public void addTalkMsgUserLeft(String talkId, String msg, Date msgDate, Boolean idxFirst, Boolean selected, Boolean isRemind) {
        ElementTalk talkElement = CacheUtil.talkMap.get(talkId);
        ListView<Pane> listView = talkElement.getInfoBoxList();
        TalkData talkUserData = (TalkData) listView.getUserData();
        Pane left = new ElementInfoBox().left(talkUserData.getTalkName(), talkUserData.getTalkHead(), msg);
        // 消息填充
        listView.getItems().add(left);
        // 滚动条
        listView.scrollTo(left);
        talkElement.fillMsgSketch(msg, msgDate);
        // 设置位置&选中
        chatView.updateTalkListIdxAndSelected(0, talkElement.getPane(), talkElement.msgRemind(), idxFirst, selected, isRemind);
        // 填充对话框聊天窗口
        fillInfoBox(talkElement, talkUserData.getTalkName());
    }

    @Override
    public void addTalkMsgGroupLeft(String talkId, String userId, String userNickName, String userHead, String msg, Date msgDate, Boolean idxFirst, Boolean selected, Boolean isRemind) {
        // 自己的消息抛弃
        if (super.userId.equals(userId)) return;
        ElementTalk talkElement = CacheUtil.talkMap.get(talkId);
        if (null == talkElement) {
            GroupsData groupsData = (GroupsData) getElement(IdUtil.createFriendGroupId(talkId), Pane.class).getUserData();
            if (null == groupsData) return;
            addTalkBox(0, 1, talkId, groupsData.getGroupName(), groupsData.getGroupHead(), userNickName + "：" + msg, msgDate, false);
            talkElement = CacheUtil.talkMap.get(talkId);
            // 事件通知(开启与群组发送消息)
            System.out.println("事件通知(开启与群组发送消息)");
        }
        ListView<Pane> listView = talkElement.getInfoBoxList();
        TalkData talkData = (TalkData) listView.getUserData();
        Pane left = new ElementInfoBox().left(userNickName, userHead, msg);
        // 消息填充
        listView.getItems().add(left);
        // 滚动条
        listView.scrollTo(left);
        talkElement.fillMsgSketch(userNickName + "：" + msg, msgDate);
        // 设置位置&选中
        chatView.updateTalkListIdxAndSelected(1, talkElement.getPane(), talkElement.msgRemind(), idxFirst, selected, isRemind);
        // 填充对话框聊天窗口
        fillInfoBox(talkElement, talkData.getTalkName());
    }

    @Override
    public void addTalkMsgRight(String talkId, String msg, Date msgData, Boolean idxFirst, Boolean selected, Boolean isRemind) {
        ElementTalk talkElement = CacheUtil.talkMap.get(talkId);
        ListView<Pane> listView = talkElement.getInfoBoxList();
        Pane right = new ElementInfoBox().right(userNickName, userHead, msg);
        // 消息填充
        listView.getItems().add(right);
        // 滚动条
        listView.scrollTo(right);
        talkElement.fillMsgSketch(msg, msgData);
        // 设置位置&选中
        chatView.updateTalkListIdxAndSelected(0, talkElement.getPane(), talkElement.msgRemind(), idxFirst, selected, isRemind);
    }
}
