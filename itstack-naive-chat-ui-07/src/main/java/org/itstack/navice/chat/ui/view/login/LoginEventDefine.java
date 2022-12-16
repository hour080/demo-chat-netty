package org.itstack.navice.chat.ui.view.login;

/**
 * TODO
 * 窗体事件定义，例如将登陆、最小化、退出等在这里完成定义
 * @author hourui
 * @version 1.0
 * @Description
 * @date 2022/11/25 15:20
 */
public class LoginEventDefine {
    private LoginInit loginInit; //窗体初始化
    private ILoginEvent loginEvent; //登陆信息处理
    private ILoginMethod loginMethod; //登陆处理后的后序逻辑

    public LoginEventDefine(LoginInit loginInit, ILoginEvent loginEvent, ILoginMethod loginMethod) {
        this.loginInit = loginInit;
        this.loginEvent = loginEvent;
        this.loginMethod = loginMethod;
        loginInit.move(); //这是UIObject的move方法来设置窗口移动的逻辑
        min();
        quit();
        doEventLogin();
    }
    // 事件；最小化
    private void min() {
        loginInit.login_min.setOnAction(event -> {
            loginInit.setIconified(true);
        });
    }

    // 事件；退出
    private void quit() {
        loginInit.login_close.setOnAction(event -> {
            loginInit.close(); //调用Stage的close方法，关闭舞台。
            System.exit(0);
        });
    }

    // 事件；登陆
    private void doEventLogin() {
        loginInit.login_button.setOnAction(event -> {
            loginEvent.doLoginCheck(loginInit.userId.getText(), loginInit.userPassword.getText());
        });
    }

}
