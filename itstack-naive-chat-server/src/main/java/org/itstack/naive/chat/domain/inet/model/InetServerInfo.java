package org.itstack.naive.chat.domain.inet.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author hourui
 * @version 1.0
 * @Description
 * @date 2023/1/28 15:53
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class InetServerInfo {
    private String ip; //服务端的ip地址
    private int port; //服务端的端口号
    private boolean status; //服务端的状态 true表示开启 false表示关闭
}
