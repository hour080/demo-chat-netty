package org.itstack.navice.chat.ui.view.chat.data;

/**
 * 聊天框（包括好友对话TalkData和群组对话GroupsData）
 * @author hourui
 * @version 1.0
 * @Description
 * @date 2022/11/29 14:07
 */
public class TalkBoxData {
    private String talkId;  //对话框id
    private Integer talkType; //对话框类型 好友 0、群组 1
    private String talkName;  //对话框名称
    private String talkHead;  //对话框头像

    public TalkBoxData() {

    }

    public TalkBoxData(String talkId, Integer talkType, String talkName, String talkHead) {
        this.talkId = talkId;
        this.talkType = talkType;
        this.talkName = talkName;
        this.talkHead = talkHead;
    }

    public String getTalkId() {
        return talkId;
    }

    public void setTalkId(String talkId) {
        this.talkId = talkId;
    }

    public Integer getTalkType() {
        return talkType;
    }

    public void setTalkType(Integer talkType) {
        this.talkType = talkType;
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
