package org.itstack.naive.chat.infrastructure.dao;

import org.apache.ibatis.annotations.Param;
import org.itstack.naive.chat.infrastructure.po.TalkBox;

import java.util.List;

public interface ITalkBoxDao {
    /**
     * 根据用户id以及对话框id(好友id、群组id)查询对话框
     * @param userId
     * @param talkId
     * @author hourui
     * @date 2023/1/24 12:16
     * @return org.itstack.naive.chat.infrastructure.po.TalkBox
     */
    TalkBox queryTalkBox(@Param("userId") String userId, @Param("talkId") String talkId);

    /**
     * 添加对话框
     * @param talkBox
     * @author hourui
     * @date 2023/1/24 12:21
     * @return void
     */
    void addTalkBox(TalkBox talkBox);

    void deleteTalkBox(@Param("userId")String userId, @Param("talkId")String talkId);

    List<TalkBox> queryTalkBoxList(String userId);
}
