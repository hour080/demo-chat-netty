package org.itstack.navice.chat.ui.view.face;


import org.itstack.navice.chat.ui.view.UIObject;
import org.itstack.navice.chat.ui.view.chat.ChatInit;
import org.itstack.navice.chat.ui.view.chat.IChatEvent;
import org.itstack.navice.chat.ui.view.chat.IChatMethod;
import org.itstack.navice.chat.ui.view.face.FaceEventDefine;
import org.itstack.navice.chat.ui.view.face.FaceInit;
import org.itstack.navice.chat.ui.view.face.FaceView;
import org.itstack.navice.chat.ui.view.face.IFaceMethod;

/**
 * 微信公众号：bugstack虫洞栈 | 欢迎关注学习专题案例
 * 论坛：http://bugstack.cn
 * Create by 小傅哥 on @2019
 */
public class FaceController extends FaceInit implements IFaceMethod {

    private FaceView faceView;

    public FaceController(ChatInit chatInit, IChatEvent chatEvent, IChatMethod chatMethod) {
        this.chatInit = chatInit;
        this.chatEvent = chatEvent;
        this.chatMethod = chatMethod;
    }

    @Override
    public void initView() {
        faceView = new FaceView(this);
    }

    @Override
    public void initEventDefine() {
        new FaceEventDefine(this);
    }

    @Override
    public void doShowFace(Double x, Double y) {
        setX(x + 230 * (1 - 0.618));  // 设置位置X
        setY(y - 160);                      // 设置位置Y
        show();                             // 展示窗口
    }

}
