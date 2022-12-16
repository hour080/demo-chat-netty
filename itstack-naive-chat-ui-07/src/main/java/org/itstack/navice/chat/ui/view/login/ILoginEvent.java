package org.itstack.navice.chat.ui.view.login;

/**
 * TODO
 * 事件接口类，具体实现交给调用方。
 * 在点击登陆后将属于窗体的功能处理完毕后，对登陆传来的消息进行处理。
 * @author hourui
 * @version 1.0
 * @Description
 * @date 2022/11/25 15:06
 */
public interface ILoginEvent {
    /**
     * 登陆验证
     * @author hourui
     * @date 2022/11/25 15:07
     * @return void
     */
    void doLoginCheck(String userId, String userPassword);
}
