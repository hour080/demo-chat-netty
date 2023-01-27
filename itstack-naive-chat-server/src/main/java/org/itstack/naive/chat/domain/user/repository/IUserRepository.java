package org.itstack.naive.chat.domain.user.repository;

import org.itstack.naive.chat.domain.user.model.*;
import org.itstack.naive.chat.infrastructure.po.UserFriend;

import java.util.List;

/**
 * TODO
 *
 * @author hourui
 * @version 1.0
 * @Description
 * @date 2023/1/19 21:37
 */
public interface IUserRepository {
    /**
     * 根据用户查询密码
     * @param userId
     * @author hourui
     * @date 2023/1/19 21:38
     * @return java.lang.String
     */
    String queryUserPassword(String userId);
    /**
     * 根据id查询用户信息
     * @param userId
     * @author hourui
     * @date 2023/1/19 21:38
     * @return org.itstack.naive.chat.domain.user.model.UserInfo 包括用户id，用户昵称，用户头像
     */
    UserInfo queryUserInfo(String userId);
    /**
     * 根据搜索条件模糊查询用户
     * @param userId
     * @param searchKey
     * @author hourui
     * @date 2023/1/19 21:38
     * @return java.util.List<org.itstack.naive.chat.protocol.friend.dto.UserDto>
     */
    List<LuckUserInfo> queryUserInfoListBySearchKey(String userId, String searchKey);

    /**
     * 添加好友信息
     * @param userFriendList
     * @author hourui
     * @date 2023/1/19 23:03
     * @return void
     */
    void addUserFriend(List<UserFriend> userFriendList);

    /**
     * 添加对话框
     * @param userId 用户id
     * @param talkId 对话框id
     * @param talkType 对话框类型
     * @author hourui
     * @date 2023/1/24 12:06
     * @return void
     */
    void addTalkBoxInfo(String userId, String talkId, Integer talkType);

    void deleteTalkBox(String userId, String talkId);

    List<TalkBoxInfo> queryTalkBoxInfoList(String userId);

    List<ChatRecordInfo> queryChatRecordInfoList(String talkId, String userId, Integer talkType);

    List<UserGroupInfo> queryUserGroupInfoList(String userId);

    List<UserFriendInfo> queryUserFriendInfoList(String userId);
    /**
     * 异步添加聊天消息
     * @param chatRecordInfo
     * @author hourui
     * @date 2023/1/26 22:32
     * @return void
     */
    void appendChatRecordInfo(ChatRecordInfo chatRecordInfo);

    List<String> queryUserGroupIdList(String userId);
}
