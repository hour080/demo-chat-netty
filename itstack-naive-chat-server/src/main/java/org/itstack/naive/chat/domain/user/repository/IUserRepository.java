package org.itstack.naive.chat.domain.user.repository;

import org.itstack.naive.chat.domain.user.model.LuckUserInfo;
import org.itstack.naive.chat.domain.user.model.UserInfo;
import org.itstack.naive.chat.infrastructure.po.UserFriend;
import org.itstack.naive.chat.protocol.friend.dto.UserDto;

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
}
