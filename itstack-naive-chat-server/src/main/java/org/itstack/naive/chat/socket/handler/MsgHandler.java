package org.itstack.naive.chat.socket.handler;

import com.alibaba.fastjson.JSON;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;
import org.itstack.naive.chat.application.UserService;
import org.itstack.naive.chat.domain.user.model.ChatRecordInfo;
import org.itstack.naive.chat.infrastructure.common.Constants;
import org.itstack.naive.chat.infrastructure.common.SocketChannelUtil;
import org.itstack.naive.chat.protocol.msg.MsgRequest;
import org.itstack.naive.chat.protocol.msg.MsgResponse;
import org.itstack.naive.chat.socket.MyBizHandler;

/**
 * 服务端消息入站处理器，这里并不会存在自己有另一方的对话框，而另一方没有本人的对话框
 * 对话框的添加只有在好友界面，点击发送消息，双方会同时弹出对话框
 * @author hourui
 * @version 1.0
 * @Description
 * @date 2023/1/26 22:08
 */
@Slf4j
public class MsgHandler extends SimpleChannelInboundHandler<MsgRequest> {

    private UserService userService;

    public MsgHandler(UserService userService) {
       this.userService = userService;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MsgRequest msg) throws Exception {
        log.info("消息信息处理：{}", JSON.toJSONString(msg));
        //1、将聊天记录异步写入到数据库中
        userService.aysncAppendChatRecord(new ChatRecordInfo(msg.getUserId(),
                msg.getFriendId(), msg.getMsgText(),
                msg.getMsgType(), msg.getMsgDate(), Constants.TalkType.Friend.getCode()));
        //2、添加对话框 [如果对方没有你的对话框则添加，否则直接返回]
        userService.addTalkBoxInfo(msg.getFriendId(), msg.getUserId(), Constants.TalkType.Friend.getCode());
        //获取好友通信管道
        Channel friendChannel = SocketChannelUtil.getChannel(msg.getFriendId());
        if (null == friendChannel) {
            log.info("用户id：{}未登录！", msg.getFriendId());
            return;
        }
        //向好友发送具体的消息信息，让好友填充对应的消息
        friendChannel.writeAndFlush(new MsgResponse(msg.getUserId(), msg.getMsgText(), msg.getMsgType(), msg.getMsgDate()));
    }
}
