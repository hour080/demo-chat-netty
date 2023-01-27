package org.itstack.naive.chat.protocol.msg;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.itstack.naive.chat.protocol.Command;
import org.itstack.naive.chat.protocol.Packet;

import java.util.Date;

/**
 * 群发消息请求协议
 * @author hourui
 * @version 1.0
 * @Description
 * @date 2023/1/27 10:48
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MsgGroupRequest extends Packet {
    private String talkId; //群组id
    private String userId; //用户id
    private String msgText; //消息内容
    private Integer msgType; //消息类型 0表示文字消息 1表示表情
    private Date msgDate; //消息时间

    @Override
    public byte getCommand() {
        return Command.MsgGroupRequest;
    }
}
