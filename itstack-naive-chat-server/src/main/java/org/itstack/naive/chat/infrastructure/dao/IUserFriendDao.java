package org.itstack.naive.chat.infrastructure.dao;

import org.apache.ibatis.annotations.Param;
import org.itstack.naive.chat.infrastructure.po.UserFriend;

import java.util.List;

public interface IUserFriendDao {
    UserFriend queryUserFriendById(UserFriend userFriend);

    void addUserFriend(@Param("userFriendList") List<UserFriend> userFriendList);

    List<String> queryUserFriendIdList(@Param("userId") String userId);
}
