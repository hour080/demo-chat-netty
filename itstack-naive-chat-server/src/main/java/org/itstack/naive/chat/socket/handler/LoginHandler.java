package org.itstack.naive.chat.socket.handler;

import com.alibaba.fastjson.JSON;
import io.netty.channel.Channel;
import org.itstack.naive.chat.application.UserService;
import org.itstack.naive.chat.domain.user.model.*;
import org.itstack.naive.chat.infrastructure.common.SocketChannelUtil;
import org.itstack.naive.chat.protocol.login.LoginRequest;
import org.itstack.naive.chat.protocol.login.LoginResponse;
import org.itstack.naive.chat.protocol.login.dto.ChatRecordDto;
import org.itstack.naive.chat.protocol.login.dto.ChatTalkDto;
import org.itstack.naive.chat.protocol.login.dto.UserFriendDto;
import org.itstack.naive.chat.protocol.login.dto.UserGroupDto;
import org.itstack.naive.chat.socket.MyBizHandler;

import java.util.ArrayList;
import java.util.List;

import static org.itstack.naive.chat.infrastructure.common.Constants.TalkType.Friend;
import static org.itstack.naive.chat.infrastructure.common.Constants.TalkType.Group;

/**
 * 处理客户端登陆请求的入站处理器
 * @author hourui
 * @version 1.0
 * @Description
 * @date 2023/1/16 22:29
 */
public class LoginHandler extends MyBizHandler<LoginRequest> {

    public LoginHandler(UserService userService){
        super(userService);
    }
    @Override
    protected void channelRead(Channel channel, LoginRequest msg) {
        logger.info("登陆请求处理：{}", JSON.toJSONString(msg));
        //进行登陆校验，返回登陆成功或者失败
        boolean auth = userService.checkAuth(msg.getUserId(), msg.getPassword());
        if(!auth){
            channel.writeAndFlush(new LoginResponse(false)); //向客户端返回登陆失败
            return;
        }
        //登陆成功
        //1.记录当前登陆用户和建立的channel之间的绑定关系
        //用户发送消息给其他用户时候，在服务端找到相应的接收消息的用户ID，获取相应的通信管道 Channel 后，进行消息发送
        SocketChannelUtil.addChannel(msg.getUserId(), channel);
        //2. 反馈消息；用户信息、用户对话框列表、好友列表、群组列表
        LoginResponse loginResponse = new LoginResponse();
        //2.1 用户信息
        UserInfo userInfo = userService.queryUserInfo(msg.getUserId());
        loginResponse.setIsSuccess(true);
        loginResponse.setUserId(userInfo.getUserId());
        loginResponse.setUserHead(userInfo.getUserHead());
        loginResponse.setUserNickName(userInfo.getUserNickName());
        //2.2 填充所有的对话框
        //queryTalkBoxInfoList填充id，type，name和head，其中name和head是根据type查询不同的表得到
        List<TalkBoxInfo> talkBoxInfoList = userService.queryTalkBoxInfoList(msg.getUserId());
        for (TalkBoxInfo talkBoxInfo : talkBoxInfoList) {
            ChatTalkDto chatTalkDto = new ChatTalkDto();
            chatTalkDto.setTalkId(talkBoxInfo.getTalkId());
            chatTalkDto.setTalkType(talkBoxInfo.getTalkType());
            chatTalkDto.setTalkName(talkBoxInfo.getTalkName());
            chatTalkDto.setTalkHead(talkBoxInfo.getTalkHead());
            chatTalkDto.setTalkSketch(talkBoxInfo.getTalkSketch());
            chatTalkDto.setTalkDate(talkBoxInfo.getTalkDate());
//            loginResponse.getChatTalkList().add(chatTalkDto);
            //针对当前对话框获得对话框对应的所有消息记录
            List<ChatRecordDto> chatRecordDtoList = new ArrayList<>();
            //如果是普通用户
            if(Friend.getCode().equals(talkBoxInfo.getTalkType())){
                //获得所有相关的消息记录 userId, friendId, msgContent, msgDate, msgType, userId包括当前用户和对话框用户
                List<ChatRecordInfo> chatRecordInfoList = userService.queryChatRecordInfoList(talkBoxInfo.getTalkId(), msg.getUserId(), talkBoxInfo.getTalkType());
                for (ChatRecordInfo chatRecordInfo : chatRecordInfoList) {
                    ChatRecordDto chatRecordDto = new ChatRecordDto();
                    //设置聊天记录所属的对话框id
                    chatRecordDto.setFriendId(talkBoxInfo.getTalkId());
                    //如果该消息是当前用户发出的
                    if(msg.getUserId().equals(chatRecordInfo.getUserId())){
                        chatRecordDto.setUserId(chatRecordInfo.getUserId());
                        chatRecordDto.setMsgUserType(0); //表示消息是当前用户发出
                    }else{
                        chatRecordDto.setUserId(chatRecordInfo.getFriendId());
                        chatRecordDto.setMsgUserType(1); //表示消息是好友发出
                    }
                    chatRecordDto.setMsgContent(chatRecordInfo.getMsgContent());
                    chatRecordDto.setMsgDate(chatRecordInfo.getMsgDate());
                    chatRecordDto.setMsgType(chatRecordInfo.getMsgType());
                    chatRecordDtoList.add(chatRecordDto);
                }
            }else if(Group.getCode().equals(talkBoxInfo.getTalkType())){
                //返回用户所参加的群组中包含的所有用户记录，不只是包含自己所发的信息，也包含其他成员发布的信息
                List<ChatRecordInfo> chatRecordInfoList = userService.queryChatRecordInfoList(talkBoxInfo.getTalkId(), msg.getUserId(), talkBoxInfo.getTalkType());
                for (ChatRecordInfo chatRecordInfo : chatRecordInfoList) {
                    ChatRecordDto chatRecordDto = new ChatRecordDto();
                    UserInfo memberInfo = userService.queryUserInfo(chatRecordInfo.getUserId());
                    chatRecordDto.setUserId(memberInfo.getUserId());
                    chatRecordDto.setFriendId(chatRecordInfo.getFriendId());
                    chatRecordDto.setUserNickName(memberInfo.getUserNickName());
                    chatRecordDto.setUserHead(memberInfo.getUserHead());
                    chatRecordDto.setMsgContent(chatRecordInfo.getMsgContent());
                    chatRecordDto.setMsgType(chatRecordInfo.getMsgType());
                    chatRecordDto.setMsgDate(chatRecordInfo.getMsgDate());
                    //如果是当前用户发出的消息，则类型为0，否则类型为1
                    Integer msgUserType = msg.getUserId().equals(chatRecordInfo.getUserId()) ? 0 : 1;
                    chatRecordDto.setMsgUserType(msgUserType);
                    chatRecordDtoList.add(chatRecordDto);
                }
            }
            chatTalkDto.setChatRecordDtoList(chatRecordDtoList);
            loginResponse.getChatTalkList().add(chatTalkDto);
        }
        //2.3 填充用户参加的群组
        List<UserGroupInfo> groupsInfoList = userService.queryUserGroupInfoList(msg.getUserId());
        for (UserGroupInfo groupsInfo : groupsInfoList) {
            UserGroupDto groupsDto = new UserGroupDto();
            groupsDto.setGroupId(groupsInfo.getGroupId());
            groupsDto.setGroupName(groupsInfo.getGroupName());
            groupsDto.setGroupHead(groupsInfo.getGroupHead());
            loginResponse.getUserGroupList().add(groupsDto);
        }
        //2.4 填充用户的好友信息
        List<UserFriendInfo> userFriendInfoList = userService.queryUserFriendInfoList(msg.getUserId());
        for (UserFriendInfo userFriendInfo : userFriendInfoList) {
            UserFriendDto friendDto = new UserFriendDto();
            friendDto.setFriendId(userFriendInfo.getFriendId());
            friendDto.setFriendName(userFriendInfo.getFriendName());
            friendDto.setFriendHead(userFriendInfo.getFriendHead());
            loginResponse.getUserFriendList().add(friendDto);
        }
        channel.writeAndFlush(loginResponse);
    }
}
