package org.itstack.naive.chat.protocol.friend;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.itstack.naive.chat.protocol.Command;
import org.itstack.naive.chat.protocol.Packet;
import org.itstack.naive.chat.protocol.friend.dto.UserDto;

import java.util.List;

/**
 * 搜索好友响应类
 * @author hourui
 * @version 1.0
 * @Description
 * @date 2023/1/19 20:01
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchFriendResponse extends Packet {
    private List<UserDto> friends;
    @Override
    public byte getCommand() {
        return Command.SearchFriendResponse;
    }
}
