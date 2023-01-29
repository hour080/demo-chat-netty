package org.itstack.naive.chat.domain.inet.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.itstack.naive.chat.infrastructure.common.PageReq;

/**
 * 通信用户查询请求
 * 继承分页请求，这样可以进行分页展示
 * @author hourui
 * @version 1.0
 * @Description
 * @date 2023/1/28 16:00
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChannelUserReq extends PageReq {
    private String userId;
    private String userNickName;
}
