package org.itstack.naive.chat.protocol.login;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.itstack.naive.chat.protocol.Command;
import org.itstack.naive.chat.protocol.Packet;

/**
 * TODO
 *
 * @author hourui
 * @version 1.0
 * @Description
 * @date 2023/1/16 20:36
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequest extends Packet {
    private String userId;
    private String password;

    @Override
    public byte getCommand() {
        return Command.LoginRequest;
    }
}
