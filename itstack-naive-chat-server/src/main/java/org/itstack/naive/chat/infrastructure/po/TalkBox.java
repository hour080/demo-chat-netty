package org.itstack.naive.chat.infrastructure.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 聊天框表， 用于添加聊天框和删除聊天框
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TalkBox {

    private Long id;          // 自增ID
    private String userId;    // 用户ID
    private String talkId;    // 对话框ID(好友ID、群组ID)
    private Integer talkType; // 对话框类型；0好友、1群组
    private Date createTime;  // 创建时间
    private Date updateTime;  // 更新时间
}
