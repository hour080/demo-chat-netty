package org.itstack.navice.chat.ui.view;

import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * TODO
 * UI 父类定义，这是一个抽象类，提供了页面的基础的初始化内容和接口，以及定义抽象方法
 * @author hourui
 * @version 1.0
 * @Description
 * @date 2022/11/25 15:20
 */
public abstract class UIObject extends Stage {
    protected Parent root;
    private double oldStageX;
    private double oldStageY;
    private double oldScreenX;
    private double oldScreenY;
    public <T> T getElement(String id, Class<T> tClass){
        return (T) root.lookup("#" + id);
    }
//    //从所有的列表中清空选中的列表
//    //ListView: 列表 Pane: 面板
//    public void clearViewListSelectedAll(ListView<Pane>... listViews){
//        for(ListView<Pane> listView : listViews){
//            listView.getSelectionModel().clearSelection();
//        }
//    }
    public void move(){
        //lookup()可以通过css选择器的方式来进行UI组件元素的访问。
//        Label label = (Label)root.lookup("#login_logo");
//        System.out.println(label.getText());
        //鼠标按下事件，这个时候记录窗体位置
        root.setOnMousePressed(event -> {
            //getX()是由Stage表示的窗口左上角的位置坐标，event.getScreenX()是鼠标点击的位置在整个屏幕中的X坐标
            //event.getSceneX是鼠标点击的位置在整个场景中的坐标(也就是在开发界面中的相对坐标)
            oldStageX = this.getX();
            oldStageY = this.getY();
            oldScreenX = event.getScreenX();
            oldScreenY = event.getScreenY();
            root.setCursor(Cursor.CLOSED_HAND); //设置鼠标样式为小手
        });
        //鼠标拖动事件，这个设置 setX、setY
        root.setOnMouseDragged(event -> {
            //拖拽前后的鼠标差值(event.getScreenX() - oldScreenX)加上原始窗体坐标值oldStageX就是现在窗体的坐标值位置（左上角）
            this.setX(event.getScreenX() - oldScreenX + oldStageX);
            this.setY(event.getScreenY() - oldScreenY + oldStageY);
            System.out.println(event.getScreenX());
            System.out.println(event.getScreenY());
        });
        //鼠标释放事件，这个时候恢复默认鼠标样式，最终完成了整个窗口的拖拽过程
        root.setOnMouseReleased(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                root.setCursor(Cursor.DEFAULT);
            }
        });
    }
    // 初始化页面
    public abstract void initView();

    // 初始化事件定义
    public abstract void initEventDefine();

    /**
     * 清除listViews中每个listView的选中元素
     * @param listViews
     * @author hourui
     * @date 2022/12/16 10:21
     * @return void
     */
    public void clearViewListSelectedAll(ListView<Pane> ...listViews) {
        for (ListView<Pane> listView : listViews) {
            listView.getSelectionModel().clearSelection();
        }
    }
}
