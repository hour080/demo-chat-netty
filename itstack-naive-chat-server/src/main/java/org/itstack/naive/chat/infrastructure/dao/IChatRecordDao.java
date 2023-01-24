package org.itstack.naive.chat.infrastructure.dao;

import org.apache.ibatis.annotations.Param;
import org.itstack.naive.chat.infrastructure.po.ChatRecord;

import java.util.List;

public interface IChatRecordDao {

    List<ChatRecord> queryUserChatRecordList(@Param("talkId") String talkId, @Param("userId")String userId);

    List<ChatRecord> queryGroupChatRecordList(@Param("talkId") String talkId, @Param("userId")String userId);
}
