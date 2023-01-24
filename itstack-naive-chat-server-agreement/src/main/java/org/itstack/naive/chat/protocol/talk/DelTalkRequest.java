package org.itstack.naive.chat.protocol.talk;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.itstack.naive.chat.protocol.Command;
import org.itstack.naive.chat.protocol.Packet;

/**
 * 删除对话框请求
 * @author hourui
 * @version 1.0
 * @Description
 * @date 2023/1/23 21:46
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DelTalkRequest extends Packet {
    private String userId; //用户id
    private String talkId; //对话框id

    @Override
    public byte getCommand() {
        return Command.DelTalkRequest;
    }
}
