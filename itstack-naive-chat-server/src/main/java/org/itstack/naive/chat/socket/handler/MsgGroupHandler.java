package org.itstack.naive.chat.socket.handler;

import io.netty.channel.Channel;
import io.netty.channel.group.ChannelGroup;
import org.itstack.naive.chat.application.UserService;
import org.itstack.naive.chat.domain.user.model.ChatRecordInfo;
import org.itstack.naive.chat.domain.user.model.UserInfo;
import org.itstack.naive.chat.infrastructure.common.Constants;
import org.itstack.naive.chat.infrastructure.common.SocketChannelUtil;
import org.itstack.naive.chat.protocol.msg.MsgGroupRequest;
import org.itstack.naive.chat.protocol.msg.MsgGroupResponse;
import org.itstack.naive.chat.socket.MyBizHandler;

/**
 * TODO
 *
 * @author hourui
 * @version 1.0
 * @Description
 * @date 2023/1/27 11:05
 */
public class MsgGroupHandler extends MyBizHandler<MsgGroupRequest> {

    public MsgGroupHandler(UserService userService) {
        super(userService);
    }

    @Override
    protected void channelRead(Channel channel, MsgGroupRequest msg) {
        //获得群组的通信管道
        ChannelGroup channelGroup = SocketChannelUtil.getChannelGroup(msg.getTalkId());
        if(channelGroup == null){
            //这里会存在线程安全问题，也就是多个客户端同时往服务端中添加群组通信管道
            SocketChannelUtil.addChannelGroup(msg.getTalkId(), channel); //群组的通道也就是群组中某个用户与服务端建立的SocketChannel
            channelGroup = SocketChannelUtil.getChannelGroup(msg.getTalkId());
        }
        //异步写入聊天记录
        userService.aysncAppendChatRecord(
                new ChatRecordInfo(msg.getUserId(), msg.getTalkId(),
                        msg.getMsgText(), msg.getMsgType(), msg.getMsgDate(),
                        Constants.TalkType.Group.getCode()));
        //获得当前用户的信息userId, userNickName, userHead
        UserInfo userInfo = userService.queryUserInfo(msg.getUserId());
        MsgGroupResponse msgGroupResponse = new MsgGroupResponse();
        msgGroupResponse.setTalkId(msg.getTalkId());
        msgGroupResponse.setUserId(userInfo.getUserId());
        msgGroupResponse.setUserHead(userInfo.getUserHead());
        msgGroupResponse.setUserNickName(userInfo.getUserNickName());
        msgGroupResponse.setMsgText(msg.getMsgText());
        msgGroupResponse.setMsgType(msg.getMsgType());
        msgGroupResponse.setMsgDate(msg.getMsgDate());
        //向channelGroup中的每一个channel发送消息
        channelGroup.writeAndFlush(msgGroupResponse);
    }
}
