package org.itstack.naive.chat.domain.user.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 聊天记录类
 * @author hourui
 * @version 1.0
 * @Description
 * @date 2023/1/24 17:05
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatRecordInfo {
    private String userId; //用户id
    private String friendId; //好友id
    private String msgContent; //消息内容
    private Integer msgType; //消息类型  0为文字消息，1为表情
    private Date msgDate; //消息时间
    private Integer talkType; //聊天类型  0表示好友 1表示群组
}
