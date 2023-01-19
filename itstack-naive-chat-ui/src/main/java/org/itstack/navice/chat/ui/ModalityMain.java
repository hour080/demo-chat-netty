package org.itstack.navice.chat.ui;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.ArrayList;

/**
 * TODO
 *
 * @author hourui
 * @version 1.0
 * @Description
 * @date 2023/1/3 00:04
 */
public class ModalityMain extends Application
{
    @Override
    public void start(Stage stage) throws Exception {

        //Modality有两个静态类方法
        //valueOf(String s)这个String必须和枚举名称一模一样，必须大写。
        //    NONE,    定义非模态且不阻止任何其他窗口的顶级窗口。
        //    WINDOW_MODAL,定义一个模式窗口，该窗口阻止事件传递到其整个所有者窗口层次结构。
        //    APPLICATION_MODAL;定义一个模式窗口，该窗口阻止事件传递到任何其他应用程序窗口。
        Modality application = Modality.valueOf("APPLICATION_MODAL");
        System.out.println(application.name());

        //values()  返回一个数组，枚举对象。
        //    NONE,
        //    WINDOW_MODAL,
        //    APPLICATION_MODAL;
        Modality[] values = Modality.values();
        System.out.println(values.length);
        for (Modality modality : values) {
            System.out.println(modality);
        }

        //Modality对象具体只有几个方法需要注意的
        //获得当前枚举对象的序号
        System.out.println(application.ordinal());
        //toString()和name()返回的结果一样
        System.out.println(application.toString());
        //两个对象比较。是两个枚举对象的序号相减
        application.compareTo(application);

        //不懂的不用在意
        VBox box = new VBox();
        box.setSpacing(20);
        Button button1 = new Button("None");
        Button button2 = new Button("Window_modal");
        Button button3 = new Button("Application_modal");
        box.getChildren().addAll(button1,button2,button3);

        //1.None情况
        Stage stage1 = new Stage();
        stage1.initModality(Modality.NONE);
        button1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                stage1.show();
            }
        });

        //2.Window_modal情况.重要是需要执行initOwner
        Stage stage2 = new Stage();
        stage2.initModality(Modality.WINDOW_MODAL);
        stage2.initOwner(stage);
        button2.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                stage2.show();
            }
        });

        //3.Application_modal情况
        Stage stage3 = new Stage();
        stage3.initModality(Modality.APPLICATION_MODAL);
        button3.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                stage3.show();
            }
        });


        stage.setScene(new Scene(box));
        stage.setTitle("JavaFX");
        stage.setWidth(400);
        stage.setHeight(400);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
