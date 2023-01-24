package org.itstack.naive.chat.protocol.talk;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.itstack.naive.chat.protocol.Command;
import org.itstack.naive.chat.protocol.Packet;

import java.util.Date;

/**
 * 发起对话响应
 * 添加一个对话框，返回对话框的具体信息
 * @author hourui
 * @version 1.0
 * @Description
 * @date 2023/1/23 21:53
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TalkNoticeResponse extends Packet {
    private String talkId; //对话框id
    private String talkName; //对话框名称,包括好友名称和群名称
    private String talkHead; //对话框头像
    private String talkSketch; //消息通信简述
    private Date talkDate; //消息时间

    @Override
    public byte getCommand() {
        return Command.TalkNoticeResponse;
    }
}
