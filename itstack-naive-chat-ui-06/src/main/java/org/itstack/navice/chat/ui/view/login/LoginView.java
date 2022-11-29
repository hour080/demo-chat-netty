package org.itstack.navice.chat.ui.view.login;

/**
 * TODO
 * 窗体的展示，主要用于扩展一些随着用户操作新展示的元素，例如后续在聊天窗体新增的消息提醒等
 * @author hourui
 * @version 1.0
 * @Description
 * @date 2022/11/25 15:19
 */
public class LoginView {
    private LoginInit loginInit; //窗体的初始化操作，包括为舞台Stage设置Scene场景，其中的按钮，文本框进行赋值。初始化页面和初始化事件定义
    private ILoginEvent loginEvent; //事件接口类，对登陆传来的消息进行处理

    public LoginView(LoginInit loginInit, ILoginEvent loginEvent) {
        this.loginInit = loginInit;
        this.loginEvent = loginEvent;
    }

}
