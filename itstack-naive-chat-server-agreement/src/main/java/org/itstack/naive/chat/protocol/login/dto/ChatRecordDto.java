package org.itstack.naive.chat.protocol.login.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 聊天消息类，包括用户头像，昵称以及消息
 * @author hourui
 * @version 1.0
 * @Description
 * @date 2023/1/24 15:41
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatRecordDto {
    private String userId; //用户id
    private String friendId; //对话框id
    private String msgContent; // 消息内容
    private Integer msgType; //消息类型，0表示文本，1表示表情
    private Date msgDate; //消息时间
    private String userNickName; //用户昵称
    private String userHead; //用户头像
    private Integer msgUserType; //0表示当前用户，1表示好友
}
