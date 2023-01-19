package org.itstack.naive.chat.domain.user.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用户信息对象，也就是dao层User对象对应的值对象
 * @author hourui
 * @version 1.0
 * @Description
 * @date 2023/1/16 23:00
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserInfo {
    private String userId;
    private String userNickName;
    private String userHead;
}
