package org.itstack.naive.chat.protocol.talk;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.itstack.naive.chat.protocol.Command;
import org.itstack.naive.chat.protocol.Packet;

/**
 * 发起对话请求
 * 表示当前用户向好友发起对话请求
 * @author hourui
 * @version 1.0
 * @Description
 * @date 2023/1/23 21:48
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TalkNoticeRequest extends Packet {
    private String userId;
    private String friendId;
    private Integer talkType; //0表示好友，1表示群组

    @Override
    public byte getCommand() {
        return Command.TalkNoticeRequest;
    }
}
