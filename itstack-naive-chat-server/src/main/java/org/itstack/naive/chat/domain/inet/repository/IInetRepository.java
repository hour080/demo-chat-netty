package org.itstack.naive.chat.domain.inet.repository;

import org.itstack.naive.chat.domain.inet.model.ChannelUserInfo;
import org.itstack.naive.chat.domain.inet.model.ChannelUserReq;

import java.util.List;

public interface IInetRepository {
    //查询通信用户数量
    Long queryChannelUserCount(ChannelUserReq req);
    //查询通信用户列表
    List<ChannelUserInfo> queryChannelUserList(ChannelUserReq req);
}
