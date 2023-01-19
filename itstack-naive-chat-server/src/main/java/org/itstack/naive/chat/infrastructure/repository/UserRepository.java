package org.itstack.naive.chat.infrastructure.repository;

import org.itstack.naive.chat.domain.user.model.UserInfo;
import org.itstack.naive.chat.infrastructure.dao.IUserDao;
import org.itstack.naive.chat.infrastructure.po.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;

/**
 * TODO
 *
 * @author hourui
 * @version 1.0
 * @Description
 * @date 2023/1/16 23:18
 */
@Repository
public class UserRepository {

    @Resource
    private IUserDao userDao;

    public String queryUserPassword(String userId) {
        return userDao.queryUserPassword(userId);
    }

    public UserInfo queryUserInfo(String userId) {
        User user = userDao.queryUserInfo(userId);
        return new UserInfo(user.getUserId(), user.getUserNickName(), user.getUserHead());
    }
}
