package org.itstack.naive.chat.protocol.msg;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.itstack.naive.chat.protocol.Command;
import org.itstack.naive.chat.protocol.Packet;

import java.util.Date;

/**
 * 发送消息响应类
 * @author hourui
 * @version 1.0
 * @Description
 * @date 2023/1/26 21:08
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MsgResponse extends Packet {
    private String friendId;  //对方的id
    private String msgText;
    private Integer msgType; //消息类型，0表示文字消息 1表示表情
    private Date msgDate;

    @Override
    public byte getCommand() {
        return Command.MsgResponse;
    }
}
