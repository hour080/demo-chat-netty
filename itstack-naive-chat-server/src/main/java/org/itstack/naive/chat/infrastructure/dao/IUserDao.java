package org.itstack.naive.chat.infrastructure.dao;

import org.apache.ibatis.annotations.Param;
import org.itstack.naive.chat.domain.inet.model.ChannelUserInfo;
import org.itstack.naive.chat.domain.inet.model.ChannelUserReq;
import org.itstack.naive.chat.infrastructure.po.User;
import org.itstack.naive.chat.protocol.friend.dto.UserDto;

import java.util.List;

public interface IUserDao {
    String queryUserPassword(@Param("userId") String userId);

    User queryUserByUserId(@Param("userId")String userId);

    List<User> queryUserInfoListBySearchKey(@Param("userId") String userId, @Param("searchKey") String searchKey);

    Long queryChannelUserCount(ChannelUserReq req);

    List<User> queryChannelUserList(ChannelUserReq req);
}
