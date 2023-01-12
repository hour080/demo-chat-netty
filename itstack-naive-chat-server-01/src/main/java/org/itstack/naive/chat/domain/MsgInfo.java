package org.itstack.naive.chat.domain;

/**
 * 传输对象
 * @author hourui
 * @version 1.0
 * @Description
 * @date 2023/1/11 20:54
 */
public class MsgInfo {
    private String channelId;
    private String msgContent;

    public MsgInfo() {
    }

    public MsgInfo(String channelId, String msgContent) {
        this.channelId = channelId;
        this.msgContent = msgContent;
    }

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public String getMsgContent() {
        return msgContent;
    }

    public void setMsgContent(String msgContent) {
        this.msgContent = msgContent;
    }

    @Override
    public String toString() {
        return "MsgInfo{" +
                "channelId='" + channelId + '\'' +
                ", msgContent='" + msgContent + '\'' +
                '}';
    }
}
