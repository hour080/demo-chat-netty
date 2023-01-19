package org.itstack.naive.chat.infrastructure.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 聊天记录
 * @author hourui
 * @version 1.0
 * @Description
 * @date 2023/1/13 22:28
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatRecord {
    private Long id;           // 自增ID
    private String userId;     // 用户ID
    private String friendId;   // 好友ID/TalkId/群组ID
    private String msgContent; // 消息内容
    private Integer msgType;   // 消息类型；0文字消息、1固定表情
    private Date msgDate;      // 消息时间
    private Integer talkType;  // 对话框类型；0好友、1群组
    private Date createTime;   // 创建时间
    private Date updateTime;   // 更新时间
}
