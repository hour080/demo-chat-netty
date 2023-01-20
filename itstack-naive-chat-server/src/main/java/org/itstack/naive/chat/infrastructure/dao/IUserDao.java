package org.itstack.naive.chat.infrastructure.dao;

import org.apache.ibatis.annotations.Param;
import org.itstack.naive.chat.infrastructure.po.User;
import org.itstack.naive.chat.protocol.friend.dto.UserDto;

import java.util.List;

public interface IUserDao {
    String queryUserPassword(@Param("userId") String userId);

    User queryUserInfo(@Param("userId")String userId);

    List<User> queryUserInfoListBySearchKey(@Param("userId") String userId, @Param("searchKey") String searchKey);
}
