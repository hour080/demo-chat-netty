package org.itstack.naive.chat.domain.user.service;

import org.itstack.naive.chat.application.UserService;
import org.itstack.naive.chat.domain.user.model.UserInfo;
import org.itstack.naive.chat.infrastructure.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * TODO
 *
 * @author hourui
 * @version 1.0
 * @Description
 * @date 2023/1/16 23:16
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;
    @Override
    public boolean checkAuth(String userId, String userPassword) {
        String authCode = userRepository.queryUserPassword(userId);
        return authCode.equals(userPassword);
    }

    @Override
    public UserInfo queryUserInfo(String userId) {
        return userRepository.queryUserInfo(userId);
    }
}
