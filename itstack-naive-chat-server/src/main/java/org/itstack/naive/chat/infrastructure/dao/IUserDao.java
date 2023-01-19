package org.itstack.naive.chat.infrastructure.dao;

import org.apache.ibatis.annotations.Param;
import org.itstack.naive.chat.infrastructure.po.User;

public interface IUserDao {
    String queryUserPassword(@Param("userId") String userId);

    User queryUserInfo(@Param("userId")String userId);
}
