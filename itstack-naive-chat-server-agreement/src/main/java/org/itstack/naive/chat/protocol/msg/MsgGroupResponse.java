package org.itstack.naive.chat.protocol.msg;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.itstack.naive.chat.protocol.Command;
import org.itstack.naive.chat.protocol.Packet;

import java.util.Date;

/**
 * 群发消息应答协议, 这里需要知道用户的id，昵称，头像以及内容，这样才可以在群组右侧面板中进行聊天记录的填充
 * @author hourui
 * @version 1.0
 * @Description
 * @date 2023/1/27 10:51
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MsgGroupResponse extends Packet {
    private String talkId; //对话框id,这里也就是群组id，通过对话框id来获得对话框的右侧聊天界面面板
    private String userId;
    private String userNickName;
    private String userHead;
    private String msgText;
    private Integer msgType;
    private Date msgDate;

    @Override
    public byte getCommand() {
        return Command.MsgGroupResponse;
    }
}
