package org.itstack.navice.chat.ui.view.chat;

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
}
