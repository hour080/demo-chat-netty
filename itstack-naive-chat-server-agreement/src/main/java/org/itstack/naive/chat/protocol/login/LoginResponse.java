package org.itstack.naive.chat.protocol.login;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.itstack.naive.chat.protocol.Command;
import org.itstack.naive.chat.protocol.Packet;
import org.itstack.naive.chat.protocol.login.dto.ChatTalkDto;
import org.itstack.naive.chat.protocol.login.dto.UserFriendDto;
import org.itstack.naive.chat.protocol.login.dto.UserGroupDto;

import java.util.ArrayList;
import java.util.List;

/**
 * 登陆响应
 * @author hourui
 * @version 1.0
 * @Description
 * @date 2023/1/16 21:36
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponse extends Packet {
    private Boolean isSuccess; //返回登陆是否成功
    private String userId;
    private String userNickName;
    private String userHead;
    private List<ChatTalkDto> chatTalkList = new ArrayList<>(); //用户的聊天框对象列表
    private List<UserGroupDto> userGroupList = new ArrayList<>(); //用户参加的群组列表
    private List<UserFriendDto> userFriendList = new ArrayList<>(); // 用户的好友列表

    public LoginResponse(Boolean isSuccess) {
        this.isSuccess = isSuccess;
    }

    @Override
    public byte getCommand() {
        return Command.LoginResponse;
    }
}
