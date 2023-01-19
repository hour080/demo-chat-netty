package org.itstack.navice.chat.ui.view.login;

import org.itstack.navice.chat.ui.view.chat.IChatMethod;

/**
 * TODO
 *  窗体的控制管理类
 * @author hourui
 * @version 1.0
 * @Description
 * @date 2022/11/25 15:16
 */
public class LoginController extends LoginInit implements ILoginMethod{
    private LoginView loginView; //初始化页面舞台场景，按钮等元素  LoginInit + ILoginEvent
    //给最小化，退出和登陆按钮绑定事件，包括LoginInit中的鼠标移动事件和ILoginEvent中的登陆信息的处理事件
    private LoginEventDefine loginEventDefine;   //LoginInit + ILoginEvent + loginMethod

    private IChatMethod chat;

    public LoginController(ILoginEvent loginEvent, IChatMethod chat) {
        //执行父类LoginInit的有参初始化，传入一个具体的loginEvent。
        //然后调用initView和initEventDefine方法来初始化登陆视图和初始化事件定义
        super(loginEvent);
        this.chat = chat; //ChatController
    }

    @Override
    public void doShow() {
        super.show();
    }

    @Override
    public void doLoginError() {
        System.out.println("登陆失败，执行提示操作");
    }

    @Override
    public void doLoginSuccess() {
        System.out.println("登陆成功，执行跳转操作");
        close(); //关闭原窗口
        // 打开聊天窗口
        chat.doShow();
    }

    @Override
    public void initView() {
        loginView = new LoginView(this, loginEvent);
    }

    /**
     * 在LoginEventDefine的构造方法中执行以下方法
     * 1.为按钮（最小化，退出，登陆）指定点击事件。
     * 2.在move方法中为鼠标的按下，拖动和释放指定对应的事件
     * @author hourui
     * @date 2022/11/26 21:14
     * @return void
     */
    @Override
    public void initEventDefine() {
        loginEventDefine = new LoginEventDefine(this, loginEvent, this); //事件的初始化
    }
}
