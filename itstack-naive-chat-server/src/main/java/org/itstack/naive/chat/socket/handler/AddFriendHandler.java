package org.itstack.naive.chat.socket.handler;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class AddFriendHandler extends SimpleChannelInboundHandler<AddFriendRequest> {

    private UserService userService;

    public AddFriendHandler(UserService userService) {
        this.userService = userService;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, AddFriendRequest msg) throws Exception {
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
            ctx.channel().writeAndFlush(response);
            return;
        }
        //查询好友信息, 将好友信息返回给当前用户，这样就可以在当前用户的好友列表中添加好友
        UserInfo userInfo = userService.queryUserInfo(msg.getFriendId());
        ctx.channel().writeAndFlush(new AddFriendResponse(true, userInfo.getUserId(), userInfo.getUserNickName(), userInfo.getUserHead()));
        //这里会抛出空指针异常
        Channel friendChannel = SocketChannelUtil.getChannel(msg.getFriendId());
        if(friendChannel == null){
            log.info("用户id:{}未登陆", msg.getFriendId());
            return;
        }
        //查询个人信息，将个人信息返回给好友，这样好友也可以在其好友列表中添加当前用户
        userInfo = userService.queryUserInfo(msg.getUserId());
        friendChannel.writeAndFlush(new AddFriendResponse(true,userInfo.getUserId(), userInfo.getUserNickName(), userInfo.getUserHead()));
    }
}
