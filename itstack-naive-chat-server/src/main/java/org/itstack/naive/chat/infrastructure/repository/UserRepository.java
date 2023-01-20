package org.itstack.naive.chat.infrastructure.repository;

import org.itstack.naive.chat.domain.user.model.LuckUserInfo;
import org.itstack.naive.chat.domain.user.model.UserInfo;
import org.itstack.naive.chat.domain.user.repository.IUserRepository;
import org.itstack.naive.chat.infrastructure.dao.IUserDao;
import org.itstack.naive.chat.infrastructure.dao.IUserFriendDao;
import org.itstack.naive.chat.infrastructure.po.User;
import org.itstack.naive.chat.infrastructure.po.UserFriend;
import org.itstack.naive.chat.protocol.friend.dto.UserDto;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * TODO
 *
 * @author hourui
 * @version 1.0
 * @Description
 * @date 2023/1/16 23:18
 */
@Repository
public class UserRepository implements IUserRepository {

    @Resource
    private IUserDao userDao;

    @Resource
    private IUserFriendDao userFriendDao;

    public String queryUserPassword(String userId) {
        return userDao.queryUserPassword(userId);
    }

    public UserInfo queryUserInfo(String userId) {
        User user = userDao.queryUserInfo(userId);
        return new UserInfo(user.getUserId(), user.getUserNickName(), user.getUserHead());
    }

    @Override
    public List<LuckUserInfo> queryUserInfoListBySearchKey(String userId, String searchKey) {
        List<LuckUserInfo> luckUserInfoList = new ArrayList<>();
        //返回符合搜索条件的所有用户，并不包括当前好友
        List<User> userList = userDao.queryUserInfoListBySearchKey(userId, searchKey);
        for (User user : userList) {
            //一开始所有返回的用户都是未添加的状态
            LuckUserInfo luckUserInfo = new LuckUserInfo(user.getUserId(), user.getUserNickName(), user.getUserHead(), 0);
            //判断当前返回的用户是否已经是userId的好友
            UserFriend userFriend = new UserFriend();
            userFriend.setUserId(userId);
            userFriend.setUserFriendId(user.getUserId());
            userFriend = userFriendDao.queryUserFriendById(userFriend);
            if(userFriend != null){
                //如果已经是userId的好友，则表示已添加
                luckUserInfo.setStatus(2);
            }
            luckUserInfoList.add(luckUserInfo);
        }
        return luckUserInfoList;

    }

    @Override
    public void addUserFriend(List<UserFriend> userFriendList) {
        userFriendDao.addUserFriend(userFriendList);
    }

}
