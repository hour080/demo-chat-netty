package org.itstack.naive.chat.protocol.msg;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.itstack.naive.chat.protocol.Command;
import org.itstack.naive.chat.protocol.Packet;

import java.util.Date;

/**
 * 消息请求类, 即客户端发送消息时，发送给服务端的请求类
 * @author hourui
 * @version 1.0
 * @Description
 * @date 2023/1/26 21:05
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MsgRequest extends Packet {
    private String userId;   //用户id
    private String friendId;  //好友id
    private String msgText;  //消息内容
    private Integer msgType; //消息类型，0表示文字消息 1表示表情
    private Date msgDate; //发送消息的时间

    @Override
    public byte getCommand() {
        return Command.MsgRequest;
    }
}
