package org.itstack.naive.chat.domain.user.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * TODO
 *
 * @author hourui
 * @version 1.0
 * @Description
 * @date 2023/1/19 21:39
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LuckUserInfo {
    private String userId;
    private String userNickName;
    private String userHead;
    private Integer status; //搜索好友的状态[0:未添加，1[保留]、2已添加]
}
