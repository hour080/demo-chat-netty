package org.itstack.naive.chat.protocol.friend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用户信息类
 * @author hourui
 * @version 1.0
 * @Description
 * @date 2023/1/19 20:03
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private String userId;
    private String userNickName;
    private String userHead;
    private Integer status; //当前用户的状态，0表示未添加，2表示已添加
}
