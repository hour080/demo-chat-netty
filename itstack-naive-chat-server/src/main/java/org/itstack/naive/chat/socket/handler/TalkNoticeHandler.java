package org.itstack.naive.chat.socket.handler;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;
import org.itstack.naive.chat.application.UserService;
import org.itstack.naive.chat.domain.user.model.UserInfo;
import org.itstack.naive.chat.infrastructure.common.SocketChannelUtil;
import org.itstack.naive.chat.protocol.talk.TalkNoticeRequest;
import org.itstack.naive.chat.protocol.talk.TalkNoticeResponse;
import org.itstack.naive.chat.socket.MyBizHandler;

import java.util.Date;

/**
 * 服务端处理客户端发来的对话发起请求的处理器
 * @author hourui
 * @version 1.0
 * @Description
 * @date 2023/1/23 22:20
 */
@Slf4j
public class TalkNoticeHandler extends SimpleChannelInboundHandler<TalkNoticeRequest> {

    private UserService userService;
    public TalkNoticeHandler(UserService userService) {
        this.userService = userService;
    }

    /**
     * 服务端接收到增加对话框请求后，则将对话框进行落库操作
     * 之后发送消息通知好友，好友收到对话请求后，会将对话添加到对话框列表中
     * 而当前用户在向服务端发送增加对话框请求前，已经将当前对话添加到自己的对话框列表
     * 因此这个增加对话框是要在好友的对话框列表中增加对话框
     * @param ctx
     * @param msg
     * @author hourui
     * @date 2023/1/24 12:37
     * @return void
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TalkNoticeRequest msg) throws Exception {
        Integer talkType = msg.getTalkType();
        switch (talkType){
            case 0:
                //如果是好友通信
                //1.将两个对话框添加到数据库中
                userService.addTalkBoxInfo(msg.getUserId(), msg.getFriendId(), talkType);
                userService.addTalkBoxInfo(msg.getFriendId(), msg.getUserId(), talkType);
                //2.查询本身的用户信息[将自己发给好友的对话框中]
                UserInfo userInfo = userService.queryUserInfo(msg.getUserId());
                TalkNoticeResponse response = new TalkNoticeResponse();
                response.setTalkId(userInfo.getUserId());
                response.setTalkHead(userInfo.getUserHead());
                response.setTalkName(userInfo.getUserNickName());
                response.setTalkSketch(null);
                response.setTalkDate(new Date());
                //3.获得好友的通道，给好友发送当前用户信息。
                Channel friendChannel = SocketChannelUtil.getChannel(msg.getFriendId());
                if(friendChannel == null){
                    log.info("用户id:{}未登陆!", msg.getFriendId());
                    return;
                }
                //让好友用户将与当前用户的对话框添加到对话框末尾
                friendChannel.writeAndFlush(response);
                break;
            case 1:
                //如果是群组通信，只需要在当前用户添加群组对话框即可，不需要通知群组创建对话框
                userService.addTalkBoxInfo(msg.getUserId(), msg.getFriendId(), talkType);
                break;
            default:
                break;
        }
    }
}
