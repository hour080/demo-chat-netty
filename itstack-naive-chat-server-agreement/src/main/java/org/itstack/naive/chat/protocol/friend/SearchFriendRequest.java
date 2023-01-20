package org.itstack.naive.chat.protocol.friend;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.itstack.naive.chat.protocol.Command;
import org.itstack.naive.chat.protocol.Packet;

/**
 * 搜索好友的请求
 * @author hourui
 * @version 1.0
 * @Description
 * @date 2023/1/19 19:55
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SearchFriendRequest extends Packet {
    private String userId; //用户id
    private String searchKey; //搜索字段

    @Override
    public byte getCommand() {
        return Command.SearchFriendRequest;
    }
}
