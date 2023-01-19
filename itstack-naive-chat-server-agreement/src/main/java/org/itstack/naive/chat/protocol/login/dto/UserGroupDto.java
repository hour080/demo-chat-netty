package org.itstack.naive.chat.protocol.login.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * TODO
 *
 * @author hourui
 * @version 1.0
 * @Description
 * @date 2023/1/16 22:10
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserGroupDto {
    private String groupId;
    private String groupName;
    private String groupHead;
}
