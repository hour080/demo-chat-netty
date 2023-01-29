package org.itstack.naive.chat.infrastructure.repository;

import org.itstack.naive.chat.domain.inet.model.ChannelUserInfo;
import org.itstack.naive.chat.domain.inet.model.ChannelUserReq;
import org.itstack.naive.chat.domain.inet.repository.IInetRepository;
import org.itstack.naive.chat.infrastructure.dao.IUserDao;
import org.itstack.naive.chat.infrastructure.po.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author hourui
 * @version 1.0
 * @Description
 * @date 2023/1/28 22:22
 */
@Repository
public class InetRepository implements IInetRepository {
    @Resource
    private IUserDao userDao;
    @Override
    public Long queryChannelUserCount(ChannelUserReq req) {
        return userDao.queryChannelUserCount(req);
    }

    @Override
    public List<ChannelUserInfo> queryChannelUserList(ChannelUserReq req) {
        List<ChannelUserInfo> channelUserInfoList = new ArrayList<>();
        List<User> userList = userDao.queryChannelUserList(req);
        for (User user : userList) {
            ChannelUserInfo channelUserInfo = new ChannelUserInfo();
            channelUserInfo.setUserId(user.getUserId());
            channelUserInfo.setUserHead(user.getUserHead());
            channelUserInfo.setUserNickName(user.getUserNickName());
            channelUserInfoList.add(channelUserInfo);
        }
        return channelUserInfoList;
    }
}
