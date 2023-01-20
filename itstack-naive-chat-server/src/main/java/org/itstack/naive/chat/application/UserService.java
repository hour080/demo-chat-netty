package org.itstack.naive.chat.application;

import org.itstack.naive.chat.domain.user.model.LuckUserInfo;
import org.itstack.naive.chat.domain.user.model.UserInfo;
import org.itstack.naive.chat.infrastructure.po.UserFriend;
import org.itstack.naive.chat.protocol.friend.dto.UserDto;

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
}
