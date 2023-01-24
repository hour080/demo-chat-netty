package org.itstack.naive.chat.domain.user.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author hourui
 * @version 1.0
 * @Description
 * @date 2023/1/24 14:59
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TalkBoxInfo {
    private Integer talkType;  // 对话框类型；0好友、1群组
    private String talkId;  // 对话框ID(好友ID、群组ID)
    private String talkHead;  // 对话框名称
    private String talkName;  // 对话框头像
    private String talkSketch;  // 消息简述
    private Date talkDate;   // 消息时间
}
