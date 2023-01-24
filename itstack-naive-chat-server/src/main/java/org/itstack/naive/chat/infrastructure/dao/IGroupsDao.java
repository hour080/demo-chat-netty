package org.itstack.naive.chat.infrastructure.dao;

import org.apache.ibatis.annotations.Param;
import org.itstack.naive.chat.infrastructure.po.Groups;

public interface IGroupsDao {

    /**
     * 查询群组信息
     * @param groupId
     * @author hourui
     * @date 2023/1/24 15:30
     * @return org.itstack.naive.chat.infrastructure.po.Groups
     */
    Groups queryGroupsById(@Param("groupId") String groupId);

}
