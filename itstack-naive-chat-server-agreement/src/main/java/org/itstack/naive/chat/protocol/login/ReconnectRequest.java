package org.itstack.naive.chat.protocol.login;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.itstack.naive.chat.protocol.Command;
import org.itstack.naive.chat.protocol.Packet;

/**
 * 断线重连请求协议
 * 简单实现只包括用户id
 * 具体应包括重连时间、重连设备、网络环境，以及当前对话的聊天记录暂存和
 * @author hourui
 * @version 1.0
 * @Description
 * @date 2023/1/28 09:26
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReconnectRequest extends Packet {
    private String userId;

    @Override
    public byte getCommand() {
        return Command.ReconnectRequest;
    }
}
