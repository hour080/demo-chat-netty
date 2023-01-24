package org.itstack.naive.chat.infrastructure.dao;

import org.apache.ibatis.annotations.Param;
import org.itstack.naive.chat.infrastructure.po.UserGroup;

import java.util.List;

public interface IUserGroupDao {
    List<String> queryUserGroupIdList(@Param("userId") String userId);
}
