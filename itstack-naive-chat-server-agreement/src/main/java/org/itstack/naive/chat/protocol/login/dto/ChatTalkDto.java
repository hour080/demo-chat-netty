package org.itstack.naive.chat.protocol.login.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 聊天框对象
 * @author hourui
 * @version 1.0
 * @Description
 * @date 2023/1/16 21:47
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatTalkDto {
    private String talkId;
    private Integer talkType; //聊天类型，0表示与用户聊天，1表示与群组聊天
    private String talkName; //聊天的用户名称
    private String talkHead; //聊天的用户头像
    private String talkSketch; //聊天框的消息简述
    private Date talkDate; //消息时间
}
