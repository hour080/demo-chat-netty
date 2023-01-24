package org.itstack.naive.chat.domain.user.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 群组信息
 * @author hourui
 * @version 1.0
 * @Description
 * @date 2023/1/24 20:38
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserGroupInfo {
    private String groupId; //群组id
    private String groupName; //群组名称
    private String groupHead; //群组头像
}
