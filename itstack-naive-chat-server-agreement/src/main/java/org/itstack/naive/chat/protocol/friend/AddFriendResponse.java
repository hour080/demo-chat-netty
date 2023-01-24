package org.itstack.naive.chat.protocol.friend;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.itstack.naive.chat.protocol.Command;
import org.itstack.naive.chat.protocol.Packet;

/**
 * 添加好友的响应类对象, 返回要添加好友的信息，包括好友的id，昵称和头像
 * @author hourui
 * @version 1.0
 * @Description
 * @date 2023/1/19 19:51
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddFriendResponse extends Packet {
    private boolean isSuccess;
    private String friendId;
    private String friendNickName;
    private String friendHead;

    @Override
    public byte getCommand() {
        return Command.AddFriendResponse;
    }
}
