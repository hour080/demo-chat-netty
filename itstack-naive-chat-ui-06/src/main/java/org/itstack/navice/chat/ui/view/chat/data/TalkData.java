package org.itstack.navice.chat.ui.view.chat.data;

/**
 * TODO
 * 与好友聊天的好友数据
 * @author hourui
 * @version 1.0
 * @Description
 * @date 2022/11/29 14:07
 */
public class TalkData {
    private String talkName; //对话框名称
    private String talkHead; //对话框头像

    public TalkData() {
    }

    public TalkData(String talkName, String talkHead) {
        this.talkName = talkName;
        this.talkHead = talkHead;
    }

    public String getTalkName() {
        return talkName;
    }

    public void setTalkName(String talkName) {
        this.talkName = talkName;
    }

    public String getTalkHead() {
        return talkHead;
    }

    public void setTalkHead(String talkHead) {
        this.talkHead = talkHead;
    }
}
