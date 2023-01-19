package org.itstack.naive.chat.infrastructure.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 用户好友
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserFriend {

    private Long id;             // 自增ID
    private String userId;       // 用户ID
    private String userFriendId; // 好友用户ID
    private Date createTime;     // 创建时间
    private Date updateTime;     // 更新时间


}
