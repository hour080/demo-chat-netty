package org.itstack.navice.chat.ui.view.login;
/**
 * 方法接口类
 * 主要用于类似登陆处理完毕后，来执行相应方法进行窗体切换或者数据填充
 * @author hourui
 * @date 2022/11/25 15:11
 * @return
 */
public interface ILoginMethod {
    /**
     * 打开登陆窗口
     */
    void doShow();

    /**
     * 登陆失败
     */
    void doLoginError();

    /**
     * 登陆成功；跳转聊天窗口 [关闭登陆窗口，打开新窗口]
     */
    void doLoginSuccess();

}
