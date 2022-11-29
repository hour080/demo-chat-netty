package org.itstack.navice.chat.ui.view.chat;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.ListView;
import javafx.scene.layout.Pane;
import org.itstack.navice.chat.ui.util.CacheUtil;
import org.itstack.navice.chat.ui.util.IdUtil;
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
    //通过调用
    @Override
    public void initView() {
    }

    @Override
    public void initEventDefine() {
        chatEventDefine = new ChatEventDefine(this); //会进行窗口和侧边栏图标的变换
    }

    @Override
    public void doShow() {
        super.show(); //这里会调用父类Stage的show方法
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
            }
            if(selected){
                //选中ListView中的对应面板Pane元素
                talkList.getSelectionModel().select(elementTalk.getPane());
            }
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
        });
        // 鼠标事件移入，删除按钮展现
        pane.setOnMouseEntered(event -> {
            talkElement.getDelete().setVisible(true);
        });
        pane.setOnMouseExited(event -> {
            talkElement.getDelete().setVisible(false);
        });
        // 从对话框中删除
        talkElement.getDelete().setOnMouseClicked(event -> {
            System.out.println("删除对话框：" + talkName);
            talkList.getItems().remove(pane);
            talkElement.clearMsgSketch();
        });
    }
}
