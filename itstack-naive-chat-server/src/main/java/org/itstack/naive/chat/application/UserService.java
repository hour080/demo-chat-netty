package org.itstack.naive.chat.application;

import org.itstack.naive.chat.domain.user.model.*;
import org.itstack.naive.chat.infrastructure.po.UserFriend;

import java.util.List;

/**
 用户服务
 */
public interface UserService {
    /**
     * 登陆校验
     *
     * @param userId       用户ID
     * @param userPassword 用户密码
     * @return 登陆状态
     */
    boolean checkAuth(String userId, String userPassword);

    /**
     * 查询用户信息
     *
     * @param userId 用户ID
     * @return 用户信息
     */
    UserInfo queryUserInfo(String userId);

    /**
     * 根据搜索条件返回userId的所有好友信息
     * @param userId
     * @param searchKey
     * @author hourui
     * @date 2023/1/19 23:02
     * @return java.util.List<org.itstack.naive.chat.domain.user.model.LuckUserInfo>
     */
    List<LuckUserInfo> queryUserInfoListBySearchKey(String userId, String searchKey);

    /**
     * 将userFriendList中的userFriendId添加到userId的好友表中
     * @param userFriendList
     * @author hourui
     * @date 2023/1/19 23:01
     * @return void
     */
    void addUserFriend(List<UserFriend> userFriendList);

    /**
     * 添加对话框
     * @param userId 用户id
     * @param talkId  对话框id，也就是好友id或者群组id
     * @param talkType 对话框类型
     * @author hourui
     * @date 2023/1/24 11:51
     * @return void
     */
    void addTalkBoxInfo(String userId, String talkId, Integer talkType);

    /**
     * 删除对话框
     * @param userId  用户id
     * @param talkId  对话框id，也就是好友id或者群组id
     * @author hourui
     * @date 2023/1/24 12:43
     * @return void
     */
    void deleteTalkBox(String userId, String talkId);

    /**
     * 出巡当前用户的所有对话框信息
     * @param userId
     * @author hourui
     * @date 2023/1/24 15:08
     * @return java.util.List<org.itstack.naive.chat.domain.user.model.TalkBoxInfo>
     */
    List<TalkBoxInfo> queryTalkBoxInfoList(String userId);

    List<ChatRecordInfo> queryChatRecordInfoList(String talkId, String userId, Integer talkType);

    List<UserGroupInfo> queryUserGroupInfoList(String userId);

    List<UserFriendInfo> queryUserFriendInfoList(String userId);

    /**
     * 异步添加聊天记录
     * @param chatRecordInfo
     * @author hourui
     * @date 2023/1/26 22:28
     * @return void
     */
    void aysncAppendChatRecord(ChatRecordInfo chatRecordInfo);
}
