package org.itstack.naive.chat.domain.inet.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 通信用户的状态信息
 * @author hourui
 * @version 1.0
 * @Description
 * @date 2023/1/28 15:58
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChannelUserInfo {
    private String userId;
    private String userNickName;
    private String userHead;
    private boolean status;  //通信用户的状态，true表示在线，false表示不在线
}
