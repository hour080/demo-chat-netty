package org.itstack.naive.chat.socket.handler;

import io.netty.channel.Channel;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.itstack.naive.chat.application.UserService;
import org.itstack.naive.chat.domain.user.model.UserInfo;
import org.itstack.naive.chat.infrastructure.common.SocketChannelUtil;
import org.itstack.naive.chat.infrastructure.po.UserFriend;
import org.itstack.naive.chat.protocol.friend.AddFriendRequest;
import org.itstack.naive.chat.protocol.friend.AddFriendResponse;
import org.itstack.naive.chat.socket.MyBizHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * 处理添加用户请求的入站处理器
 * @author hourui
 * @version 1.0
 * @Description
 * @date 2023/1/19 22:54
 */
public class AddFriendHandler extends MyBizHandler<AddFriendRequest> {

    public AddFriendHandler(UserService userService) {
        super(userService);
    }

    @Override
    protected void channelRead(Channel channel, AddFriendRequest msg) {
        //添加好友到数据库中
        List<UserFriend> userFriendList = new ArrayList<>();
        //单纯的一个双向绑定，也就是你添加别人为好友，别人也添加你为好友
        userFriendList.add(new UserFriend(msg.getUserId(), msg.getFriendId()));
        userFriendList.add(new UserFriend(msg.getFriendId(), msg.getUserId()));
        try{
            userService.addUserFriend(userFriendList);
        }catch (Exception e){
            AddFriendResponse response = new AddFriendResponse();
            response.setSuccess(false);
            channel.writeAndFlush(response);
            return;
        }
        //查询好友信息, 将好友信息返回给当前用户
        UserInfo userInfo = userService.queryUserInfo(msg.getFriendId());
        channel.writeAndFlush(new AddFriendResponse(true, userInfo.getUserId(), userInfo.getUserNickName(), userInfo.getUserHead()));
        //这里会抛出空指针异常
        Channel friendChannel = SocketChannelUtil.getChannel(msg.getFriendId());
        //查询个人信息，将个人信息返回给好友
        userInfo = userService.queryUserInfo(msg.getUserId());
        friendChannel.writeAndFlush(new AddFriendResponse(true,userInfo.getUserId(), userInfo.getUserNickName(), userInfo.getUserHead()));
    }

}
