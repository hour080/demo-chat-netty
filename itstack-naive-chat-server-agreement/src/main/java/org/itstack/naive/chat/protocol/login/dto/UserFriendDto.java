package org.itstack.naive.chat.protocol.login.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author hourui
 * @version 1.0
 * @Description
 * @date 2023/1/16 22:11
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserFriendDto {
    private String friendId; // 好友ID
    private String friendName; // 好友名称
    private String friendHead; // 好友头像
}
