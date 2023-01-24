package org.itstack.naive.chat.domain.user.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用户好友信息类
 * po ---> info ---> dto
 * @author hourui
 * @version 1.0
 * @Description
 * @date 2023/1/24 21:33
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserFriendInfo {
    private String friendId; // 好友ID
    private String friendName;  // 好友名称
    private String friendHead;  // 好友头像
}
