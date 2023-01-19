package org.itstack.naive.chat.infrastructure.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 群组
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Groups {

    private Long id;          // 自增ID
    private String groupId;   // 群组ID
    private String groupName; // 群组名称
    private String groupHead; // 群组头像
    private Date createTime;  // 创建时间
    private Date updateTime;  // 更新时间

}
