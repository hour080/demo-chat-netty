package org.itstack.naive.chat.application;

import org.itstack.naive.chat.domain.inet.model.ChannelUserInfo;
import org.itstack.naive.chat.domain.inet.model.ChannelUserReq;
import org.itstack.naive.chat.domain.inet.model.InetServerInfo;

import java.util.List;

//管理Netty服务端的状态
public interface InetService {
    //查询Netty服务端的状态信息, ip, port, status
    InetServerInfo queryNettyServerInfo();
    //查询通讯用户的数量，也就是连接到服务端的客户端数量
    Long queryChannelUserCount(ChannelUserReq req);
    //查询通信用户列表
    List<ChannelUserInfo> queryChannelUserList(ChannelUserReq req);

}
