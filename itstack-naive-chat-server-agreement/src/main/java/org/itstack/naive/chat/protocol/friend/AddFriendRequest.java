package org.itstack.naive.chat.protocol.friend;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.itstack.naive.chat.protocol.Command;
import org.itstack.naive.chat.protocol.Packet;

/**
 * 添加好友的请求类对象
 * @author hourui
 * @version 1.0
 * @Description
 * @date 2023/1/19 19:32
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddFriendRequest extends Packet {
    private String userId;
    private String friendId;
    @Override
    public byte getCommand() {
        return Command.AddFriendRequest;
    }
}
