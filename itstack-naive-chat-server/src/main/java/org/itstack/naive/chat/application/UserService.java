package org.itstack.naive.chat.application;

import org.itstack.naive.chat.domain.user.model.UserInfo;

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
}
