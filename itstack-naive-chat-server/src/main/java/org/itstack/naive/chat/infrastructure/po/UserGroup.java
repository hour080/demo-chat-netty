package org.itstack.naive.chat.infrastructure.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 用户群组
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserGroup {

    private Long id;          // 自增ID
    private String userId;    // 用户ID
    private String groupId;   // 群组ID
    private Date createTime;  // 创建时间
    private Date updateTime;  // 更新时间
}
