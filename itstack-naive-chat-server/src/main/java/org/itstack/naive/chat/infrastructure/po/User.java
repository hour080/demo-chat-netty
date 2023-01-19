package org.itstack.naive.chat.infrastructure.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 用户信息
 * @author hourui
 * @version 1.0
 * @Description
 * @date 2023/1/13 22:32
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private Long id;
    private String userId;
    private String userNickName;
    private String userHead;
    private String userPassword;
    private Date createTime;
    private Date updateTime;
}
